<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload Attack</title>
</head>
<body>
	<h1>This file has been uploaded from a user...</h1>

	Web-Root: <%=application.getRealPath("/") %><br>
	
<%
   // Scriptlet
    Date date = new java.util.Date();
	
	out.println( date );
    out.println( "<BR>Your machine's address is " );
    out.println( request.getRemoteHost());
%>
</body>
</html>