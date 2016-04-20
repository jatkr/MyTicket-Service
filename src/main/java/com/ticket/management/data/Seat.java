package com.ticket.management.data;



public class Seat {
	
	/* Option:AtomicIteger could have been used here to generate Id
	 * not used here by assuming DB table would have unique seat_id for each seat
	 * 
	 */
	
	//private static AtomicInteger nextId = new AtomicInteger();
	private int id;
	private int levelId;
	private Character rowId;
	private Status status;
	

	public Seat(int id,Character rowId, int levelId) {
		this.id = id;//nextId.incrementAndGet();
		this.rowId = rowId;
		this.status = Status.VACANT;
		this.levelId = levelId;
	}	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(Character rowId) {
		this.rowId = rowId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	

}
