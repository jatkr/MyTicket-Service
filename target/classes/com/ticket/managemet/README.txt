Ticket Service

This application will provide below functionality
-Get the count of Total seats available  
-Hold number of Seats
-Confirm Reservation
To these achieve functionalities , 
this project has been implemented with User Option displaying on console.

Assumption-
-Maximum number of seats to hold at a time, must be less than or equal to total seats in a row of that level 

-Seat allocation is done in sequential order. All seat would be allocated sequentially.

-Seat Hold Expiration Time - 120 second


---------------------------------------------------------------------------------------------------------
Build Project
---------------------------------------------------------------------------------------------------------

1. Execute following command from within the project directory to compile:
   
   mvn compile
   
2. Execute following command from within the project directory to run tests:

   mvn test
   
3. Execute following command from within the project directory to package jar:

   mvn package
   
   This will create the MyTicket-Service-0.0.1-SNAPSHOT.jar 
   
4. To install the project's jar file to the local repository, execute following command within 
   the project directory:
 
   mvn clean install
   

---------------------------------------------------------------------------------------------------------
Execute the Application
---------------------------------------------------------------------------------------------------------

1. To execute the application, issue the following command from within the project directory:

	java -cp target/MyTicket-Service-0.0.1-SNAPSHOT.jar com.ticket.managemet.App
	Menu option would be displayed on console. For Optional field please select 0 as mentioned in menu option.
	In java, internally Optional.empty() is used even though 0 is pressed in order to utilize method with Optional Argument
