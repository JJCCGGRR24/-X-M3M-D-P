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

<display:table name="categories" id="row"
	requestURI="category/all/list.do" pagesize="5" class="displaytag">
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<jstl:if test="${row.name != 'CATEGORY'}">
				<input type="button" name="edit"
				value="<spring:message code="template.edit" />"
				onclick="document.location.href='category/admin/edit.do?categoryId='+ ${row.id};" />
			</jstl:if>
		</display:column>
	</security:authorize>
	
	
	<spring:message code="category.name" var="name"></spring:message>
	<display:column property="name" title="${name}"></display:column>
	
	<spring:message code="category.children" var="children"></spring:message>
	<display:column  title="${children}">
		<jstl:forEach items="${row.categoriesChildren}" var="x">
			<a href="trip/all/list.do?categoryId=${x.id}">${x.name}</a>
		</jstl:forEach><br/>
	</display:column>
	
	<spring:message code="category.father" var="father"></spring:message>
	<display:column  title="${father}">
			<a href="trip/all/list.do?categoryId=${row.id}">${row.categoryFather.name}</a><br/>
	</display:column>
	
</display:table>

 <security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.create" var="create" />
	<input type="button" value="${create}" onclick="javascript: window.location.href = './category/admin/create.do';" />
	</security:authorize>