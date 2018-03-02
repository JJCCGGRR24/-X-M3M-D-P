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


<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="uN"/>
</security:authorize>

<display:table sort="list" name="socialIdentities" id="row" requestURI="${requestURI}"
		pagesize="3" class="displaytag">

	<jstl:if test="${actor.userAccount.username eq uN}">
		<spring:message code="socialId.edit" var="editHeader" />
		<display:column>
		<spring:url
				value="/socialIdentity/actor/edit.do"
				var="editURL">
				<spring:param name="socialIdentityId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${editURL}"> <spring:message code="socialId.edit" /></a>
		</display:column> 
	</jstl:if>
		
		
	<spring:message code="socialId.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}"
		sortable="true" />

	<spring:message code="socialId.network" var="networkHeader" />
	<display:column property="network" title="${networkHeader}"
		sortable="true" />


	<spring:message code="socialId.photo" var="photoHeader" />
	<display:column property="photo" title="${photoHeader}"
		sortable="true" />
		
	<spring:message code="socialId.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}"
		sortable="true" />	
</display:table>
	
	
	<jstl:if test="${actor.userAccount.username eq uN}">
	<input type="button" name="create" value="<spring:message code="socialId.create"/>"
	onclick ="javascript: relativeRedir('socialIdentity/actor/create.do')"/> 
	</jstl:if>
	
	

</body>
</html>