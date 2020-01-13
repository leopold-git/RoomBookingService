# RoomBookingService
Project for a Room Booking service using REST API/ Spring Boot Server and SQL Database. Complete setup of database included.


Eddie Kochman, Brian Li, Leopold Ringmayr


 Instructions
1) Run the reserveOxy.sql to create the database and the tables
2) Run the insert method with the csv file so that the data can be inserted into the tables
3)How to run our project:
- Make sure MySQL passwords are included in the UserController file for all endpoints
that require a MySQL connection
- Open terminal and go to directory of Maven project (in our case, cd Desktop/DBuser)
- Run command mvn install to compile project and build jar file
- Jar file should be in target folder of the project
- In our case, drag jar file to Desktop
- Make sure there is a application.properties file in the same directory as the jar file (to run
on port 80)
- Open new Terminal window, and cd to directory with jar file (here, cd Desktop)
- Run command java -jar DBuser-0.0.1.jar
- Server should be running on port 80
- Open browser at http://localhost/homePage.html to begin using website
