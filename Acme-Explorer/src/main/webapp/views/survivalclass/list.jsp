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




	<security:authorize access="hasRole('MANAGER')">
	
	<spring:message code="template.create" var="create" />
		<input type="button" value="${create}"
			onclick="javascript: window.location.href = './survivalClass/manager/create.do';" />
	
	</security:authorize>



<display:table name="survivalClasses" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	

	
	
	<spring:message code="application.trip" var="trip"></spring:message>
	<display:column property="trip.ticker" title="${trip}"></display:column>
	
	<spring:message code="survivalClass.title" var="title"></spring:message>
	<display:column property="title" title="${title}"></display:column>
	
	<spring:message code="survivalClass.description" var="description"></spring:message>
	<display:column property="description" title="${description}"></display:column>
	
	<spring:message code="event.format.date" var="pattern"/>
	<spring:message code="survivalClass.date" var="date"></spring:message>
	<display:column property="date" title="${date}" format="${pattern}"></display:column>
	
	<spring:message code="survivalClass.location" var="location"></spring:message>
	<display:column property="location.name" title="${location}"></display:column>
	
	
	<!-- PARA EDITAR -->
	
	<security:authorize access="hasRole('MANAGER')">
	
	<spring:message code="template.edit" var="edit" />
	<display:column title="${edit}">
		<input type="button" value="${edit}"
			onclick="javascript: window.location.href = './survivalClass/manager/edit.do?survivalClassId=${row.id}';" />
	</display:column>
	
	</security:authorize>
	
	
	
	
	
	
	
	<security:authorize access="hasRole('EXPLORER')"> 
	
	<spring:message code="template.enrol" var="enrol" />
	<spring:message code="template.unenrol" var="unenrol" />
	<spring:message code="template.enrolunenrol" var="enrolunenrol" />
	<display:column title="${enrolunenrol}">
	
		<!-- PARA ENROL -->
		<spring:eval var="containsValue" expression="${row.explorers.contains(explorer) }" />
		<jstl:if test="${ not containsValue }">
		<input type="button" value="${enrol}"
			onclick="javascript: window.location.href = './survivalClass/explorer/enrol.do?survivalClassId=${row.id}';" />
		</jstl:if>
		
		
		<!-- PARA UNENROL -->
		<jstl:if test="${containsValue }">
		<input type="button" value="${unenrol}"
			onclick="javascript: window.location.href = './survivalClass/explorer/unenrol.do?survivalClassId=${row.id}';" />
		</jstl:if>
		
			
	</display:column>
	
	</security:authorize>
	
	
	
</display:table>
