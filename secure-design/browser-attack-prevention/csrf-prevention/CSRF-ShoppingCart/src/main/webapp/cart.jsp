<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Simple Shopping Cart</title>
</head>
<body>
	<h2>Shopping Cart:</h2>
	<form method="post" action="controller">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<table border="0" cellspacing="1" cellpadding="5">
			<colgroup>
				<col width="150">
				<col width="150">
				<col width="100">
			</colgroup>
			<tr>
				<th>Product</th>
				<th>Quantity</th>
				<th></th>
			</tr>
			<tr>
				<td><input type="text" name="name" /></td>
				<td><input type="text" name="quantity" /></td>
				<td><input type="submit" name="action" value="Add" /></td>
			</tr>
		</table>
	</form>
	<p />
	<hr />
	
<%
	List<org.se.lab.Product> cart = (List<org.se.lab.Product>)session.getAttribute("cart");
	if(cart != null)
	{
		StringBuilder html = new StringBuilder();
		
		html.append("    <ul>\n");
		for(org.se.lab.Product p : cart)
		{
			html.append("        <li>").append(p.getQuantity()).append(" x ").append(p.getName()).append("</li>\n");
		}
		html.append("    </ul>\n");
		out.print(html);
	}
%>
	<hr />
</body>
</html>