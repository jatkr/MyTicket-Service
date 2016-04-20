package com.ticket.management.data;



import java.util.HashMap;
import java.util.Map;

public class Level {
	
	private int id;
	private String name;
	private int totalRows;
	private float pricePerSeat;
	private int totalSeatsPerRow;
	private Map<Character,Row> rows;
	private int totalAvailableSeats;
	
	public Level(int id,String name,int totalRows,float price,int totalSeatsPerRow){
		this.id=id;
		this.name = name;
		this.totalRows =totalRows;
		this.pricePerSeat =price;
		this.totalSeatsPerRow = totalSeatsPerRow;
		this.rows = new HashMap<Character,Row>();
		this.totalAvailableSeats = totalRows*totalSeatsPerRow;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public float getPricePerSeat() {
		return pricePerSeat;
	}

	public void setPricePerSeat(float pricePerSeat) {
		this.pricePerSeat = pricePerSeat;
	}

	public int getTotalSeatsPerRow() {
		return totalSeatsPerRow;
	}

	public void setTotalSeatsPerRow(int totalSeatsPerRow) {
		this.totalSeatsPerRow = totalSeatsPerRow;
	}

	public Map<Character, Row> getRows() {
		return rows;
	}

	public void setRows(Map<Character, Row> rows) {
		this.rows = rows;
	}

	public int getTotalAvailableSeats() {
		return totalAvailableSeats;
	}

	public void setTotalAvailableSeats(int totalAvailableSeats) {
		this.totalAvailableSeats = totalAvailableSeats;
	}
	
	public void incrementNumberOfVacantSeats() {
		this.totalAvailableSeats ++;
	}
	
	public void decrementNumberOfVacantSeats() {
		this.totalAvailableSeats --;
	}	

}
