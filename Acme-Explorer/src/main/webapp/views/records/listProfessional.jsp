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



<!-- CREAR -->

	<spring:message code="general.create" var="create" />
	<div align="center">
		<input type="button" value="${create}"
			onclick="javascript: window.location.href = './professionalRecord/ranger/create.do?curriculumId=${curriculumId}';" /></div>


<display:table name="records" id="row"
	requestURI="${requestURI}" pagesize="4" class="displaytag">
	
<spring:message code="professionalRecord.companyName" var="companyName"></spring:message>
<spring:message code="professionalRecord.role" var="role"></spring:message>
<spring:message code="educationRecord.starts" var="starts"></spring:message>
<spring:message code="educationRecord.ends" var="ends"></spring:message>
<spring:message code="general.comments" var="comments"></spring:message>
<spring:message code="general.attachment" var="attachment"></spring:message>

	
	<display:column property="companyName" title="${companyName}"></display:column>
	<display:column property="role" title="${role}"></display:column>
	<display:column property="startDate" title="${starts}"></display:column>
	<display:column property="endDate" title="${ends}"></display:column>
	<display:column property="comments" title="${comments}"></display:column>
	<display:column property="attachment" title="${attachment}"></display:column>
	

<!-- EDITAR -->
	<spring:message code="general.edit" var="edit" />
	<display:column title="${edit}">
		<input type="button" value="${edit}"
			onclick="javascript: window.location.href = './professionalRecord/ranger/edit.do?professionalRecordId=${row.id}';" />
	</display:column>
	

</display:table>