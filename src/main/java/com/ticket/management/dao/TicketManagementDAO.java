package com.ticket.management.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ticket.management.data.Level;
import com.ticket.management.data.Row;
import com.ticket.management.data.Seat;
import com.ticket.management.data.SeatHold;
import com.ticket.management.data.Status;

public class TicketManagementDAO {
	
	public static final int TotalNumberOfLevels=4;
	
	private Map<Integer, Level> levelsMap = new HashMap<Integer, Level>(TotalNumberOfLevels);
	private Map<Integer, SeatHold> seatHolds= new HashMap<Integer, SeatHold>();
	private Map<String, SeatHold> seatReserved= new HashMap<String, SeatHold>();
	/**
	 * DAO Ideally should interact with Database. Here,have not utilized DB so just created static data 
	 * through below constructor for ease 
	 * When update levelsMap, ideally data from DB should be updated 
	 */
	public TicketManagementDAO(){
		//Creating data
		Level orchestra = new Level(1, "Orchestra", 25, 100.00f , 50);
		Level main = new Level(2, "Main", 20, 75.00f , 100);
		Level balcony1 = new Level(3, "Balcony1", 15, 50.00f , 100);
		Level balcony2 = new Level(4, "Balcony2", 15, 40.00f , 100);
		orchestra.setRows(createRowsForLevel(1,25,50));
		main.setRows(createRowsForLevel(2,20,100));
		balcony1.setRows(createRowsForLevel(3,15,100));
		balcony2.setRows(createRowsForLevel(4,15,100));
		levelsMap.put(1, orchestra);
		levelsMap.put(2, main);
		levelsMap.put(3, balcony1);
		levelsMap.put(4, balcony2);
		
	}
	
	private Map<Character, Row> createRowsForLevel(int levelId,int totalRows, int seatsInRow) {
		Map<Character, Row> rows = new HashMap<Character,Row>();
		List<Seat> seats=null;
		Row row=null;
		char rowId='A';
		int seatId=1;
		for(int i=1;i<=totalRows;i++){
			row= new Row(rowId, 1, seatsInRow);
			seats= new ArrayList<Seat>();
			Seat seat=null;
			for(int j=1;j<=seatsInRow;j++){
				seat= new Seat(seatId,rowId, levelId);
				seats.add(seat);
				seatId++;
			}
			
			row.setSeats(seats);
			rows.put(rowId, row);
			rowId++;
		}
		return rows;
		
	}
	
	
	public Map<Integer, Level> getLevelsData() {
		return levelsMap;
	}

	public void setLevelsMap(Map<Integer, Level> levelsMap) {
		this.levelsMap = levelsMap;
	}

	public static int getTotalnumberoflevels() {
		return TotalNumberOfLevels;
	}

	public int getTotalNumberOfAvailableSeatsByLevel(int levelId){
		
		return levelsMap.get(levelId).getTotalAvailableSeats();
	}
	
	public int getTotalNumberOfAvailableSeats() {
		int totalSeats=0;
		
		for (int i=1; i<5;i++) {
			
			totalSeats+=levelsMap.get(i).getTotalAvailableSeats();
		} 
		
		return totalSeats;
	}
	
	public void updateSeatStatus(SeatHold hold, Status status) {
		Level level=levelsMap.get(hold.getLevelId());
		Row row= level.getRows().get(hold.getRowId());
		Iterator<Seat> it= row.getSeats().iterator();
		while(it.hasNext()){
			Seat seat=it.next();
			if(hold.getSeatIds().contains(seat.getId())){
				
				seat.setStatus(status);
				if(Status.VACANT.equals(status)){
					row.incrementNumberOfVacantSeats();
					level.incrementNumberOfVacantSeats();
				} 
				if(Status.HOLD.equals(status)){
					row.decrementNumberOfVacantSeats();
					level.decrementNumberOfVacantSeats();
				}
			}
		}		
	}
	
	public void insertSeatHold(SeatHold hold) {
		seatHolds.put(hold.getSeatHoldId(), hold);		
	}
	
	public void deleteSeatHold(int seatHoldId) {
		seatHolds.remove(seatHoldId);
		
	}
	
	public void deleteExpiredSeatHold() {
		
		Iterator<Map.Entry<Integer, SeatHold>> iter = seatHolds.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry<Integer, SeatHold> entry = iter.next();
		    
		    SeatHold seatHold = entry.getValue();
		   
		    if(seatHold.isExpired()){
		    	System.out.println("SeatHoldId:"+seatHold.getSeatHoldId()+"Total Seats:"+seatHold.getSeatIds().size());
		    	updateSeatStatus(seatHold,Status.VACANT);
		        iter.remove();
		    }
		}
		
	}
	
	public SeatHold getSeatHoldBySeatHoldId(int seatHoldId) {
		
		if(seatHolds.containsKey(seatHoldId)){
			return seatHolds.get(seatHoldId);
		}
		
		return null;
		
		
		
	}
	
	public SeatHold getReservationByCode(String code) {
		return seatReserved.get(code);
		
	}
	
	public void insertSeatReserve(String code,SeatHold seatHolad) {
		seatReserved.put(code, seatHolad);
		
		
	}
	
	public Map<Character, Row> getRowsByLevel(int levelId) {
		return levelsMap.get(levelId).getRows();
		
	}
	

}
