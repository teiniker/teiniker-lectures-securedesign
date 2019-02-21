<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="user" class="org.se.lab.presentation.UserBean" scope="request"/>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>MVC-UserList</title>
    </head>
    <body>

		<h1>User List Example</h1>

    	<form method="post" action="controller">
        	<table border="1">
            	<tr>
            		<th width="50">Id</th>
                	<th width="150">FirstName</th>
                	<th width="150">LastName</th>
                	<th width="150">Username</th>
                	<th width="150">Password</th>   
                	<th width="100">Actions</th> 
            	</tr>
            	<tr>
            		<td/>
                	<td><input type = "text" name = "firstName" /></td>
                	<td><input type = "text" name = "lastName" /></td>
                	<td><input type = "text" name = "username" /></td>
                	<td><input type = "password" name = "password" /></td>
                	<td align="center">
                		<input type = "submit" name = "action" value = "Add" /></td> 
            	</tr>
        	</table>
    	</form>

		
<%
	String message;
	if(request.getAttribute("message") == null)
		message = "";
	else
		message = (String)request.getAttribute("message");
%>

		<p><a href="./login.jsp">Logout</a></p>

		<p><a href="./table.jsp">Refresh User List</a></p>

		<p style="color:blue"><i><%=message%></i></p>
    	<br/>

		${user.userTable}
		
        <h6>
        	${user.timeStamp}
        </h6>        
    </body>
</html>