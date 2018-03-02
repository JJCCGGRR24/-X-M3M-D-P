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




<form:form action="${requestURI}" modelAttribute="tag">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="trips" />



	<form:label path="name">
		<spring:message code="tag.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	
	

	<input type="submit" name="save" 
		value="<spring:message code="template.save" />" 
		onclick="javascript: relativeRedir('tag/manager/edit.do');"/>&nbsp; 
		
	<jstl:if test="${tag.id != 0}">	
	<input type="submit" name="delete" 
	onclick="javascript: relativeRedir('tag/manager/edit.do');"
		value="<spring:message code="template.delete" />" />&nbsp; 
	</jstl:if>
		
	
	<input type="button" name="cancel"
		value="<spring:message code="template.cancel" />"
		onclick="javascript: relativeRedir('tag/admin/catalogue.do');" />
	<br />
</form:form>


