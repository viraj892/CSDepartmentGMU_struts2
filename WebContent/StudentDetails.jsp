<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="beans.StudentBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="style_common.css">
<link rel="stylesheet" type="text/css" href="style_survey_form.css">
<link rel="stylesheet" type="text/css" href="jquery/jquery-ui.css">
<link rel="stylesheet" type="text/css"
	href="jquery/jquery-ui.structure.css">
<link rel="stylesheet" type="text/css" href="jquery/jquery-ui.theme.css">
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="jquery/jquery-ui.js"></script>
<!-- <script type="text/javascript" src="survey_form.js"></script> -->
</head>
<body>
	<%
		StudentBean student = (StudentBean) session.getAttribute("studBean");
	%>
	<h1>Student Details from database</h1>

	<form name="survey_form1" action="StudentDAO" method="GET"
		onsubmit="return validate()">
		<fieldset>
			<legend>Personal Information</legend>
			<table>
				<tr>
					<td><label class="field">Name:</label></td>
					<td><label class="field">Address:</label></td>
					<td><label class="field">Zip:</label></td>
				</tr>
				<tr>
					<td><span><%= student.getName() %></span></td>
					<td><span><%= student.getAddress() %></span></td>
					<td><span><%= student.getZip() %></span></td>
				</tr>
				<tr>
					<td><label class="field">City:</label></td>
					<td><label class="field">State:</label></td>
					<td><label class="field">Phone Number:</label></td>
				</tr>
				<tr>
					<td><span><%= student.getCity() %></span></td>
					<td><span><%= student.getState() %></span></td>
					<td><span><%= student.getPhone() %></span></td>
				</tr>

				<tr>
					<td><label class="field">E-mail:</label></td>
					<td><label class="field">Personal URL:</label></td>
					<td><label class="field">Date of Survey:</label></td>
				</tr>
				<tr>
					<td><span><%= student.getEmail() %></span></td>
					<td><span><%= student.getUrl() %></span></td>
					<td><span><%= student.getDate() %></span></td>
				</tr>
				<tr>
					<td><label class="field">High School Graduation Date:</label></td>
					<td><label class="field">Data:</label></td>
					<td><label class="field">ID:</label></td>
				</tr>
				<tr>
					<td><span><%= student.getGradMonth() %></span> <span><%= student.getGradYear() %></span></td>
					<td><span>Data undisclosed</span>
					<td><span><%= student.getId() %></span></td>

				</tr>

				<!-- <tr id="calculated_avg_row">
						<td><label class="field">Average:</label></td>
						<td><span id="calculated_avg_value"></span></td>
					</tr>
					<tr id="calculated_max_row">
						<td><label class="field">Maximum:</label></td>
						<td><span id="calculated_max_value"></span></td>
					</tr> -->
			</table>
		</fieldset>
		<br>
		<fieldset>
			<legend>Survey Information</legend>
			<table>
				<tr>
					<td><label id="survey_info_label1" class="field">What
							did you like most about the college?</label></td>
					<td><label id="survey_info_label2" class="field">How
							did you find out about GMU?</label></td>
					<td><label id="survey_info_label3" class="field">How
							likely are you to recommend GMU to others?</label></td>
				</tr>
				<tr>
					<td><span><%= student.getPreference() %></span></td>
					<td><span><%= student.getFound() %></span></td>
					<td><span><%= student.getRecommend() %></span></td>
				</tr>

				<tr>
					<td><label id="survey_info_label" class="field">Additional
							Comments:</label></td>
				</tr>
				<tr>
					<td><span><%= student.getComment() %></span></td>
				</tr>
			</table>
		</fieldset>
		<br>
	</form>
</body>
</html>