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
	<form:hidden path="remark"/>
	<form:hidden path="auditor"/>
	<form:hidden path="trip"/>
	
	
	<form:label path="comment">
		<spring:message code="note.comment"/>:
	</form:label>
	<form:textarea path="comment" type="text"/>
	<form:errors cssClass="error" path="comment"/>
	<br />
	
	
<input type="submit" name="save"  value="<spring:message code="note.reply"/>" />


<input type="button" name="cancell" value="<spring:message code="template.cancel"/>" 
onclick="javascript: relativeRedir('note/manager/list.do');"/>

</form:form>


</body>
</html>