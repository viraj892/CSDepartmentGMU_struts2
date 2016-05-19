package controller;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import beans.ResultBean;
import beans.StudentBean;
import services.DataProcessor;

public class StudentBeanAction {
	private String name;
	private String address;
	private String zip;
	private String city;
	private String state;
	private String phone;
	private String email;
	private String url;
	private String date_of_survey;
	private String grad_month;
	private String grad_year;
	private String data;
	private String id;
	private String preference;
	private String found;
	private String recommend;
	private String comments;
	private ResultSet rs;
	private String student_id_button;

	public String process() {
		DataProcessor service = new DataProcessor();
		ResultBean db = service.calculate(processDataField(data));
		HttpServletRequest request = ServletActionContext.getRequest();
		String result;
		String connectionString = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";
		String user = "vshah13";
		String pass = "oaftoo";
		Connection conn = createDbConnection(connectionString, user, pass);
		List<String> ids = new ArrayList<String>();

		insertStudentToDb(conn, db);
		ids = retrieveStudentIds(conn);
		HttpSession session = request.getSession();

		// Checking mean to decide on the resulting view
		if (db.getMean() > 90.0d)
			result = "WinnerAcknowledgement.jsp";
		else
			result = "SimpleAcknowledgement.jsp";

		session.setAttribute("ids", ids);
		session.setAttribute("data", db);
		return result;
	}

	// Action to handle request from result page and return student details
	public String populateStudentDetails() {
		String connectionString = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";
		String user = "vshah13";
		String pass = "oaftoo";
		ResultSet r;
		StudentBean student = new StudentBean();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Connection conn = createDbConnection(connectionString, user, pass);

		// Check the student id selected
		if (student_id_button != null && !student_id_button.isEmpty()) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				r = stmt.executeQuery("SELECT * FROM STUDENT WHERE ID=\'" + student_id_button + "\'");

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
				System.out.println("Select query error...");
				e.printStackTrace();
			}

			session.setAttribute("studBean", student);
			return "found";
		} else
			return "not_found";
	}

	// Insert student data into a database
	protected void insertStudentToDb(Connection con, ResultBean d) {
		String insertStudentQuery = "INSERT INTO STUDENT " + "VALUES('" + id + "','" + name + "','" + address + "','"
				+ zip + "','" + city + "','" + state + "','" + phone + "','" + email + "','" + url + "','"
				+ date_of_survey + "','" + grad_month + "','" + grad_year + "','" + preference + "','" + found + "','"
				+ recommend + "','" + comments + "','" + d.getMean() + "','" + d.getSd() + "')";

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(insertStudentQuery);
		} catch (SQLException e) {
			System.out.println("Unable to insert student details...");
			e.printStackTrace();
		}
	}

	// Process the data from a single string into an array of type double
	protected double[] processDataField(String form_data) {
		String[] split_data = form_data.split(",");
		double[] data = new double[10];
		for (int i = 0; i < split_data.length; i++)
			data[i] = Double.parseDouble(split_data[i]);
		return data;
	}

	// Create a connection to a specified oracle database
	protected Connection createDbConnection(String connString, String user, String pwd) {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(connString, user, pwd);

		} catch (ClassNotFoundException e) {
			System.out.println("JDBC connection problem...");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB connection problem...");
			e.printStackTrace();
		}
		return con;
	}

	// Retrieve student table data
	protected List<String> retrieveStudentIds(Connection con) {
		ResultSet rs = null;
		List<String> ids = new ArrayList<String>();
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT id FROM STUDENT");

			while (rs.next()) {
				ids.add(rs.getString("id"));
			}
		} catch (SQLException e) {
			System.out.println("Unable to retrieve data...");
			e.printStackTrace();
		}

		return ids;
	}

	// Getters and setters for all form data
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url.toString();
	}

	public String getDate_of_survey() {
		return date_of_survey;
	}

	public void setDate_of_survey(String date_of_survey) {
		this.date_of_survey = date_of_survey.toString();
	}

	public String getGrad_month() {
		return grad_month;
	}

	public void setGrad_month(String grad_month) {
		this.grad_month = grad_month;
	}

	public String getGrad_year() {
		return grad_year;
	}

	public void setGrad_year(String grad_year) {
		this.grad_year = grad_year;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPreference() {
		return preference;
	}

	/*
	 * setting preference class variable to a concatenated string instead of
	 * string array
	 */
	public void setPreference(String[] preference) {
		String[] pref = preference;
		String str = new String("");
		str = pref[0];
		for (int i = 1; i < pref.length; i++)
			str = str + " " + pref[i];
		this.preference = str;
	}

	public String getFound() {
		return found;
	}

	public void setFound(String found) {
		this.found = found;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public String getStudent_id_button() {
		return student_id_button;
	}

	public void setStudent_id_button(String student_id_button) {
		this.student_id_button = student_id_button;
	}
}
