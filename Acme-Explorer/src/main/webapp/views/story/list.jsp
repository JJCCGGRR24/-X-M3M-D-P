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

	<display:table sort="list" name="stories" id="row" requestURI="story/explorer/list.do"
		pagesize="4" class="displaytag">

		<spring:message code="story.title" var="title" />
		<display:column property="title" title="${title}" />
		
		<spring:message code="story.text" var="text" />
		<display:column property="text" title="${text}" />
		
		<spring:message code="story.attachments" var="attachments" />
		<display:column  title="${attachments}">
		<jstl:forEach items="${row.attachments}" var="x">
			<a href='<jstl:out value="${x}"/>'><jstl:out value="${x}"/></a><br/>
		</jstl:forEach>	
		</display:column>
		<spring:message code="story.trip" var="trip" />
		<display:column title="${trip}" > 
			<input type="button" name="create"
			value="<spring:message code="story.trip"/>"
			onclick="javascript: window.location.href = './all/trip/details.do?tripId=' + ${row.trip.id};" />
		</display:column>
		
	</display:table> 

	 
		<input type="button" name="create"
			value="<spring:message code="sponsorship.create"/>"
			onclick="javascript: window.location.href = './story/explorer/create.do';" />
	

</body>
</html>