<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Vulnerable Application</title>
	</head>
	
	<body>
		<h2>Login: </h2>		
		<form method="POST" action="controller" >
			<table border="1" >
				<colgroup>
					<col width="200"> <col width="200">
				</colgroup>
				<tr>
					<th align="left"> 
						Username 
					</th>
					<th align="left">  
						<input type="text" name="username" maxlength="40" size="20"> <br>
					</th>
				</tr>
				
				<tr>
					<th align="left"> 
						Password 
					</th>
					<th align="left">  
						<input type="password" name="password" maxlength="40" size="20">
					</th>
				</tr>
			</table>			
			
			<table >
				<colgroup>
					<col width="150"> <col width="50"> <col width="150">
				</colgroup>
				<tr>
					<th>
						<input type="reset"  name="action" value="Reset">  
					</th>
					<th/>  
					<th>  
						<input type="submit" name="action" value="Login">
					</th>
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

		<p style="color:blue"><i><%=message%></i></p>
		
		<br/>
		<a href="login.jsp">reset</a>
	</body>
</html>