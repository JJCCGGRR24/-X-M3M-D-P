<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<form:form action="${requestURI}" modelAttribute="trip">
	

	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	<form:hidden path="price"/>
	<form:hidden path="stages"/>
	<form:hidden path="tags"/>
	<form:hidden path="legalText"/>
	<form:hidden path="ranger"/>
	<form:hidden path="survivalClasses"/>
	<form:hidden path="manager"/>
	<form:hidden path="finders"/>
	<form:hidden path="applications"/>
	<form:hidden path="stories"/>
	<form:hidden path="notes"/>
	<form:hidden path="audits"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="category"/>
	<form:hidden path="title"/>
	<form:hidden path="description"/>
	<form:hidden path="requirements"/>
	<form:hidden path="starts"/>
	<form:hidden path="ends"/>
	
	
	<form:label path="cancelledReason">
		<spring:message code="trip.cancelledReason"/>
	</form:label>
	<form:input path="cancelledReason" type="text"/>
	<form:errors cssClass="error" path="cancelledReason"/>
	<br />
	
	
	


	<input type="submit" name="save" value="<spring:message code="trip.cancel"/>"
	onclick="javascript:relativeRedir('trip/manager/edit.do');"> &nbsp;


	<input type="button" name="cancel" value="<spring:message code="template.back"/>" 
	onclick="javascript:relativeRedir('trip/manager/list.do');"/>

	
</form:form>




</body>
</html>