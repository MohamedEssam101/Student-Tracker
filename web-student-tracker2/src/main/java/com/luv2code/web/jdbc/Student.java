package com.luv2code.web.jdbc;

public class Student {

	private int studentID;
	private String firstName;
	private String lastName;
	private String email;
	
	public Student(int studentID, String firstName, String lastName, String email) {
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public Student(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	// to get student information without doing it manually 
	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + "]";
	}
	
}
