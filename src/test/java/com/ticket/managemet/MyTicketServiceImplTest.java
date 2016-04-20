package com.ticket.managemet;



import java.util.Optional;

import org.junit.Test;

import com.ticket.management.data.SeatHold;

import junit.framework.TestCase;

public class MyTicketServiceImplTest extends TestCase {
	MyTicketService service=null;
	
	public MyTicketServiceImplTest() {
		this.service= new MyTicketServiceImpl();
	}
	
	@Test
	public final void testNumSeatsAvailableForLevel() {
		assertEquals(1250, service.numSeatsAvailable(Optional.of(1)));
		
	}
	
	@Test
	public final void testNumSeatsAvailable() {
		Optional<Integer> level=Optional.empty(); 
		assertEquals(6250, service.numSeatsAvailable(level));
		
	}
	
	@Test
	public final void testFindAndHoldSeats() {
		SeatHold hold= service.findAndHoldSeats(20, Optional.of(1), Optional.of(2), "nj@nj.com");
		assertNotNull(hold);
		
	}
	
	@Test
	public final void testReserveSeats() {
		SeatHold hold= service.findAndHoldSeats(20, Optional.of(1), Optional.of(2), "nj@nj.com");
		String code=service.reserveSeats(hold.getSeatHoldId(), "nj@nj.com");
		assertEquals(1230, service.numSeatsAvailable(Optional.of(1)));
		
	}
	
	

}
