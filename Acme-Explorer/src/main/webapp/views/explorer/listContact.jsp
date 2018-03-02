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

	<display:table sort="list" name="contacts" id="row" requestURI="${requestURI}"
		pagesize="10" class="displaytag">


		<spring:message code="contact.edit" var="editHeader" />
		
		<jstl:if test="${actor.userAccount.username eq uN }">
		<display:column>
		<spring:url
				value="/contact/explorer/edit.do"
				var="editURL">
				<spring:param name="contactId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${editURL}"> <spring:message code="contact.edit" /></a>
		</display:column> 
		</jstl:if>
		

		<spring:message code="contact.name" var="nameHeader" />
		<display:column property="name" title="${nameHeader}"
			sortable="true" />


		<<spring:message code="contact.email" var="emailHeader" />
		<display:column property="email" title="${emailHeader}"
			sortable="true" />


		<spring:message code="contact.phone" var="phoneHeader" />
		<display:column property="phone" title="${phoneHeader}"
			sortable="true" />		
		
	</display:table> 

	 <jstl:if test="${actor.userAccount.username eq uN }">
		<input type="button" name="create"
			value="<spring:message code="contact.create"/>"
			onclick="javascript: relativeRedir('contact/explorer/create.do')" />
	</jstl:if>

</body>
</html>