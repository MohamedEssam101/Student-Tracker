package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	
	//create a reference to the datasource
	private DataSource dataSource;
	// someone create this method and  and they will pass  reference to our datasource object
	public StudentDBUtil (DataSource theDataSource) {
		dataSource = theDataSource;
	}	
	// we will get a list of student from database and return it 
	public List<Student> getStudent() throws Exception {
		
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
	try {	
		// get a connection
		Class.forName("com.mysql.cj.jdbc.Driver");
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker","root","m@hamed615");
		
		// create sql statment
		String sql = "select * from student order by last_name";
		myStmt = myConn.createStatement();
		
		// execute the query
		myRs = myStmt.executeQuery(sql);
		// process result	
		while(myRs.next()) {
			//retrieve data from result set row
				//actual coloumn name from data base
			int studentID = myRs.getInt("id");
			String firstName = myRs.getString("first_name");
			String lastName = myRs.getString("last_name");
			String email = myRs.getString("email");
			
			//create new student object
			Student TempStudent = new Student(studentID, firstName, lastName, email);
			// add it to the list of students
			students.add(TempStudent);
	}

		
		return students;
	}
	finally {
		// close JDBC objects
		close(myConn, myStmt, myRs);
	}		
}
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs !=null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close(); 	//make it avaiable to another one to use the connection pool.
								// no close
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	public void addStudent(Student theStudent) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			// get db connection
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker","root","m@hamed615");
			
			// create sql for insert
			String sql = "insert into student "
					   + "(first_name, last_name, email) "
					   + "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the student
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}
	public Student getStudent(String theStudentId) throws Exception{

		Student theStudent = null;	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int StudentId;
		try {
			//convert student id to int
				StudentId = Integer.parseInt(theStudentId);
			//get connection to database
				Class.forName("com.mysql.cj.jdbc.Driver");
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker","root","m@hamed615");
			
			// create sql to get selected student
				String sql = "select * from student where id =?" ;
			//create prepared statement
					myStmt = myConn.prepareStatement(sql);
			//set parameters to know which student to get
				myStmt.setInt(1, StudentId);
			// execute that query then i will get the result set
				myRs = myStmt.executeQuery();
			// retrieve data from result set row
				if(myRs.next()) {
					String firstName = myRs.getString("first_name"); // actual column name in database
					String lastName = myRs.getString("last_name");
					String email = myRs.getString("email");
					
					// used the Studentid during construction
					theStudent = new Student(StudentId, firstName, lastName, email);
		}
				else {
					throw new Exception("Could not find a student id" + StudentId);
				}
		return theStudent;
				}
		finally {
			//clean up jdbc object
			close(myConn, myStmt, myRs);
		}
	}
	public void updateStudent(Student theStudent) throws Exception 
	{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {		
		//get connection to database
			Class.forName("com.mysql.cj.jdbc.Driver");
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker","root","m@hamed615");
	
			//create sql update statement
				String sql = "update student "
						+"set first_name=?, last_name=?, email=? "
						+"where id=?";
			
		// prepare that statement
			myStmt = myConn.prepareStatement(sql);
			
		// set params to that statement
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getStudentID());
			
			// execute  sql statement that will perform update on the database
			myStmt.execute();
		}
			finally {
				close(myConn, myStmt, null);
			}	
	}
	public void deleteStudent(String theStudentId) throws Exception
	{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			//convert student id to int
				int studentId = Integer.parseInt(theStudentId);
				
			//get connection to database
				Class.forName("com.mysql.cj.jdbc.Driver");
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker","root","m@hamed615");
		
			//create sql to delete student
				String sql = "delete from student where id=?";
			
			//prepare statement
				myStmt = myConn.prepareStatement(sql);
				
			//set params
				myStmt.setInt(1, studentId);
				
			//execute sql statement (which perform the delete on the database)
				myStmt.execute();
		}
		finally {
			//clean up jdbc code 
			close(myConn, myStmt, null);
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}