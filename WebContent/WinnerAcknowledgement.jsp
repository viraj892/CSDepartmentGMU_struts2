<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="beans.StudentBean"%>
<%@page import="beans.ResultBean"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Winner!</title>
</head>
<body>
	<%@ taglib prefix="s" uri="/struts-tags" %>
	<%
		ResultBean data = (ResultBean) session.getAttribute("data");
		List<String> id_list = new ArrayList<String>();
		ResultSetMetaData rSetMeta = (ResultSetMetaData) request.getAttribute("rSetMeta");
	%>

	<h2>Thank you for completing the survey, ${name}</h2>
	<h3>You are the raffle winner of two movie tickets!!</h3>
	<h4>
		Mean =
		<%=data.getMean()%></h4>
	<h4>
		Standard Deviation =
		<%=data.getSd()%></h4>

	<h4>Students List:</h4>
	<s:form action="populateStudentDetails">
		<ul>
			<%
				session.getAttribute("ids");
				for (String temp_id : id_list) {
					out.print("<li><button type=\"submit\" name=\"student_id_button\" value=\"" + temp_id + "\">" + temp_id
							+ "</button></li>");
				}
			%>

		</ul>
	</s:form>
</body>
</html>