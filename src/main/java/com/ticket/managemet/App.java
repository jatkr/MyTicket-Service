package com.ticket.managemet;

import com.ticket.management.data.SeatHold;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Ticket Service System
 *
 */
public class App 
{
    private static Scanner scanner = new Scanner( System.in );
    
    public static void main( String[] args )
    {	
    	int choice;
    	MyTicketService service= new MyTicketServiceImpl();
        /*This can be implemented as dynamic web project with UI
         * All validation messages should be retrieved from ValidationMessage.properties file
         * but just to achieve functionality for now - have kept it simple
         * can be enhanced by time
         * 
         * 
         */
    	
    	do{
    	System.out.println("My Ticket Service System: ");
    	System.out.println("1. Find the number of seats available ");
    	System.out.println("2. Find and hold the best available seats ");
    	System.out.println("3. Reserve seats");
    	System.out.println("Enter your choice or Enter 0 to Exit");
    	choice= scanner.nextInt();
    	
    	
    	switch(choice) {    
    	case 0:
    		System.out.println("Closing Application");
    		System.exit(0);
    		break;
		case 1:    	
			Optional<Integer> LevelId=Optional.empty();
			
			do{
			System.out.println("Enter Venue Level Id = ");
			System.out.println("0: ALL, 1: Orchestra, 2: Main, 3: Balcony 1, 4: Balcony 2 ");
			LevelId = readInt();
			//This is just to achieve optional functionality, so that numSeatsAvailable method get empty object
			if(LevelId.isPresent() && LevelId.equals(Optional.of(Integer.valueOf(0)))){
				LevelId=Optional.empty();
			}
			}while(LevelId.isPresent() && ((LevelId.get() < 0) || (LevelId.get() > 4)));
			String s =LevelId.isPresent()?LevelId.get().toString():"ALL";
			System.out.println("Number of seats available in Venue Level with ID " + s + " = " + service.numSeatsAvailable(LevelId));
			break;
		case 2:    			
			System.out.println("Enter Number of Seats to Hold = ");
			int numSeats = readInt().get();
			
			Optional<Integer> minLevel=Optional.empty();
			Optional<Integer> maxLevel=Optional.empty();
			
			do{
			System.out.println("0: ALL, 1: Orchestra, 2: Main, 3: Balcony 1, 4: Balcony 2 ");
			System.out.println("Enter Minimum Venue Level ID = ");
			
			minLevel = readInt();
			if(minLevel.isPresent() && minLevel.equals(Optional.of(Integer.valueOf(0)))){
				minLevel=Optional.empty();
			}
			}while(minLevel.isPresent() && (minLevel.get() < 0 || minLevel.get() > 4) );

			
			do{
			System.out.println("0: ALL, 1: Orchestra, 2: Main, 3: Balcony 1, 4: Balcony 2 ");
			System.out.println("Enter Maximum Venue Level ID = ");
			maxLevel = readInt(); 
			if(maxLevel.isPresent() && maxLevel.equals(Optional.of(Integer.valueOf(0)))){
				maxLevel=Optional.empty();
			}
			}while((maxLevel.isPresent()) &&(maxLevel.get() < 0 || maxLevel.get() > 4));
			
			if(minLevel.isPresent() && maxLevel.isPresent()){
				
				if(minLevel.get().intValue()>maxLevel.get().intValue()){
					System.out.println("Max Level ID should be Greater or equal to Min Level ID");
					continue;
				}
			}
			
			System.out.println("Enter Customer Email = ");
			String email = null;
			
			if (scanner.hasNext()) {
				email = isValidEmail(scanner.next());
			}
			
			SeatHold seatHold = service.findAndHoldSeats(numSeats, minLevel, maxLevel, email);
			 
			if (seatHold != null) {
				System.out.println("\n\nCompleted seat hold request, seatHoldId = " + seatHold.getSeatHoldId());	
			} else {
				System.out.println("\n\nUnable to complete seat hold request.");	
			} 			
			break;
		case 3:
			System.out.println("Enter seatHoldId = ");
			int seatHoldId = readInt().get();
			
			System.out.println("Enter Customer Email = ");
			String customerEmail = null;
		
			if (scanner.hasNext()) {
				customerEmail = isValidEmail(scanner.next());
			}
			
			String confirmationCode = service.reserveSeats(seatHoldId, customerEmail);
			
			
			if (confirmationCode == null) {
			
				System.out.println("\n\nUnable to complete seat reservation request.");	
			}   			
			break;
		default:
				System.out.println("Invalid Choice");
    	}
   
    	
    	}while(choice!=0);
    	
    }
    
    //The standard way to validate any field is to validate through Validator 
    //For now validating here
    public static String isValidEmail(String emailStr) {
        Pattern pattern =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        while(!pattern.matcher(emailStr).find()){
        	System.out.println("Invalid email. Enter email again.");
        	scanner.next();
        }
        return emailStr;
    }
   
    private static Optional<Integer> readInt() {
    	while ((!scanner.hasNextInt())&&!(scanner.next().equals(new String()))) {
		   System.out.println("Please enter a numeric value.");
		   scanner.nextLine();
		}
			
		int value = scanner.nextInt();
		return Optional.of(value);
    }
}
