package com.luv2code.web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       //reference to StudentDBUtil
	private StudentDBUtil StudentDBUtil;
	//inject the datasource for me
	//THE TOMCAT server will inject that connection pool object and 
	//assign it here to this variable datasource
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	// init method will be called by the app server when this servlet is initialized
	@Override
	public void init() throws ServletException {
		super.init();
		
		//create instance of our student DB util and pass in the connection pool /datasource object		
		try {
			//data member we defined , and we assigining it in here
			// dataSource is that resource injection item that will create our studentdbutil
			StudentDBUtil = new StudentDBUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listStudent(request, response);
				break;
				
			case "ADD":
				addStudent(request, response);
				break;
				
			case "LOAD":
				loadStudent(request,response);
				break;
			
			case "UPDATE":
				updateStudent(request,response);
				break;
			case "DELETE":
				deleteStudent(request,response);
				break;
			default:
				listStudent(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{

		//read student id from form data
			String theStudentId = request.getParameter("studentId");
			
		// delete student from the database
			StudentDBUtil.deleteStudent(theStudentId);
		
		// send them back too "list students" page
			listStudent(request,response);		
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//read student info from the form data
			int id= Integer.parseInt(request.getParameter("studentId"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			
		//create a new student object based on that form data
			Student theStudent = new Student(id, firstName, lastName ,email);
			
		//perform update on the database
			StudentDBUtil.updateStudent(theStudent);	
			
		//send them back to the "list Student"  Page
			listStudent(request, response);
		
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//read student id from form data
			String theStudentId=request.getParameter("studentId");
		
		//get student from database (DB util)
				//get me this student id full info
			Student theStudent = StudentDBUtil.getStudent(theStudentId); //get student return all student info
		//place student in a request object
			request.setAttribute("THE_STUDENT", theStudent); //attribute name , value
		//send to jsp page : update-student-form.jsp
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("/update-student-form.jsp");
			dispatcher.forward(request, response);
		
	}
	



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create a new student object
		Student theStudent = new Student(firstName, lastName, email); // student object created
		
		// add the student to the database
		StudentDBUtil.addStudent(theStudent);
		
		
		// send back to main page (the student list)
		
		listStudent(request,response);
	}



	private void listStudent(HttpServletRequest request, HttpServletResponse response) 
		throws Exception{
		//get data , set attribute ,and then use request dispatcher and send it over to the jsp.
		
		// get student from db util
		List<Student> students = StudentDBUtil.getStudent();
		
		// add students to the request object
		request.setAttribute("STUDENT_LIST", students);
		
		// send to jsp page (view)
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		
	}
	
	
	
}
