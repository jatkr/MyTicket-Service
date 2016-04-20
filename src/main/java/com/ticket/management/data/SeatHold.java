package com.ticket.management.data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class SeatHold {
	private static AtomicInteger nextId = new AtomicInteger();
	private int seatHoldId;
	private int levelId;
	private Character rowId;
	private String customerEmail; //can be taken as part of Customer
	private volatile boolean expired = false;
	private Set<Integer> seatIds = new HashSet<Integer>();		
	private final ScheduledExecutorService scheduler;
	
	public SeatHold(String customerEmail, int levelId, Character rowId, Set<Integer> seatIds, ScheduledExecutorService scheduler, int expireTime) {
		this.seatHoldId = nextId.incrementAndGet();
		this.customerEmail = customerEmail;
		this.levelId = levelId;
		this.rowId = rowId;
		this.seatIds = seatIds;
		this.scheduler = scheduler;
		this.setValidity(expireTime);
	}
		
	public void setValidity(final int seconds) {
        scheduler.schedule(new Runnable() {
            public void run() {
                expired = true;
            }
        }, seconds, TimeUnit.SECONDS);
    }

	public int getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public Set<Integer> getSeatIds() {
		return seatIds;
	}

	public Character getRowId() {
		return rowId;
	}

	public void setRowId(Character rowId) {
		this.rowId = rowId;
	}

	public ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	public void setSeatIds(Set<Integer> seatIds) {
		this.seatIds = seatIds;
	}
	
}
