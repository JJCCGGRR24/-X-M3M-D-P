<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<form:form action="${requestURI }" modelAttribute="story">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="explorer"/>
	
	
	<form:label path="title">
		<spring:message code="story.title"/>:
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br />
	
	<form:label path="text">
		<spring:message code="story.text"  />:
	</form:label>
	<form:textarea path="text" />
	<form:errors cssClass="error" path="text" />
	<br />
	
	<form:label path="trip">
		<spring:message code="story.trip" />:
	</form:label>
	<form:select path="trip">
		<form:options items="${tripsConAppAceptadas}" itemLabel="ticker"
			itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="trip.ticker" />
	<br />
	<form:label path="attachments">
		<spring:message code="story.attachments"  /> (<spring:message code="general.split" />):
	</form:label>
	<form:textarea path="attachments" />
	<form:errors cssClass="error" path="attachments" />
	<br />
	

<input type="submit" name="save" value="<spring:message code="sponsorship.save"/>" />

<input type="button" value="<spring:message code="sponsorship.cancel"/>" 
onclick="document.location.href='story/explorer/list.do'"/>

</form:form>


</body>
</html>