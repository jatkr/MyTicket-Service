package com.ticket.management.data;

import java.util.ArrayList;
import java.util.List;


public class Row {
	
	private Character rowId;
	private int levelId;
	private List<Seat> seats;
	private int numberOfVacantSeats;
	
	
	
	public Row(Character rowId, int levelId, int totalNumberOfSeats){
		this.rowId = rowId;
		this.levelId = levelId;
		this.seats = new ArrayList<Seat>();
		this.numberOfVacantSeats = totalNumberOfSeats;
		
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public int getNumberOfVacantSeats() {
		return numberOfVacantSeats;
	}

	public void setNumberOfVacantSeats(int numberOfVacantSeats) {
		this.numberOfVacantSeats = numberOfVacantSeats;
	}

	public void setRowId(Character rowId) {
		this.rowId = rowId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	
	public void incrementNumberOfVacantSeats() {
		this.numberOfVacantSeats ++;
	}
	
	public void decrementNumberOfVacantSeats() {
		this.numberOfVacantSeats --;
	}

	public Character getRowId() {
		return rowId;
	}

	public int getLevelId() {
		return levelId;
	}
	

}
