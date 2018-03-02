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



<display:table sort="list" name="legalTexts" id="row" requestURI="${requestURI}"
		pagesize="4" class="displaytag">
		
	<spring:message code="legalText.edit" var="editHeader" />
			<display:column title="${editHeader}">	
			<jstl:if test="${row.finalMode eq false}">
				<spring:url
						value="/legalText/admin/edit.do"
						var="editURL">
						<spring:param name="legalTextId" value="${row.id}"></spring:param>
					</spring:url>
					<a href="${editURL}"> <spring:message code="legalText.edit" /></a>
			</jstl:if>	
			</display:column>
		
	<spring:message code="legalText.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />

	<spring:message code="legalText.body" var="bodyHeader" />
	<display:column property="body" title="${bodyHeader}"
		sortable="true" />


	<spring:message code="legalText.laws" var="lawHeader" />
	<display:column property="laws" title="${lawHeader}"
		sortable="true" />
		
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
</display:table>
	
	
	
	<input type="button" name="create" value="<spring:message code="legalText.create"/>"
	onclick ="javascript: relativeRedir('legalText/admin/create.do')"/> 
	
	
	

</body>
</html>