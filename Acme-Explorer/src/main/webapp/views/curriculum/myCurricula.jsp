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

	<jstl:if test="${ owner && ranger.curricula eq null}">
	<spring:message code="general.create" var="create" />
	<div align="center">
		<input type="button" value="${create}"
			onclick="javascript: window.location.href = './curricula/ranger/create.do';" /></div>
			</jstl:if>

<display:table name="curricula" id="row"
	requestURI="${requestURI}" pagesize="4" class="displaytag">
	
	
	<spring:message code="curriculum.ticker" var="ticker"></spring:message>
	<display:column property="ticker" title="${ticker}"></display:column>
	
	
	
	
<!-- DETALLES -->
	<spring:message code="general.details" var="details" />
	<display:column title="${details}">
		<input type="button" value="${details}"
			onclick="javascript: window.location.href = './curricula/details.do?curriculumId=${row.id}';" />
	</display:column>
	
	
	<jstl:if test="${owner}">
	<!-- BORRAR -->
	<spring:message code="general.delete" var="delete" />
	<display:column title="${delete}">
		<input type="button" value="${delete}"
			onclick="javascript: confirm('<spring:message code="template.confirm" />');
			window.location.href = './curricula/ranger/delete.do?curriculumId=${row.id}';" />
	</display:column>
	</jstl:if>



</display:table>