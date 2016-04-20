package com.ticket.managemet;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.ticket.management.dao.TicketManagementDAO;
import com.ticket.management.data.Level;
import com.ticket.management.data.Row;
import com.ticket.management.data.Seat;
import com.ticket.management.data.SeatHold;
import com.ticket.management.data.Status;

public class MyTicketServiceImpl implements MyTicketService {

	private TicketManagementDAO ticketManagementdao = null;
	private ScheduledExecutorService scheduler = null;
	// Here the best approach is to retrieve from configuration property file
	private static final int DEFAULT_SEATHOLD_EXPIRATION_TIME_LIMIT = 120;// in Second
	private static final int DEFAULT_MIN_LEVEL=1;
	private static final int DEFAULT_MAX_LEVEL=4;
	
	private int seatHoldExpirationTimeLimit;

	public MyTicketServiceImpl() {
		this.ticketManagementdao = new TicketManagementDAO();
		scheduler = Executors.newScheduledThreadPool(10);
		this.seatHoldExpirationTimeLimit = DEFAULT_SEATHOLD_EXPIRATION_TIME_LIMIT;
	}

	public int numSeatsAvailable(Optional<Integer> levelId) {
		int numSeats = 0;
		ticketManagementdao.deleteExpiredSeatHold();

		if (levelId.isPresent()) {

			numSeats = ticketManagementdao
					.getTotalNumberOfAvailableSeatsByLevel(levelId.get());

		} else {

			numSeats = ticketManagementdao.getTotalNumberOfAvailableSeats();

		}

		return numSeats;
	}

	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
			Optional<Integer> maxLevel, String customerEmail) {
		ticketManagementdao.deleteExpiredSeatHold();
		
		/*
		 * Here there is not actual DB call or large data so retrieving data for all levels
		 * For large data, this DAO method can be modified to get Level data by minLevel and max Level range
		 * Also, not necessarily to get all the rows and seats data along with same call
		 * This could be separated in multiple call.
		 */

		Map<Integer, Level> levels = ticketManagementdao.getLevelsData();
		SeatHold hold = null;
		int minimumLevel = minLevel.isPresent()? minLevel.get():DEFAULT_MIN_LEVEL;
		int maximumLevel = maxLevel.isPresent()? maxLevel.get() : DEFAULT_MAX_LEVEL;
		
		for (int level = minimumLevel; level <= maximumLevel; level++) {
			if (ticketManagementdao
					.getTotalNumberOfAvailableSeatsByLevel(level) < numSeats) {
				continue;
			}
			int counter = 0;
			Map<Character, Row> rows = levels.get(level).getRows();
			Iterator<Map.Entry<Character, Row>> iter = rows.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Map.Entry<Character, Row> entry = iter.next();

				Row row = entry.getValue();
				if (row.getNumberOfVacantSeats() < numSeats) {

					continue;
				}
				Set<Integer> heldSeatIds = new HashSet<Integer>();
				List<Seat> seats = row.getSeats();
				for (int seat = 0; seat < levels.get(level)
						.getTotalSeatsPerRow(); seat++) {
					if (seats.get(seat).getStatus().equals(Status.VACANT)) {
						counter++;
						heldSeatIds.add(seats.get(seat).getId());
						if (counter == numSeats) {


							hold = new SeatHold(customerEmail, level,
									row.getRowId(), heldSeatIds, scheduler,
									DEFAULT_SEATHOLD_EXPIRATION_TIME_LIMIT);
							ticketManagementdao.updateSeatStatus(hold,
									Status.HOLD);
							ticketManagementdao.insertSeatHold(hold);
							return hold;
						}
					} else {
						heldSeatIds.clear();
						counter = 0;
					}
				}

			}
		}
		if (hold == null){
			System.out.println("Enough seats are not available in level range you described.");
		}
		return hold;
	}

	public String reserveSeats(int seatHoldId, String customerEmail) {
		String code = null;
		ticketManagementdao.deleteExpiredSeatHold();
		SeatHold seatHold = ticketManagementdao
				.getSeatHoldBySeatHoldId(seatHoldId);
		if (seatHold != null) {
			if (!seatHold.getCustomerEmail().equalsIgnoreCase(customerEmail)) {
				code = "code1";
				System.out.println("Provided Email ID does not match with SeatHold ID ");
		}else {
				code = "" + new Date() + seatHoldId;
				ticketManagementdao.updateSeatStatus(seatHold, Status.RESVERED);
				ticketManagementdao.insertSeatReserve(code, seatHold);
				ticketManagementdao.deleteSeatHold(seatHoldId);
				System.out.println("\n\nRequested seats have been reserved for customer with email, " + customerEmail + ". Confirmation Code = " + code);        			

			}
		} else {
			code = "code2";
			System.out.println("Your held seats are Expired or SeatHoldId provided does not exist in System");

		}

		return code;
	}

}
