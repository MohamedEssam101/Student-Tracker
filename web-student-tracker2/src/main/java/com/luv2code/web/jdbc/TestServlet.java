package com.luv2code.web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Step 1:  Set up the printwriter
				PrintWriter out = response.getWriter();
				response.setContentType("text/plain");
				
				// Step 2:  Get a connection to the database
				Connection myConn = null;
				Statement myStmt = null;
				ResultSet myRs = null;
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker","root","m@hamed615");
					
					// Step 3:  Create a SQL statements
					myStmt = myConn.createStatement();
					
					// Step 4:  Execute SQL query
					myRs = myStmt.executeQuery("select * from student");
					
					// Step 5:  Process the result set
					while (myRs.next()) {
						String email = myRs.getString("email");
						out.println(email);
					}
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
			}

}

