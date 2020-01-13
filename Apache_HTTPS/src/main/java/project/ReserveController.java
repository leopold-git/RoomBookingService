package project;
import org.springframework.web.bind.annotation.*; 
import org.springframework.http.*;

import javax.servlet.http.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;

import javax.servlet.http.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin
public class ReserveController {
    
    
    @RequestMapping(value = "/loadDB", method = RequestMethod.GET) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> database() {
        //String nameToPull = request.getParameter("firstname");
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
        int facility_id = 0;
        String event_name = "";
        String location = "";
        String phone_number = "";
        int attending= 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
            String query = "select Event.event_name, count(Student.stu_id) from Student, Membership, Club, Event where Student.stu_id = Membership.stu_id and Membership.club_name = Club.club_name and Club.club_name = Event.club_name group by Event.event_name order by count(Student.stu_id) desc";
            PreparedStatement stmt = null;  //important for safety reasons
            /**String tester = "tester";
            return tester;**/
            
            stmt = conn.prepareStatement(query);
           // stmt.setString(1, nameToPull);
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                event_name = rs.getString("event_name");
                attending = rs.getInt("count(Student.stu_id)");
                
                JSONObject obj = new JSONObject();
                obj.put("event_name", event_name);
                obj.put("attending", attending);
      
                
                nameArray.put(obj);
                    }
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
      
    }
    

    
       @RequestMapping(value = "/showEvent", method = RequestMethod.GET) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> showEvent() {
        //String nameToPull = request.getParameter("firstname");
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
        String event_name = "";
        String supplier = "";
       int room_no = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
            String query = "SELECT event_name, supplier, room_no FROM Event";
            PreparedStatement stmt = null;  //important for safety reasons
            /**String tester = "tester";
            return tester;**/
            
            stmt = conn.prepareStatement(query);
           // stmt.setString(1, nameToPull);
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                event_name = rs.getString("event_name");
                supplier = rs.getString("supplier");
                room_no = rs.getInt("room_no");
                
                JSONObject obj = new JSONObject();
                obj.put("event_name", event_name);
                obj.put("supplier", supplier);
                obj.put("room_no", room_no);
           
                
                nameArray.put(obj);
                    }
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
      
    }

         @RequestMapping(value = "/clubQuery", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> clubQuery(@RequestBody String body, HttpServletRequest request) {
        //String clubname = request.getParameter("clubname");
        JSONObject bodyObject = new JSONObject(body);
        String clubname = bodyObject.getString("clubname");
        System.out.println(clubname);
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
        String club_name = "";
        String club_category = "";
        double c_budget = 0;
        int presID = 0;
        int advisor_id = 0;
        String dept_name = "";


       

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
            String query = "SELECT * FROM Club WHERE club_name = ?";
            PreparedStatement stmt = null;  //important for safety reasons
            /**String tester = "tester";
            return tester;**/
            
            stmt = conn.prepareStatement(query);
            stmt.setString(1, clubname);
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                club_name = rs.getString("club_name");
                club_category = rs.getString("club_category");
                c_budget = rs.getDouble("c_budget");
                presID = rs.getInt("presID");
                advisor_id = rs.getInt("advisor_id");
                dept_name = rs.getString("dept_name");
                JSONObject obj = new JSONObject();
                obj.put("club_name", club_name);
                obj.put("club_category", club_category);
                obj.put("c_budget", c_budget);
                obj.put("presID", presID);
                obj.put("advisor_id", advisor_id);
                obj.put("dept_name", dept_name);

           
                
                nameArray.put(obj);
                    }
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
      
    }

     @RequestMapping(value = "/upcoming", method = RequestMethod.GET) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> upcoming() {
        //String nameToPull = request.getParameter("firstname");
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
        String event_name = "";
        String club_name = "";
        String date = "";
        String e_start_time = "";
        String e_end_time = "";
        String building_name = "";
        int room_no = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
        String query = "select event_name, club_name, date, e_start_time, e_end_time, building_name, room_no from Event where date> CURDATE() order by date, e_start_time";
            PreparedStatement stmt = null;  //important for safety reasons
            /**String tester = "tester";
            return tester;**/
            
            stmt = conn.prepareStatement(query);
           // stmt.setString(1, nameToPull);
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                event_name = rs.getString("event_name");
                club_name = rs.getString("club_name");
                date = (rs.getDate("date")).toString();
                e_start_time = rs.getString("e_start_time");
                e_end_time = rs.getString("e_end_time");
                building_name = rs.getString("building_name");
                room_no = rs.getInt("room_no");
                
                JSONObject obj = new JSONObject();
                obj.put("event_name", event_name);
                obj.put("club_name", club_name);
                obj.put("date", date);
                obj.put("e_start_time", e_start_time);
                obj.put("e_end_time", e_end_time);
                obj.put("building_name", building_name);
                obj.put("room_no", room_no);

                
                nameArray.put(obj);
                    }
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
      
    }

            
    @RequestMapping(value = "/eventBook", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> eventBook(@RequestBody String body, HttpServletRequest request) {
        //String clubname = request.getParameter("clubname");
        JSONObject bodyObject = new JSONObject(body);
        String in_event_name= bodyObject.getString("event_name");
        String in_supplier = bodyObject.getString("supplier");

        System.out.print(in_event_name);
        String in_start = bodyObject.getString("e_start_time");
        String in_end = bodyObject.getString("e_end_time");
        String in_date = bodyObject.getString("date");
        String in_clubname = bodyObject.getString("club_name");
        String in_building_name = bodyObject.getString("building_name");
        int in_room_no = bodyObject.getInt("room_no");
        int in_presID =  bodyObject.getInt("presID");
        int in_fac_id =  bodyObject.getInt("fac_id");


     




        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
            String event_name = "";
            String supplier = "";
            String e_start_time = "";
            String e_end_time = "";
            String date = "";
            String club_name = "";
            String building_name = "";
            int room_no = -1;
            int presID = -1;
            int fac_id = -1;


            // move this???
            
            String message = "";


               // SOME INITIAL CHECKS IF USER INPUT WAS VALID
        // DID USER PROVIDE INPIUT FOR ALL FORM FIELDS
        if (in_event_name == null || in_supplier == null || in_end == null || in_start == null || in_date == null
                    || in_clubname == null || in_building_name == null || in_room_no == -1 || in_presID == 0 || in_fac_id == 0) {
                 message = "Not everything was filled! GoodBye!";
                         JSONObject obj = new JSONObject();

                 obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);

            }
        // DOES ENTERED BUILDING EXIST
        if (!in_building_name.equals("Johnson") && !in_building_name.equals("Fowler") && !in_building_name.equals("Swan")
                        && !in_building_name.equals("Mosher") && !in_building_name.equals("Norris")) {
                    message = "That building does not exist!";
                            JSONObject obj = new JSONObject();

                    obj.put("message", message);
                    
                    nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                }
        // DOES ENTERED ROOM NO EXIST
            if (in_room_no != 101 && in_room_no != 102 && in_room_no != 103 && in_room_no != 104 && in_room_no != 105 && in_room_no != 201
                    && in_room_no != 202 && in_room_no != 203 && in_room_no != 204 && in_room_no != 205) {
                message = "That room_no does not exist!";
                        JSONObject obj = new JSONObject();

                obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
            }
       

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
            PreparedStatement ps = null;  //important for safety reasons
           // condition 2: check if student is president id
            String presValid = "select E.presID from Event as E, Club as C where E.club_name = C.club_name and E.presID = ? ;";
            ps = conn.prepareStatement(presValid);
            ps.setInt(1, in_presID);

            // System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                presID = rs.getInt("presID");
            }
            if (presID == -1) {
                message = ("That's not valid president ID");
                            JSONObject obj = new JSONObject();

                obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
            }

            // condition 3: check if club name exists
            String clubValid = "select C.club_name from Event as E, Club as C where E.club_name = C.club_name and C.presID = ?;";
            ps = conn.prepareStatement(clubValid);
            ps.setInt(1, presID);
            // System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                club_name = rs.getString("club_name");
            }
            if (club_name == null) {
                message = ("The club_name does not exist or the student is not the president of the club");
                            JSONObject obj = new JSONObject();

                obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
            }

            // condition 4: Date calculation
            java.util.Hashtable<Integer, String> dayIndex = new java.util.Hashtable<Integer, String>();
            java.util.Hashtable<Integer, Integer> monthNumDays = new java.util.Hashtable<Integer, Integer>();

            // total number of days corresponding to the month
            monthNumDays.put(1, 31);
            monthNumDays.put(2, 59);
            monthNumDays.put(3, 90);
            monthNumDays.put(4, 120);
            monthNumDays.put(5, 151);
            monthNumDays.put(6, 181);
            monthNumDays.put(7, 212);
            monthNumDays.put(8, 243);
            monthNumDays.put(9, 273);
            monthNumDays.put(10, 304);
            monthNumDays.put(11, 334);

            // key for finding what day of the week it is
            dayIndex.put(1, "Tuesday");
            dayIndex.put(2, "Wednesday");
            dayIndex.put(3, "Thursday");
            dayIndex.put(4, "Friday");
            dayIndex.put(5, "Saturday");
            dayIndex.put(6, "Sunday");
            dayIndex.put(7, "Monday");

            System.out.println("THIS IS THE DATE FROM USER:" + in_date);

            // check if date is put in valid format yyyy-mm-dd
            String[] splitter = in_date.toString().split("-");
            if (splitter[0].length() != 4 || splitter[1].length() != 2 || splitter[2].length() != 2) {
                message = ("Date is not a valid input!");
                            JSONObject obj = new JSONObject();

                obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
            }

            // find the index
            int calculate = -1;
            if (splitter[1].equals("01")) {
                calculate = (Integer.parseInt(splitter[2])) % 7;
            } else {
                calculate = (monthNumDays.get(Integer.parseInt(splitter[1]) - 1) + Integer.parseInt(splitter[2])) % 7;
            }



            // get what day of the week it is
            String day = dayIndex.get(calculate);
              if(calculate == 0){
                day = "Monday";
            }
            System.out.println(day);

            String construction = "";
            // compare day then add to string of query
            if (day.equals("Monday")) {
                construction = "c.mon is true";
            } else if (day.equals("Tuesday")) {
                construction = "c.tue is true";
            } else if (day.equals("Wednesday")) {
                construction = "c.wed is true";
            } else if (day.equals("Thursday")) {
                construction = "c.thu is true";
            } else if (day.equals("Friday")) {
                construction = "c.fri is true";
            }
            if (!construction.equals("")) {
                String roomAvailable = "select c.c_name, c.c_start_time, c.c_end_time from Course as C where "
                        + construction + " and building_name  = ? and room_no = ?;";

                // check course time conflict
                ps = conn.prepareStatement(roomAvailable);
                ps.setString(1, in_building_name);
                ps.setInt(2, in_room_no);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (!(compareTime(in_end, rs.getString("c_start_time"))
                            || compareTime(rs.getString("c_end_time"), in_start))) {
                        message = "Sorry! There is a time conflict since " + rs.getString("c_name")
                                + " begins at " + rs.getString("c_start_time") + " to " + rs.getString("c_end_time")
                                + " in " + in_building_name + " " + in_room_no + "!!!";
                                            JSONObject obj = new JSONObject();

                    obj.put("message", message);
                    
                    nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                    }
                }
            }

            // check event time conflict
            String roomAvailable2 = "select event_name, e_start_time, e_end_time from Event where building_name = ? and room_no = ? and date = ?;";
            ps = conn.prepareStatement(roomAvailable2);
            ps.setString(1, in_building_name);
            ps.setInt(2, in_room_no);
            ps.setDate(3, Date.valueOf(in_date));
            // System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (!(compareTime(in_end, rs.getString("e_start_time"))
                        || compareTime(rs.getString("e_end_time"), in_start))) {
                     message = "Sorry! There is a time conflict since " + rs.getString("event_name")
                            + " begins at " + rs.getString("e_start_time") + " to " + rs.getString("e_end_time")
                            + " in " + in_building_name + " " + in_room_no + "!!!";
                                        JSONObject obj = new JSONObject();



                obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                }
            }


            // prepare to set up for adding the insert method
            java.util.Date todayDate = new java.util.Date();
            long time = todayDate.getTime();
            Timestamp ts = new Timestamp(time);

            String insertBuilding = "Insert INTO Building (time_stamp, building_name, room_no, max_cap, stu_id, fac_id) values (?,?,?,?,?,?)";
            ps = conn.prepareStatement(insertBuilding);
            ps.setTimestamp(1, ts);
            ps.setString(2, in_building_name);
            ps.setInt(3, in_room_no);
            String roomNo = String.valueOf(in_room_no);
            if (roomNo.charAt(0) == '1') {
                ps.setInt(4, 75);
            } else {
                ps.setInt(4, 50);
            }
            ps.setInt(5, in_presID);
            ps.setInt(6, in_fac_id);
            // System.out.println(ps);
            ps.executeUpdate();

            // need to insert into event table and building table
            String insertEvent = "Insert INTO EVENT (event_name, supplier, e_start_time, e_end_time, date, club_name, time_stamp, building_name, room_no, presID, fac_id) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(insertEvent);
            ps.setString(1, in_event_name);
            ps.setString(2, in_supplier);
            ps.setString(3, in_start);
            ps.setString(4, in_end);
            ps.setDate(5, Date.valueOf(in_date));
            ps.setString(6, in_clubname);
            ps.setTimestamp(7, ts);
            ps.setString(8, in_building_name);
            ps.setInt(9, in_room_no);
            ps.setInt(10, in_presID);
            ps.setInt(11, in_fac_id);
            // System.out.println(ps);
            ps.executeUpdate();

            ps.close();
        
                       
             //nameArray.put(obj);
        
    
    
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        message = ("Success! Event has been added!");
                    JSONObject obj = new JSONObject();


                obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);      
    }



         @RequestMapping(value = "/showPastEvents", method = RequestMethod.GET) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> showPastEvents() {
        //String clubname = request.getParameter("clubname");
     
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
         String event_name = "";
            String supplier = "";
            String e_start_time = "";
            String e_end_time = "";
            String date = "";
            String club_name = "";
            String building_name = "";
            int room_no = -1;
            int presID = -1;
            int fac_id = -1;


       

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
            String query = "select event_name, supplier, date, e_start_time, e_end_time, club_name, building_name, room_no from Event where date< CURDATE() order by date DESC, e_start_time DESC;";
            PreparedStatement stmt = null;  //important for safety reasons
            /**String tester = "tester";
            return tester;**/
            
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                event_name = rs.getString("event_name");
                club_name = rs.getString("club_name");
                date = (rs.getDate("date")).toString();
                e_start_time = rs.getString("e_start_time");
                e_end_time = rs.getString("e_end_time");
                building_name = rs.getString("building_name");
                room_no = rs.getInt("room_no");
                
                JSONObject obj = new JSONObject();
                obj.put("event_name", event_name);
                obj.put("club_name", club_name);
                obj.put("date", date);
                obj.put("e_start_time", e_start_time);
                obj.put("e_end_time", e_end_time);
                obj.put("building_name", building_name);
                obj.put("room_no", room_no);

                
                nameArray.put(obj);
                    }
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
      
    }
    

       @RequestMapping(value = "/showCourse", method = RequestMethod.GET) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> showCourse() {
        //String clubname = request.getParameter("clubname");
     
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
         String crn = "";
            String c_name = "";
            String c_start_time = "";
            String c_end_time = "";
            String daysAvail = "";
            String building_name = "";
            int room_no = -1;
         
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
            String query = "select crn, c_name, c_start_time, c_end_time, Mon, Tue, Wed, Thu, Fri, building_name, room_no from Course order by crn;";
            PreparedStatement stmt = null;  //important for safety reasons
            /**String tester = "tester";
            return tester;**/
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                crn = rs.getString("crn");
                c_name = rs.getString("c_name");
                c_start_time = rs.getString("c_start_time");
                c_end_time = rs.getString("c_end_time");

                if (rs.getBoolean("Mon") == true) {
                    daysAvail = daysAvail + "Monday ";
                }

                if (rs.getBoolean("Tue") == true) {
                    daysAvail = daysAvail + "Tuesday ";
                }
                if (rs.getBoolean("Wed") == true) {
                    daysAvail = daysAvail + "Wednesday ";
                }
                if (rs.getBoolean("Thu") == true) {
                    daysAvail = daysAvail + "Thursday ";
                }
                if (rs.getBoolean("Fri") == true) {
                    daysAvail = daysAvail + "Friday ";
                }
                building_name = rs.getString("building_name");
                room_no = rs.getInt("room_no");
                JSONObject obj = new JSONObject();

                obj.put("crn", crn);
                obj.put("c_name", c_name);
                obj.put("c_start_time", c_start_time);
                obj.put("c_end_time", c_end_time);
                obj.put("daysAvail", daysAvail);
                obj.put("building_name", building_name);
                obj.put("room_no", room_no);
                nameArray.put(obj);
                daysAvail = "";

            }
                
                    
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
      
    }

 @RequestMapping(value = "/showClub", method = RequestMethod.GET) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> showClub() {
        //String clubname = request.getParameter("clubname");
     
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
            String club_name = "";
            String club_category = "";
            String advisor = "";
       
         
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
             String query = "select C.club_name, C.club_category, F.fac_name as Advisor from Club as C, Faculty as F where C.advisor_id = F.fac_id order by C.club_name;";
            PreparedStatement stmt = null;  //important for safety reasons
            /**String tester = "tester";
            return tester;**/
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                club_name = rs.getString("C.club_name");
                club_category = rs.getString("C.club_category");
                advisor = rs.getString("Advisor");
                JSONObject obj = new JSONObject();


                obj.put("club_name", club_name);
                obj.put("club_category", club_category);
                obj.put("advisor", advisor);
                nameArray.put(obj);
            }
                
                
                    
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
      
    }

     @RequestMapping(value = "/advancedCourseSearch", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> advancedCourseSearch(@RequestBody String body, HttpServletRequest request) {    //requesting to the server
        JSONObject bodyObj = new JSONObject(body);
        String in_crn = bodyObj.getString("crn");
        System.out.println(in_crn);
        String in_c_name = bodyObj.getString("c_name");
        System.out.println(in_c_name);
        String in_days = bodyObj.getString("dayInput");
        System.out.println(in_days);
        String in_bName = bodyObj.getString("building_name");
        System.out.println(in_bName);
        String temp_room_no = bodyObj.getString("room_no");
        System.out.println(temp_room_no);


        int in_room_no = Integer.parseInt(temp_room_no);

        /*Creating http headers object to place into response entity the server will return.
        This is what allows us to set the content-type to application/json or any other content-type
        we would want to return */
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
        String crn = "";
        String c_name = "";
        String c_start_time = "";
        String c_end_time = "";
        String mon = "";
        String tue = "";
        String wed = "";
        String thu = "";
        String fri = "";
        String building_name = "";
        int room_no = -1;

        String daySplit[] = in_days.split(" ");
        for (int i = 0; i < daySplit.length; i++) {
            if (daySplit[i].equals("1")) {
                mon = "true";
            }
            if (daySplit[i].equals("2")) {
                tue = "true";
            }
            if (daySplit[i].equals("3")) {
                wed = "true";
            }
            if (daySplit[i].equals("4")) {
                thu = "true";
            }
            if (daySplit[i].equals("5")) {
                fri = "true";
            }
        }


        String queryCondition = "";
        if (in_crn != null) {
            queryCondition = queryCondition + "crn = " + in_crn + " and ";
        }

        if (in_c_name != null) {
            queryCondition = queryCondition + "c_name like '" + in_c_name + "%' and ";
        }
        if (mon != null) {
            boolean monday = Boolean.valueOf(mon);
            queryCondition = queryCondition + "mon is " + monday + " and ";
        }
        if (tue != null) {
            boolean tuesday = Boolean.valueOf(tue);
            queryCondition = queryCondition + "tue is " + tuesday + " and ";

        }
        if (wed != null) {
            boolean wednesday = Boolean.valueOf(wed);
            queryCondition = queryCondition + "wed is " + wednesday + " and ";

        }
        if (thu != null) {
            boolean thursday = Boolean.valueOf(thu);
            queryCondition = queryCondition + "thu is " + thursday + " and ";

        }
        if (fri != null) {
            boolean friday = Boolean.valueOf(fri);
            queryCondition = queryCondition + "fri is " + friday + " and ";

        }
        if (in_bName != null) {
            queryCondition = queryCondition + "building_name like '" + in_bName + "%' and ";
        }

        if (in_room_no != -1) {
            queryCondition = queryCondition + "room_no = " + in_room_no;
        }
        if (queryCondition.substring(queryCondition.length() - 4, queryCondition.length() - 1).equals("and")) {
            queryCondition = queryCondition.substring(0, queryCondition.length() - 4);
        }


        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");

            String query = "select crn, c_name, c_start_time, c_end_time, Mon, Tue, Wed, Thu, Fri, building_name, room_no from Course where ";
            query = query + queryCondition + " order by crn;";

            
            PreparedStatement stmt = null;  //important for safety reasons

            stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                crn =rs.getString("crn");
                c_name = rs.getString("c_name");
                c_start_time = rs.getString("c_start_time");
                c_end_time = rs.getString("c_end_time");
                String days = "";
                if (rs.getBoolean("mon") == true) {
                    days = days + "Monday ";
                }
                if (rs.getBoolean("tue") == true) {
                    days = days + "Tuesday ";
                }
                if (rs.getBoolean("wed") == true) {
                    days = days + "Wednesday ";
                }
                if (rs.getBoolean("thu") == true) {
                    days = days + "Thursday ";
                }
                if (rs.getBoolean("fri") == true) {
                    days = days + "Friday ";
                }
                building_name = rs.getString("building_name");
                room_no = rs.getInt("room_no");

                JSONObject obj = new JSONObject();
                obj.put("crn", crn);
                obj.put("c_name", c_name);
                obj.put("c_start_time", c_start_time);
                obj.put("c_end_time", c_end_time);
                obj.put("days", days);
                obj.put("building_name", building_name);
                obj.put("room_no", room_no);
            
                nameArray.put(obj);
                days ="";
                    }
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }       
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
    }

      @RequestMapping(value = "/advancedEventSearch", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> advancedEventSearch(@RequestBody String body, HttpServletRequest request) {    //requesting to the server
        JSONObject bodyObj = new JSONObject(body);
        String in_club_name = bodyObj.getString("club_name");
        String in_date = bodyObj.getString("date");
        String in_bName = bodyObj.getString("building_name");
        int in_room_no = bodyObj.getInt("room_no");
        /*Creating http headers object to place into response entity the server will return.
        This is what allows us to set the content-type to application/json or any other content-type
        we would want to return */
        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
        String club_name = "";
        String date = "";
        String building_name = "";
        int room_no = -1;
        String e_start_time ="";
        String e_end_time="";
        String supplier= "";
        String event_name = "";



        String queryCondition = "";
        if (in_date != null) {
            queryCondition = queryCondition + "date = '" + in_date + "' and ";
        }

        if (in_club_name != null) {
            queryCondition = queryCondition + "club_name like '" + in_club_name + "%' and ";
        }

        if (in_bName != null) {
            queryCondition = queryCondition + "building_name like '" + in_bName + "%' and ";
        }

        if (in_room_no != -1) {
            queryCondition = queryCondition + "room_no = " + in_room_no;
        }
        if (queryCondition.substring(queryCondition.length() - 4, queryCondition.length() - 1).equals("and")) {
            queryCondition = queryCondition.substring(0, queryCondition.length() - 4);
        }
        
        queryCondition = queryCondition + " order by event_name;";
    

            String query = "select event_name, supplier, e_start_time, e_end_time, date, club_name, building_name, room_no from Event where "
                + queryCondition;

        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");


            
            PreparedStatement stmt = null;  //important for safety reasons

            stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) { //while there's something else next in the resultset
                event_name = rs.getString("event_name");
                supplier = rs.getString("supplier");
                e_start_time = rs.getString("e_start_time");
                e_end_time = rs.getString("e_end_time");
                date = rs.getDate("date").toString();
                club_name = rs.getString("club_name");
                building_name = rs.getString("building_name");
                room_no = rs.getInt("room_no");


                JSONObject obj = new JSONObject();
                obj.put("event_name", event_name);
                obj.put("supplier", supplier);
                obj.put("e_start_time", e_start_time);
                obj.put("e_end_time", e_end_time);
                rs.getDate("date");
                obj.put("club_name", club_name);
                obj.put("building_name", building_name);
                obj.put("room_no", room_no);
            
                nameArray.put(obj);
                    }
        } catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }       
        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
    }

@RequestMapping(value = "/courseBook", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> courseBook(@RequestBody String body, HttpServletRequest request) {
        //String clubname = request.getParameter("clubname");
        JSONObject bodyObject = new JSONObject(body);
        String in_crn= bodyObject.getString("crn");
        String in_c_name = bodyObject.getString("c_name");
        String in_c_start_time = bodyObject.getString("c_start_time");
        String in_c_end_time = bodyObject.getString("c_end_time");
        String in_days = bodyObject.getString("dayInput");
        String in_building_name = bodyObject.getString("building_name");
        int in_room_no = bodyObject.getInt("room_no");
        int in_fac_id =  bodyObject.getInt("fac_id");
        System.out.println(in_crn);

     

        HttpHeaders responseHeaders = new HttpHeaders(); 
        responseHeaders.set("Content-Type", "application/json");
        Connection conn = null;
        JSONArray nameArray = new JSONArray();
            String crn = "";
            String c_name = "";
            String c_start_time = "";
            String c_end_time = "";
            String dayInput = "";
            String building_name = "";
            int room_no = -1;
            int fac_id = -1;

            String mon = "";
            String tue = "";
            String wed = "";
            String thu = "";
            String fri = "";

            System.out.println(dayInput);

                String daySplit[] = in_days.split(" ");
            for (int i = 0; i < daySplit.length; i++) {
                if (daySplit[i].equals("1")) {
                    mon = "true";
                }
                if (daySplit[i].equals("2")) {
                    tue = "true";
                }
                if (daySplit[i].equals("3")) {
                    wed = "true";
                }
                if (daySplit[i].equals("4")) {
                    thu = "true";
                }
                if (daySplit[i].equals("5")) {
                    fri = "true";
                }
            }


            // move this???
            
            String message = "";

            // check if building exists
            if (!in_building_name.equals("Johnson") && !in_building_name.equals("Fowler") && !in_building_name.equals("Swan")
                    && !in_building_name.equals("Mosher") && !in_building_name.equals("Norris")) {
                 JSONObject obj = new JSONObject();

                message = ("That building does not exist!");
                 obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
            }

            // check if room_no exists
            if (in_room_no != 101 && in_room_no != 102 && in_room_no != 103 && in_room_no != 104 && in_room_no != 105 && in_room_no != 201
                    && in_room_no != 202 && in_room_no != 203 && in_room_no != 204 && in_room_no != 205) {
               
                message = ("That room_no does not exist!");
                JSONObject obj = new JSONObject();

                 obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
            }
       

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
            PreparedStatement ps = null;  //important for safety reasons
           // condition 2: check if student is president id
            String deptQuery = "select head from Faculty where fac_id = ?;";
            ps = conn.prepareStatement(deptQuery);
            ps.setInt(1, in_fac_id);
            Boolean checker = false;
            ResultSet rs = ps.executeQuery();
            // System.out.println(ps);
            while (rs.next()) {
                checker = rs.getBoolean("head");
            }
            if (checker != true) {
                message = ("Faculty ID, " + in_fac_id + ", is not a department head!");
               JSONObject obj = new JSONObject();

                 obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
            }

            String crnChecker = null;
            String courseQuery = "select crn from Course where crn = ?;";
            ps = conn.prepareStatement(courseQuery);
            ps.setString(1, in_crn);
            rs = ps.executeQuery();
            while (rs.next()) {
                crnChecker = rs.getString("crn");
            }
            if (crnChecker != null) {
                message = (crn + " already exists!");
                JSONObject obj = new JSONObject();

                 obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                
            }

       // check existing event
            java.util.Hashtable<Integer, String> dayIndex = new java.util.Hashtable<Integer, String>();
            java.util.Hashtable<Integer, Integer> monthNumDays = new java.util.Hashtable<Integer, Integer>();

            // total number of days corresponding to the month
            monthNumDays.put(1, 31);
            monthNumDays.put(2, 59);
            monthNumDays.put(3, 90);
            monthNumDays.put(4, 120);
            monthNumDays.put(5, 151);
            monthNumDays.put(6, 181);
            monthNumDays.put(7, 212);
            monthNumDays.put(8, 243);
            monthNumDays.put(9, 273);
            monthNumDays.put(10, 304);
            monthNumDays.put(11, 334);

            // key for finding what day of the week it is
            dayIndex.put(1, "Tuesday");
            dayIndex.put(2, "Wednesday");
            dayIndex.put(3, "Thursday");
            dayIndex.put(4, "Friday");
            dayIndex.put(5, "Saturday");
            dayIndex.put(6, "Sunday");
            dayIndex.put(0, "Monday");


            String eventQuery = "select event_name, date, e_start_time, e_end_time from Event where building_name = ? and room_no = ?;";
            ps = conn.prepareStatement(eventQuery);
            ps.setString(1, in_building_name);
            ps.setInt(2, in_room_no);
            rs = ps.executeQuery();
            java.sql.Date dateChecker;
            while (rs.next()) {
                dateChecker = rs.getDate("date");
                String[] splitter = String.valueOf(dateChecker).split("-");
                int calculate = -1;
                if (splitter[1].equals("01")) {
                    calculate = (Integer.parseInt(splitter[2])) % 7;
                } else {
                    calculate = (monthNumDays.get(Integer.parseInt(splitter[1]) - 1) + Integer.parseInt(splitter[2]))
                            % 7;
                }
                // get what day it is and parse it into 3 chars
                String day = dayIndex.get(calculate).substring(0, 3);
                if (day.equals("Mon")) {
                    if (Boolean.valueOf(mon) == true) {
                        if (!(compareTime(in_c_end_time, rs.getString("e_start_time"))
                                || compareTime(rs.getString("e_end_time"), in_c_start_time))) {
                           message = ("Sorry! The event, " + rs.getString("event_name") + ", has been booked at "
                                            + rs.getString("e_start_time") + " from " + rs.getString("e_end_time"));
                           JSONObject obj = new JSONObject();

                                
                                 obj.put("message", message);
                                    
                                nameArray.put(obj);
                                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                        }
                    }
                } else if (day.equals("Tue")) {
                    if (Boolean.valueOf(tue) == true) {
                        if (!(compareTime(in_c_end_time, rs.getString("e_start_time"))
                                || compareTime(rs.getString("c_end_time"), in_c_start_time))) {
                           message = ("Sorry! The event, " + rs.getString("event_name") + ", has been booked at "
                                            + rs.getString("e_start_time") + " from " + rs.getString("e_end_time"));
                           JSONObject obj = new JSONObject();

                             obj.put("message", message);
                    
                         nameArray.put(obj);
                        return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                        }
                    }
                } else if (day.equals("Wed")) {
                    if (Boolean.valueOf(wed) == true) {
                        if (!(compareTime(in_c_end_time, rs.getString("e_start_time"))
                                || compareTime(rs.getString("e_end_time"), in_c_start_time))) {
                            message = ("Sorry! The event, " + rs.getString("event_name") + ", has been booked at "
                                            + rs.getString("e_start_time") + " from " + rs.getString("e_end_time"));
                            JSONObject obj = new JSONObject();

                            obj.put("message", message);
                    
                             nameArray.put(obj);
                             return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                        }
                    }
                } else if (day.equals("Thu")) {
                    if (Boolean.valueOf(thu) == true) {
                        if (!(compareTime(in_c_end_time, rs.getString("e_start_time"))
                                || compareTime(rs.getString("e_end_time"), in_c_start_time))) {
                            message = ("Sorry! The event, " + rs.getString("event_name") + ", has been booked at "
                                            + rs.getString("e_start_time") + " from " + rs.getString("e_end_time"));
                            JSONObject obj = new JSONObject();

                            obj.put("message", message);
                    
                nameArray.put(obj);
                return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                        }
                    }
                } else if (day.equals("Fri")) {
                    if (Boolean.valueOf(fri) == true) {
                        if (!(compareTime(in_c_end_time, rs.getString("e_start_time"))
                                || compareTime(rs.getString("e_end_time"), in_c_start_time))) {
                            message = ("Sorry! The event, " + rs.getString("event_name") + ", has been booked at "
                                            + rs.getString("e_start_time") + " from " + rs.getString("e_end_time"));
                            JSONObject obj = new JSONObject();

                            obj.put("message", message);
                    
                            nameArray.put(obj);
                             return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);
                        }
                    }
                }
            }


                // prepare to set up for adding the insert method
            java.util.Date todayDate = new java.util.Date();
            long time = todayDate.getTime();
            Timestamp ts = new Timestamp(time);

            // book building
            String insertBuilding = "Insert INTO Building (time_stamp, building_name, room_no, max_cap, stu_id, fac_id) values (?,?,?,?,?,?)";
            ps = conn.prepareStatement(insertBuilding);
            ps.setTimestamp(1, ts);
            ps.setString(2, in_building_name);
            ps.setInt(3, in_room_no);
            String roomNo = String.valueOf(room_no);
            if (roomNo.charAt(0) == '1') {
                ps.setInt(4, 75);
            } else {
                ps.setInt(4, 50);
            }
            ps.setString(5, null);
            ps.setInt(6, in_fac_id);
            // System.out.println(ps);
            ps.executeUpdate();

            // book course
            String insertCourse = "Insert INTO Course (crn, c_name, c_start_time, c_end_time, Mon, Tue, Wed, Thu, Fri, time_stamp, building_name, room_no, fac_id) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(insertCourse);
            ps.setString(1, in_crn);
            ps.setString(2, in_c_name);
            ps.setString(3, in_c_start_time);
            ps.setString(4, in_c_end_time);
            ps.setBoolean(5, Boolean.valueOf(mon));
            ps.setBoolean(6, Boolean.valueOf(tue));
            ps.setBoolean(7, Boolean.valueOf(wed));
            ps.setBoolean(8, Boolean.valueOf(thu));
            ps.setBoolean(9, Boolean.valueOf(fri));
            ps.setTimestamp(10, ts);
            ps.setString(11, in_building_name);
            ps.setInt(12, in_room_no);
            ps.setInt(13, in_fac_id);
            // System.out.println(ps);
            ps.executeUpdate();

                    // insert cin
            String insertCin = "Insert INTO c_in (crn, time_stamp, building_name, room_no) values (?,?,?,?)";
            ps = conn.prepareStatement(insertCin);
            ps.setString(1, in_crn);
            ps.setTimestamp(2, ts);
            ps.setString(3, in_building_name);
            ps.setInt(4, in_room_no);
            // System.out.println(ps);
            ps.executeUpdate();


                }


            
    
         catch (SQLException e ) {
            e.printStackTrace();
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
            message =("Your course has been successfully booked!");
                JSONObject obj = new JSONObject();


                obj.put("message", message);
                    nameArray.put(obj);                    
                    return new ResponseEntity(nameArray.toString(), responseHeaders, HttpStatus.OK);    
    }




 @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
    public ResponseEntity<String> deleteEvent(@RequestBody String body, HttpServletRequest request) {
        
        JSONObject bodyObject = new JSONObject(body);
        String in_ename = bodyObject.getString("event_name");
        
          HttpHeaders responseHeaders = new HttpHeaders(); 
           responseHeaders.set("Content-Type", "application/json");
        
        Connection conn = null;
        PreparedStatement ps = null;
        String message = "";
        // userInput
    //  String in_ename = "Money Management 101";

        // if no event is found then do nothing else or do the 2 delete statements
        String findEvent = "select event_name, time_stamp, building_name, room_no from Event where event_name = ?;";

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReserveOxy", "root", "");
            ps = conn.prepareStatement(findEvent);
            ps.setString(1, in_ename);
            ResultSet rs = ps.executeQuery();
            String ename = null;
            String tstamp = "";
            String bname = "";
            int room = -1;
            while (rs.next()) {
                ename = rs.getString("event_name");
                tstamp = rs.getString("time_stamp");
                bname = rs.getString("building_name");
                room = rs.getInt("room_no");

            }
            if (ename == null) {
                JSONObject obj = new JSONObject();
                message = in_ename + " does not exist!";
                obj.put("message", message);

                return new ResponseEntity(obj.toString(), responseHeaders, HttpStatus.OK);
            }
            String deleteEvent = "Delete from Event where event_name = ?;";
            String deleteBuilding = "Delete from Building where time_stamp = ? and building_name = ? and room_no = ?;";

            ps = conn.prepareStatement(deleteEvent);
            ps.setString(1, in_ename);
            //System.out.println(ps);
            ps.executeUpdate();

            ps = conn.prepareStatement(deleteBuilding);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(tstamp));
            ps.setString(2, bname);
            ps.setInt(3, room);
            //System.out.println(ps);
            ps.executeUpdate();

       
            ps.close();
            conn.close();
        } 

         catch (SQLException e ) {
            return new ResponseEntity(e.toString(), responseHeaders, HttpStatus.OK);
        } finally {
            try {
                if (conn != null) { conn.close(); }
            }catch(SQLException se) {
            }  
        }
        
             JSONObject obj = new JSONObject();
            message = in_ename + " has been successfully deleted!";
            obj.put("message", message);

            return new ResponseEntity(obj.toString(), responseHeaders, HttpStatus.OK);






    }




    
    //Helper method to convert bytes into hexadecimal
    public static String bytesToHex(byte[] in) {
        StringBuilder builder = new StringBuilder();
        for(byte b: in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }



    // check if time1 < time2
    public static boolean compareTime(String time1, String time2) {
        String one = "";
        String two = "";
        for (int i = 0; i < time1.length(); i++) {
            if (time1.charAt(i) != ':')
                one = one + time1.charAt(i);
        }

        for (int i = 0; i < time2.length(); i++) {
            if (time2.charAt(i) != ':')
                two = two + time2.charAt(i);
        }
        return (Integer.parseInt(two) > Integer.parseInt(one));
    }

}



