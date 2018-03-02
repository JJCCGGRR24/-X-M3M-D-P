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





<display:table name="suspicius" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	

	
	
	<spring:message code="actor.userAccount.username" var="username"></spring:message>
	<display:column property="userAccount.username" title="${username}"></display:column>
	

	
	
	<spring:message code="template.ban" var="ban" />
	<spring:message code="template.unban" var="unban" />
	<spring:message code="template.banunban" var="banunban" />
	<display:column title="${baunban}">
	
		<!-- PARA banear -->
		<spring:eval var="banned" expression="${ row.userAccount.authorities.contains(auth) }" />
		<jstl:if test="${ not banned }">
		<input type="button" value="${ban}"
			onclick="javascript: window.location.href = './suspicius/admin/ban.do?actorId=${row.id}';" />
		</jstl:if>
		
		
		<!-- PARA desbanear -->
		<jstl:if test="${banned }">
		<input type="button" value="${unban}"
			onclick="javascript: window.location.href = './suspicius/admin/unban.do?actorId=${row.id}';" />
		</jstl:if>
		
			
	</display:column>
	
	
	
	
</display:table>
