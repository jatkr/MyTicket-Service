package com.ticket.management.dao;


import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Before;


import org.junit.Test;

import com.ticket.management.data.SeatHold;
import com.ticket.management.data.Status;

import junit.framework.TestCase;

public class TicketManagementDAOTest extends TestCase {
	
	private TicketManagementDAO dao;
	private SeatHold hold;
	private ScheduledExecutorService scheduler;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		HashSet<Integer> set= new HashSet<Integer>();
		set.add(1);
		set.add(2);
		dao= new TicketManagementDAO();
		scheduler = Executors.newScheduledThreadPool(10);
		hold= new SeatHold("nj@nj.com", 2, 'A',set, scheduler, 60);
		
	}

	@Test
	public final void testGetTotalNumberOfAvailableSeatsByLevel() {
		assertEquals(1250, dao.getTotalNumberOfAvailableSeatsByLevel(1));
		
	}

	@Test
	public final void testGetTotalNumberOfAvailableSeats() {
		assertEquals(6250, dao.getTotalNumberOfAvailableSeats());
		
	}

	@Test
	public final void testUpdateSeatStatus() {
		dao.updateSeatStatus(hold, Status.HOLD);
		assertEquals(1998, dao.getTotalNumberOfAvailableSeatsByLevel(2));
		
	}
	

	@Test
	public final void testInsertSeatReserve() {
		dao.insertSeatReserve("code", hold);
		assertNotNull(dao.getReservationByCode("code"));
		
	}
	
	
	@Test
	public final void testDeleteExpiredSeatHold() {
		//seatHold should not be expired 
		assertNull(dao.getSeatHoldBySeatHoldId(1));
		
	}
	

	public final void testDeleteSeatHold() {
		dao.deleteSeatHold(1);
		assertNull(dao.getSeatHoldBySeatHoldId(1));
		
	}

	
	
	

}
