<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

	<display:table sort="list" name="sponsorships" id="row" requestURI="sponsorship/sponsor/list.do"
		pagesize="4" class="displaytag">

		<spring:message code="sponsorship.banner" var="bannerHeader" />
		<display:column property="banner" title="${bannerHeader}" sortable="false"/>
			


		<spring:message code="sponsorship.infoPage" var="infoPageHeader" />
		<display:column title="${infoPageHeader}"
			sortable="false" >
			<a href="${infoPageHeader}">${row.infoPage}</a>
		</display:column>

	

		<spring:message code="sponsorship.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<input type="submit" name="edit" value="<spring:message code="sponsorship.edit"/>"
			onclick="javascript: window.location.href = './sponsorship/sponsor/edit.do?sponsorshipId=${row.id}';"/>
		</display:column> 
		
		
	</display:table> 

	 
		<input type="button" name="create"
			value="<spring:message code="sponsorship.create"/>"
			onclick="javascript: window.location.href = './sponsorship/sponsor/create.do';" />
	

</body>
</html>