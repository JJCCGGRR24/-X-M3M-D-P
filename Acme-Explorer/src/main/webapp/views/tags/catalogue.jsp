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





<display:table name="tags" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	
	
		<spring:message code="tag.edit" var="tagHeader" />
			<display:column title="${tagHeader}">
			<jstl:if test="${tagsFree.contains(row)}">
			<spring:url
					value="/tag/admin/edit.do"
					var="editURL">
					<spring:param name="tagId" value="${row.id}"></spring:param>
				</spring:url>
				<a href="${editURL}"> <spring:message code="tag.edit" /></a>
				</jstl:if>
		</display:column> 
	
	
	
	
	<spring:message code="tag.name" var="tagHeader"></spring:message>
	<display:column property="name" title="${tagHeader}"></display:column>
	
	<spring:message code="general.delete" var="delete" />
			<display:column title="${delete}">
			<spring:url
					value="/tag/admin/delete.do"
					var="editURL">
					<spring:param name="tagId" value="${row.id}"></spring:param>
				</spring:url>
				<a href="${editURL}"> <spring:message code="general.delete" /></a>
		</display:column> 

	

	
</display:table>

<input type="button" name="create" value="<spring:message code="template.create"/>" 
onclick="javascript:relativeRedir('tag/admin/create.do');"/>
