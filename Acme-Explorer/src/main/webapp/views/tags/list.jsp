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




<spring:message code="tag.tituloTag1" var="titulo1"/>
<h2><jstl:out value="${titulo1}"/></h2>
<display:table name="tags" id="row"
		requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	
	
	<spring:message code="tag.name" var="tagHeader"></spring:message>
	<display:column property="name" title="${tagHeader}"></display:column>


<security:authorize access="hasRole('MANAGER')">
	<security:authentication property="principal.username" var="username"/>
	<jstl:if test="${trip.manager.userAccount.username eq username}">	
	
	<spring:message code="template.takeOff" var="takeOff" />
	<display:column title="${takeOff}">
		<spring:url value="/tag/manager/takeOff.do" var="editURL">
			<spring:param name="tagId" value="${row.id}"></spring:param>
			<spring:param name="tripId" value="${tripId}"></spring:param>
		</spring:url>
		<a href="${editURL}"> <spring:message code="template.takeOff" /></a>
	</display:column>
	

	</jstl:if>
</security:authorize>

	
</display:table>

<spring:message code="tag.tituloTag2" var="titulo2"/>
<h2><jstl:out value="${titulo2}"/></h2>
<security:authorize access="hasRole('MANAGER')">
	<security:authentication property="principal.username" var="username"/>
	<jstl:if test="${trip.manager.userAccount.username eq username}">	
	<display:table name="allTag" id="rowTag" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="tag.name" var="tagHeader"></spring:message>
	<display:column property="name" title="${tagHeader}"></display:column>
	
	<spring:message code="template.add" var="add" />
	<display:column title="${add}">
		<spring:url value="/tag/manager/add.do" var="editURL">
			<spring:param name="tagId" value="${rowTag.id}"></spring:param>
			<spring:param name="tripId" value="${tripId}"></spring:param>
		</spring:url>
		<a href="${editURL}"> <spring:message code="template.add" /></a>
	</display:column>
	
	</display:table>
	</jstl:if>
</security:authorize>