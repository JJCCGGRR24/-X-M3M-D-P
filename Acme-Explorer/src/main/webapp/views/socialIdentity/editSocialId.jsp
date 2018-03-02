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




<form:form action="${requestURI}" modelAttribute="socialIdentity">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="actor" />



	<form:label path="link">
		<spring:message code="socialId.link" />:
	</form:label>
	<form:input path="link" />
	<form:errors cssClass="error" path="link" />
	<br />
	
	<form:label path="network">
		<spring:message code="socialId.network" />:
	</form:label>
	<form:input path="network" />
	<form:errors cssClass="error" path="network" />
	<br />
	
	<form:label path="photo">
		<spring:message code="socialId.photo" />:
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<form:label path="nick">
		<spring:message code="socialId.nick" />:
	</form:label>
	<form:input path="nick" />
	<form:errors cssClass="error" path="nick" />
	<br />
	

	<input type="submit" name="save" 
		value="<spring:message code="template.save" />" />&nbsp; 
		
	<jstl:if test="${socialIdentity.id != 0}">	
	<input type="submit" name="delete" 
	onclick="javascript: relativeRedir('socialIdentity/actor/edit.do');"
		value="<spring:message code="template.delete" />" />&nbsp; 
	</jstl:if>
		
	
	<input type="button" name="cancel"
		value="<spring:message code="template.cancel" />"
		onclick="javascript: relativeRedir('socialIdentity/actor/list.do');" />
	<br />
</form:form>


