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




<display:table name="audits" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	
	<security:authorize access="hasRole('AUDITOR')">
		<display:column  >
			<jstl:if test="${row.finalMode eq false }">
	
				<input type="button" name="edit"
				value="<spring:message code="template.edit" />"
				onclick="document.location.href='audit/auditor/edit.do?auditId='+ ${row.id};" />
	
			</jstl:if>
		</display:column>
	</security:authorize>
	
	
	
	
	<spring:message code="audit.title" var="title"></spring:message>
	<display:column property="title" title="${title}"></display:column>
	
	<spring:message code="audit.description" var="description"></spring:message>
	<display:column property="description" title="${description}"></display:column>
	
	<spring:message code="audit.attachments" var="attachments"></spring:message>
	<display:column  title="${attachments}">
		<jstl:forEach items="${row.attachments}" var="x">
			<a href='<jstl:out value="${x}"/>'><jstl:out value="${x}"/></a><br/>
		</jstl:forEach>
	</display:column>
	
	<security:authorize access="hasRole('AUDITOR')">
	<spring:message code="legalText.finalMode" var="modeHeader" />
	<display:column  title="${modeHeader}"
		sortable="true">
		<jstl:if test="${row.finalMode}">
		<spring:message code="template.yes"/>
		</jstl:if>
		<jstl:if test="${row.finalMode == false}">
		<spring:message code="template.no"/>
		</jstl:if>
	</display:column>	
	</security:authorize>
	
<%-- 	<spring:message code="finalMode.true" var="true"></spring:message> --%>
<%-- 	<spring:message code="finalMode.false" var="false"></spring:message> --%>
<%-- 	<spring:message code="audit.state" var="state"></spring:message> --%>
<%-- 	<display:column property="finalMode" title="${state}"> --%>
<%-- 			<jstl:if test="${row.finalMode eq true }"> --%>
<%-- 			</jstl:if> --%>
<%-- 	</display:column> --%>	
</display:table>

