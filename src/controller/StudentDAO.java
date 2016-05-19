package controller;

import java.io.IOException;
import java.sql.*;
//import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ResultBean;
import beans.StudentBean;
import services.DataProcessor;

/**
 * Servlet implementation class StudentDAO
 * 
 * @author Viraj Shah
 * 
 */
public class StudentDAO extends HttpServlet {
	private static final long serialVersionUID = 102831973239L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		StudentBean student = getStudentDetails(request);
//		DataProcessor service = new DataProcessor();
//		ResultBean db = service.calculate(processDataField(student.getData()));
		RequestDispatcher dispatcher;
//		String address;
//		String connectionString = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";
//		String user = "vshah13";
//		String pass = "oaftoo";
//		Connection conn = createDbConnection(connectionString, user, pass);

		// Check value of mean
//		if (db.getMean() > 90.0d)
//			address = "WinnerAcknowledgement.jsp";
//		else
//			address = "SimpleAcknowledgement.jsp";

		// Setting session attributes
		request.setAttribute("student", student);
		request.setAttribute("data", db);

		// Inserting into Database
		insertStudentToDb(conn, student, db);
		ResultSet rs = retrieveStudents(conn);
		ResultSetMetaData rsMeta = retrieveMetadata(rs);
		request.setAttribute("rSet", rs);
		request.setAttribute("rSetMeta", rsMeta);

		// Forwarding to JSP
		dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String connectionString = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";
		String user = "vshah13";
		String pass = "oaftoo";
		Connection conn = createDbConnection(connectionString, user, pass);
		String studentId = request.getParameter("student_button");
		ResultSet r = (ResultSet) request.getAttribute("rSet");
		RequestDispatcher reqDisp = null;
		StudentBean student = new StudentBean();

		// Check the student id selected
		if (studentId != null && !studentId.isEmpty()) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				r = stmt.executeQuery("SELECT * FROM STUDENT WHERE ID=\'" + studentId + "\'");
			} catch (SQLException e) {
				System.out.println("Select query error...");
				e.printStackTrace();
			}

			// Set StudentBean with the returned values of the selected student

			try {
				while (r.next()) {
					student.setName(r.getString("name"));
					student.setAddress(r.getString("address"));
					student.setZip(r.getString("zip"));
					student.setCity(r.getString("city"));
					student.setState(r.getString("state"));
					student.setPhone(r.getString("phone"));
					student.setEmail(r.getString("email").toString());
					student.setUrl(r.getString("url").toString());
					student.setDate(r.getString("SDATE"));
					student.setGradMonth(r.getString("GRADMONTH"));
					student.setGradYear(r.getString("GRADYEAR"));
					student.setId(r.getString("ID"));
					student.setPreference(r.getString("PREFERENCE"));
					student.setFound(r.getString("FOUND"));
					student.setRecommend(r.getString("LIKELY"));
					student.setComment(r.getString("SCOMMENT").toString());
				}
			} catch (SQLException e) {
				System.out.println("Error getting string from result set..");
				e.printStackTrace();
			}

			request.setAttribute("studBean", student);
			reqDisp = request.getRequestDispatcher("StudentDetails.jsp");
			reqDisp.forward(request, response);

		} else {
			reqDisp = request.getRequestDispatcher("NoSuchStudent.jsp");
			reqDisp.forward(request, response);
		}
	}

	// Getting data from form
	protected StudentBean getStudentDetails(HttpServletRequest req) {
		StudentBean student = new StudentBean();
		student.setName(req.getParameter("name"));
		student.setAddress(req.getParameter("address"));
		student.setZip(req.getParameter("zip"));
		student.setCity(req.getParameter("city"));
		student.setState(req.getParameter("state"));
		student.setPhone(req.getParameter("phone"));
		student.setEmail(req.getParameter("email").toString());
		student.setUrl(req.getParameter("url").toString());
		student.setDate(req.getParameter("date_of_survey"));
		student.setGradMonth(req.getParameter("grad_month"));
		student.setGradYear(req.getParameter("grad_year"));
		student.setId(req.getParameter("id"));
		student.setData(req.getParameter("data"));
		student.setFound(req.getParameter("found"));
		student.setRecommend(req.getParameter("recommend"));
		student.setComment(req.getParameter("comments").toString());

		// Setting multiple preference key values
		String[] pref = req.getParameterValues("preference");
		String str = new String("");
		str = pref[0];
		for (int i = 1; i < pref.length; i++)
			str = str + " " + pref[i];
		student.setPreference(str);

		return student;
	}

	// Process the data from a single string into an array of type double
//	protected double[] processDataField(String form_data) {
//		String[] split_data = form_data.split(",");
//		double[] data = new double[10];
//		for (int i = 0; i < split_data.length; i++)
//			data[i] = Double.parseDouble(split_data[i]);
//		return data;
//	}

	// Create the connection to GMU database
//	protected Connection createDbConnection(String connString, String user, String pwd) {
//		Connection con = null;
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			con = DriverManager.getConnection(connString, user, pwd);
//
//		} catch (ClassNotFoundException e) {
//			System.out.println("JDBC connection problem...");
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.out.println("DB connection problem...");
//			e.printStackTrace();
//		}
//		return con;
//	}

	// Insert student data into a database
//	protected void insertStudentToDb(Connection con, StudentBean s, ResultBean d) {
//		String insertStudentQuery = "INSERT INTO STUDENT " + "VALUES('" + s.getId() + "','" + s.getName() + "','"
//				+ s.getAddress() + "','" + s.getZip() + "','" + s.getCity() + "','" + s.getState() + "','"
//				+ s.getPhone() + "','" + s.getEmail() + "','" + s.getUrl() + "','" + s.getDate() + "','"
//				+ s.getGradMonth() + "','" + s.getGradYear() + "','" + s.getPreference() + "','" + s.getFound() + "','"
//				+ s.getRecommend() + "','" + s.getComment() + "','" + d.getMean() + "','" + d.getSd() + "')";
//
//		try {
//			Statement stmt = con.createStatement();
//			stmt.executeUpdate(insertStudentQuery);
//		} catch (SQLException e) {
//			System.out.println("Unable to insert student details...");
//			e.printStackTrace();
//		}
//	}

	// Retrieve student table data
	protected ResultSet retrieveStudents(Connection con) {
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM STUDENT");
		} catch (SQLException e) {
			System.out.println("Unable to retrieve data...");
			e.printStackTrace();
		}
		return rs;
	}

	protected ResultSetMetaData retrieveMetadata(ResultSet rs) {
		ResultSetMetaData rm = null;
		try {
			ResultSetMetaData rsMeta = rs.getMetaData();
		} catch (SQLException e) {
			System.out.println("Error generating metadata");
			e.printStackTrace();
		}

		return rm;
	}
}
