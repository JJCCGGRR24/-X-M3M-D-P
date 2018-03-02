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




<display:table name="applications" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag" defaultsort="2" sort="list">
	
	<security:authorize access="hasRole('EXPLORER')">
		<display:column  >
			<jstl:if test="${row.status eq 'ACCEPTED' and dateNow lt row.trip.starts}">
	
				<input type="button" name="cancel"
				value="<spring:message code="template.cancel" />"
				onclick="document.location.href='application/explorer/cancell.do?applicationId='+ ${row.id};" />
	
			</jstl:if>
		</display:column>
	</security:authorize>
			
	<jstl:choose>
		<jstl:when test="${(row.status eq 'PENDING') and (row.trip.starts gt dateNow) and (row.trip.starts lt dateMonthAfter)}">
			<spring:message code="application.status" var="status"></spring:message>
			<display:column title="${status}" sortable ="true"> 
				<div style="background: red;"> <jstl:out value="${row.status }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.trip" var="trip"></spring:message>
			<display:column  title="${trip}">
				<div style="background: red;"> <jstl:out value="${row.trip.ticker }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.comments" var="comments"></spring:message>
			<display:column title="${comments}">
				<div style="background: red;"> <jstl:out value="${row.comments }"></jstl:out> </div>
			</display:column>
		</jstl:when>
		
		
	
		<jstl:when test="${row.status eq 'PENDING' }">
			<spring:message code="application.status" var="status"></spring:message>
			<display:column title="${status}" sortable ="true"> 
				<div style="background: white;"> <jstl:out value="${row.status }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.trip" var="trip"></spring:message>
			<display:column  title="${trip}">
				<div style="background: white;"> <jstl:out value="${row.trip.ticker }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.comments" var="comments"></spring:message>
			<display:column title="${comments}">
				<div style="background: white;"> <jstl:out value="${row.comments }"></jstl:out> </div>
			</display:column>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'REJECTED' }">
			<spring:message code="application.status" var="status"></spring:message>
			<display:column title="${status}" sortable ="true"> 
				<div style="background: gray;"> <jstl:out value="${row.status }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.trip" var="trip"></spring:message>
			<display:column  title="${trip}">
				<div style="background: gray;"> <jstl:out value="${row.trip.ticker }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.comments" var="comments"></spring:message>
			<display:column title="${comments}">
				<div style="background: gray;"> <jstl:out value="${row.comments }"></jstl:out> </div>
			</display:column>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'DUE' }">
			<spring:message code="application.status" var="status"></spring:message>
			<display:column title="${status}" sortable ="true"> 
				<div style="background: yellow;"> <jstl:out value="${row.status }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.trip" var="trip"></spring:message>
			<display:column  title="${trip}">
				<div style="background: yellow;"> <jstl:out value="${row.trip.ticker }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.comments" var="comments"></spring:message>
			<display:column title="${comments}">
				<div style="background: yellow;"> <jstl:out value="${row.comments }"></jstl:out> </div>
			</display:column>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'ACCEPTED' }">
			<spring:message code="application.status" var="status"></spring:message>
			<display:column title="${status}" sortable ="true"> 
				<div style="background: green;"> <jstl:out value="${row.status }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.trip" var="trip"></spring:message>
			<display:column  title="${trip}">
				<div style="background: green;"> <jstl:out value="${row.trip.ticker }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.comments" var="comments"></spring:message>
			<display:column title="${comments}">
				<div style="background: green;"> <jstl:out value="${row.comments }"></jstl:out> </div>
			</display:column>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'CANCELLED' }">
			<spring:message code="application.status" var="status"></spring:message>
			<display:column title="${status}" sortable ="true"> 
				<div style="background: #00FFFF;"> <jstl:out value="${row.status }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.trip" var="trip"></spring:message>
			<display:column  title="${trip}">
				<div style="background: #00FFFF;"> <jstl:out value="${row.trip.ticker }"></jstl:out> </div>
			</display:column>
	
			<spring:message code="application.comments" var="comments"></spring:message>
			<display:column title="${comments}">
				<div style="background: #00FFFF;"> <jstl:out value="${row.comments }"></jstl:out> </div>
			</display:column>
		</jstl:when>
		
	
	</jstl:choose>
	
	
	
	<spring:message code="application.change.status" var="change.status"></spring:message>
	<security:authorize access="hasRole('MANAGER')">
		<display:column  title="${change.status }">
			<jstl:if test="${row.status eq 'PENDING'}">
			
				<input type="button" name="rejected"
				value="<spring:message code="application.rejected" />"
				onclick="document.location.href='application/manager/rejected.do?applicationId='+ ${row.id};" />
				
				
				<input type="button" name="due"
				value="<spring:message code="application.due" />"
				onclick="document.location.href='application/manager/due.do?applicationId='+ ${row.id};" />
			
			
			</jstl:if>
		</display:column>
	</security:authorize>
	
	
	<spring:message code="application.addCreditCard" var="addCreditCard"></spring:message>
	<security:authorize access="hasRole('EXPLORER')">
		<display:column >
			<jstl:if test="${row.status eq 'DUE'}">
			
				<input type="button" name="addCreditCard"
				value="${addCreditCard}"
				onclick="document.location.href='application/explorer/addCreditCard.do?applicationId='+ ${row.id};" />
			
			</jstl:if>
		</display:column>
	</security:authorize>
	
	
	
	
	
	
</display:table>
