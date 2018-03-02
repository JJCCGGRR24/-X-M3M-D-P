<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

	<display:table sort="list" name="stages" id="row" requestURI="${requestURI}"
		pagesize="8" class="displaytag">


		<%-- <spring:message code="stage.edit" var="editHeader" />
		<display:column>
		<spring:url
				value="/stage/manager/edit.do"
				var="editURL">
				<spring:param name="stage" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${editURL}"> <spring:message code="stage.edit" /></a>
		</display:column>  --%>
		
		<security:authorize access="hasRole('MANAGER')" >	
		<security:authentication property="principal.username" var="username"/>
		<spring:message code="stage.edit" var="editHeader" />
			<display:column title="${editHeader}">
			<jstl:if test="${row.trip.manager.userAccount.username eq username}">
			<spring:url
					value="/stage/manager/edit.do"
					var="editURL">
					<spring:param name="stageId" value="${row.id}"></spring:param>
				</spring:url>
				<a href="${editURL}"> <spring:message code="stage.edit" /></a>
			</jstl:if>
		</display:column> 
	</security:authorize>
		
		
		<spring:message code="stage.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}"
			sortable="true" />

		<spring:message code="stage.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}"
			sortable="true" >
		</display:column>


		<spring:message code="stage.price" var="priceHeader" />
		<display:column property="price" title="${priceHeader}"
			sortable="true" >
		</display:column>
			
		
			
	
</display:table>

		<security:authentication property="principal.username" var="username"/>
			<jstl:if test="${trip.manager.userAccount.username eq username}">
			<spring:url value="/stage/manager/create.do" var="editURL">
				<spring:param name="tripId" value="${tripId}"></spring:param>
			</spring:url>
			<a href="${editURL}"> <spring:message code="stage.create" /></a>
			</jstl:if>
	
<%-- <input type="button" name="create"
			value="<spring:message code="stage.create"/>"
			onclick="javascript: relativeRedir('stage/manager/create.do')" /> --%>
	

</body>
</html>