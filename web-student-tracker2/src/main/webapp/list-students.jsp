<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>



<head> STUDENT TRACKER APP

<link type="text/css" rel="stylesheet" href="css/style.css" >

</head>


<body>

<div id="wrapper" >
	<div id="header">
		<h2>University</h2>
	</div>
	
	<div id="container" >
		<div id="content">
		
		<!--  put a new button : ADD STUDENT -->
		<input type="button" value="Add Student"
			onclick="window.location.href='add-student-form.jsp'; return false;"
			class="add-student-button"
		/>
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
					
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
				<!--  set up a link for each student -->
				<!--  using jstl -> URL (it defines a link to a url and can also pass parameters to it-->				
				<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="studentId" value="${tempStudent.studentID}" />				
				</c:url>
				
				<!--  setup a link to delete a student -->
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${tempStudent.studentID}" />				
				</c:url>
				
				
					<tr>
						<td> ${tempStudent.firstName} </td>
						<td> ${tempStudent.lastName} </td>
						<td> ${tempStudent.email}</td>
						<td> 
							<a href="${tempLink}">Update</a> 
							 |
							<a href="${deleteLink}"
							onclick="if(!(confirm('Are you sure you want to delete this Student?'))) return false">
							Delete</a>
							<!--  used javascript onclick to help -->
						</td>
					</tr>
				</c:forEach>
			</table>
		
		</div>
	</div>
</div>



</body>

</html>