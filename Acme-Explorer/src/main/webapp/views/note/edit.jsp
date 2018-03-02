<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<form:form action="${requestURI}" modelAttribute="note">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="momentCreation"/>
	<form:hidden path="comment"/>
	<form:hidden path="auditor"/>
	<form:hidden path="trip"/>
	
	
	<form:label path="remark">
		<spring:message code="note.remark"/>:
	</form:label>
	<form:textarea path="remark" type="text"/>
	<form:errors cssClass="error" path="remark"/>
	<br />
	
	<input type="submit" name="save" onclick="javascript:relativeRedir('note/auditor/edit.do');" value="<spring:message code="note.save"/>" />


	<input type="button" name="cancell" value="<spring:message code="template.cancel"/>" 
	onclick="javascript: relativeRedir('trip/auditor/list.do');"/>


</form:form>




</body>
</html>