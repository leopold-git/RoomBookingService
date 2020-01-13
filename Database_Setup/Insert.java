import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Insert {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/ReserveOxy";

	static final String USER = "root";
	static final String PASSWORD = "Occidental!";

	private static List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}
		return values;
	}

	public static void insertStudent() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> stuRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("student.csv"));) {
				while (scan.hasNextLine()) {
					stuRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the Student table.");
			for (int i = 0; i < stuRecords.size(); i++) {
				String insertTableSQL = "Insert INTO Student (stu_id, stu_name, email, major) values (?, ?, ?,?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setInt(1, Integer.parseInt(stuRecords.get(i).get(0)));
				ps.setString(2, stuRecords.get(i).get(1));
				ps.setString(3, stuRecords.get(i).get(2));
				ps.setString(4, stuRecords.get(i).get(3));
				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insertDept() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> deptRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("dept.csv"));) {
				while (scan.hasNextLine()) {
					deptRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the Department table.");
			for (int i = 0; i < deptRecords.size(); i++) {
				String insertTableSQL = "Insert INTO Dept (dept_name, d_budget) values (?, ?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setString(1, deptRecords.get(i).get(0));
				ps.setDouble(2, Double.parseDouble(deptRecords.get(i).get(1)));
				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insertFaculty() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> facRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("faculty.csv"));) {
				while (scan.hasNextLine()) {
					facRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the Faculty table.");
			for (int i = 0; i < facRecords.size(); i++) {
				String insertTableSQL = "Insert INTO Faculty (fac_id, fac_name, dept_name, head) values (?, ?, ?,?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setInt(1, Integer.parseInt(facRecords.get(i).get(0)));
				ps.setString(2, facRecords.get(i).get(1));
				ps.setString(3, facRecords.get(i).get(2));
				ps.setBoolean(4, Boolean.valueOf(facRecords.get(i).get(3)));
				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insertClub() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> clubRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("club.csv"));) {
				while (scan.hasNextLine()) {
					clubRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the Club table.");
			for (int i = 0; i < clubRecords.size(); i++) {
				String insertTableSQL = "Insert INTO Club (club_name, club_category, c_budget, presID, advisor_id, dept_name) values (?, ?, ?,?,?,?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setString(1, clubRecords.get(i).get(0));
				ps.setString(2, clubRecords.get(i).get(1));
				ps.setDouble(3, Double.parseDouble(clubRecords.get(i).get(2)));
				ps.setInt(4, Integer.parseInt(clubRecords.get(i).get(3)));
				ps.setInt(5, Integer.parseInt(clubRecords.get(i).get(4)));
				ps.setString(6, clubRecords.get(i).get(5));

				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insertMembership() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> memRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("membership.csv"));) {
				while (scan.hasNextLine()) {
					memRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the Membership table.");
			for (int i = 0; i < memRecords.size(); i++) {
				String insertTableSQL = "Insert INTO Membership (stu_id, club_name) values (?, ?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setInt(1, Integer.parseInt(memRecords.get(i).get(0)));
				ps.setString(2, memRecords.get(i).get(1));
				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insertBuilding() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> buildRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("building.csv"));) {
				while (scan.hasNextLine()) {
					buildRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the Building table.");
			for (int i = 0; i < buildRecords.size(); i++) {
				String insertTableSQL = "Insert INTO Building (time_stamp, building_name, room_no, max_cap, stu_id, fac_id) values (?,?,?,?,?,?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setTimestamp(1, Timestamp.valueOf(buildRecords.get(i).get(0)));
				ps.setString(2, buildRecords.get(i).get(1));
				ps.setInt(3, Integer.parseInt(buildRecords.get(i).get(2)));
				ps.setInt(4, Integer.parseInt(buildRecords.get(i).get(3)));
				if (buildRecords.get(i).get(4).equals("null"))
					ps.setNull(5, java.sql.Types.INTEGER);
				else
					ps.setInt(5, Integer.parseInt(buildRecords.get(i).get(4)));
				ps.setInt(6, Integer.parseInt(buildRecords.get(i).get(5)));
				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insertCourse() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> courseRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("course.csv"));) {
				while (scan.hasNextLine()) {
					courseRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the Course table.");
			for (int i = 0; i < courseRecords.size(); i++) {
				String insertTableSQL = "Insert INTO Course (crn, c_name, c_start_time, c_end_time, Mon, Tue, Wed, Thu, Fri, time_stamp, building_name, room_no, fac_id) values (?, ?, ?,?,?,?, ?, ?,?,?, ?, ?,?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setString(1, courseRecords.get(i).get(0));
				ps.setString(2, courseRecords.get(i).get(1));
				ps.setString(3, courseRecords.get(i).get(2));
				ps.setString(4, courseRecords.get(i).get(3));
				ps.setBoolean(5, Boolean.valueOf(courseRecords.get(i).get(4)));
				ps.setBoolean(6, Boolean.valueOf(courseRecords.get(i).get(5)));
				ps.setBoolean(7, Boolean.valueOf(courseRecords.get(i).get(6)));
				ps.setBoolean(8, Boolean.valueOf(courseRecords.get(i).get(7)));
				ps.setBoolean(9, Boolean.valueOf(courseRecords.get(i).get(8)));
				ps.setTimestamp(10, Timestamp.valueOf(courseRecords.get(i).get(9)));
				ps.setString(11, courseRecords.get(i).get(10));
				ps.setInt(12, Integer.parseInt(courseRecords.get(i).get(11)));
				ps.setInt(13, Integer.parseInt(courseRecords.get(i).get(12)));

				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insertEvent() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> eventRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("event.csv"));) {
				while (scan.hasNextLine()) {
					eventRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the Event table.");
			for (int i = 0; i < eventRecords.size(); i++) {
				String insertTableSQL = "Insert INTO Event (event_name, supplier, e_start_time, e_end_time, date, club_name, time_stamp, building_name, room_no, presID, fac_id) values (?, ?, ?,?, ?, ?,?,?, ?, ?,?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setString(1, eventRecords.get(i).get(0));
				ps.setString(2, eventRecords.get(i).get(1));
				ps.setString(3, eventRecords.get(i).get(2));
				ps.setString(4, eventRecords.get(i).get(3));
				ps.setDate(5, Date.valueOf(eventRecords.get(i).get(4)));
				ps.setString(6, eventRecords.get(i).get(5));
				ps.setTimestamp(7, Timestamp.valueOf(eventRecords.get(i).get(6)));
				ps.setString(8, eventRecords.get(i).get(7));
				ps.setInt(9, Integer.parseInt(eventRecords.get(i).get(8)));
				ps.setInt(10, Integer.parseInt(eventRecords.get(i).get(9)));
				ps.setInt(11, Integer.parseInt(eventRecords.get(i).get(10)));

				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insertCin() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			List<List<String>> cinRecords = new ArrayList<>();
			try (Scanner scan = new Scanner(new File("cin.csv"));) {
				while (scan.hasNextLine()) {
					cinRecords.add(getRecordFromLine(scan.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Insert into the c_in table.");
			for (int i = 0; i < cinRecords.size(); i++) {
				String insertTableSQL = "Insert INTO c_in (crn, time_stamp, building_name, room_no) values (?, ?,?, ?)";
				ps = conn.prepareStatement(insertTableSQL);
				ps.setString(1, cinRecords.get(i).get(0));
				ps.setTimestamp(2, Timestamp.valueOf(cinRecords.get(i).get(1)));
				ps.setString(3, cinRecords.get(i).get(2));
				ps.setInt(4, Integer.parseInt(cinRecords.get(i).get(3)));

				// System.out.println(ps);
				ps.executeUpdate();
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// student dept faculty club membership building course event cin
		insertStudent();
		insertDept();
		insertFaculty();
		insertClub();
		insertMembership();
		insertBuilding();
		insertCourse();
		insertEvent();
		insertCin();

	}
}
