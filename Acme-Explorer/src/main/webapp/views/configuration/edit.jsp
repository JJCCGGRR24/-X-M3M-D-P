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

<form:form action="configuration/admin/edit.do" modelAttribute="configuration">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="welcome" />

	<form:label path="timeCache">
		<spring:message code="configuration.timeCache" />:
	</form:label>
	<form:input path="timeCache" />
	<form:errors cssClass="error" path="timeCache" />
	<spring:message code="general.hours" />
	<br/><br/>
	
	<form:label path="defaultCountry">
		<spring:message code="configuration.defaultCountry" />:
	</form:label>
	<form:input path="defaultCountry" />
	<form:errors cssClass="error" path="defaultCountry" />
	<br/><br/>
	
	<form:label path="spamWords">
		<spring:message code="configuration.spamWords" /> (<spring:message code="general.split" />):
	</form:label>
	<br>
	<form:textarea path="spamWords" />
	<form:errors cssClass="error" path="spamWords" />
	
	
	<br/><br/>
	
	<form:label path="taxVAT">
		<spring:message code="configuration.taxVAT" />:
	</form:label>
	<form:input path="taxVAT" />
	<form:errors cssClass="error" path="taxVAT" />
	<br/><br/>
	
	<form:label path="numResults">
		<spring:message code="configuration.numResults" />:
	</form:label>
	<form:input path="numResults" />
	<form:errors cssClass="error" path="numResults" />
	<br/><br/>
	
	<form:label path="welcomeEn">
		<spring:message code="configuration.welcomeEn" />:
	</form:label>
	<form:textarea path="welcomeEn" />
	<form:errors cssClass="error" path="welcomeEn" />
	<br/><br/>
	
	<form:label path="welcomeEs">
		<spring:message code="configuration.welcomeEs" />:
	</form:label>
	<form:textarea path="welcomeEs" />
	<form:errors cssClass="error" path="welcomeEs" />
	<br/><br/>
	
	<input type="button" class="btn btn-warning" name="cancel" 
	value='<spring:message code="template.cancel"/>' onclick="document.location.href='';">
	
	<input type="submit" class="btn btn-success" name="save" 
	value='<spring:message code="template.save"/>'>
	
</form:form>


