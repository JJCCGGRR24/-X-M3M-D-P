<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<form:form action="${requestURI }" modelAttribute="application">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
		<form:hidden path="status" />
		<form:hidden path="trip" />
		<form:hidden path="explorer" />
		
		



	<form:label path="comments">
		<spring:message code="application.comments" />:
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />
	
	
	

	<input type="submit" name="save" 
		value="<spring:message code="template.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="template.cancel" />"
		onclick="document.location.href='application/explorer/list.do';" />
	<br />
</form:form>


