<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>


<b><spring:message code="trip.price"/>: </b><jstl:out value="${trip.price}"/> <br><br>

<b><spring:message code="trip.title"/>: </b><jstl:out value="${trip.title}"/> <br><br>

<b><spring:message code="trip.description"/>: </b><jstl:out value="${trip.description}"/> <br><br>

<b><spring:message code="trip.category"/>: </b><jstl:out value="${trip.category.name}"/> <br><br>

<b><spring:message code="trip.tags"/>: </b> <br>
	<jstl:forEach items="${trip.tags}" var="er" varStatus="loop">
	 - <jstl:out value="${er.name}"/> <br><br>
	</jstl:forEach>

<b><spring:message code="trip.starts"/>: </b><jstl:out value="${trip.starts}"/> <br><br>

<b><spring:message code="trip.ends"/>: </b><jstl:out value="${trip.ends}"/> <br><br>

<b><spring:message code="trip.requirements"/>: </b> <br>
	<jstl:forEach items="${trip.requirements}" var="er" varStatus="loop">
		- <jstl:out value="${er}"/> <br>
	</jstl:forEach><br>

<b><spring:message code="trip.stages"/>: </b> <br>
	<jstl:forEach items="${trip.stages}" var="er" varStatus="loop">
		- <jstl:out value="${er.title}"/>: <jstl:out value="${er.description}"/> <br>
	</jstl:forEach><br>
	
<b><spring:message code="trip.ranger"/>: </b> <spring:url value="/profile/details.do" var="editURL">
		<spring:param name="actorId" value="${trip.ranger.id}" />
		</spring:url>
		<a href="${editURL}">${trip.ranger.name}</a> <br><br>	
		
<b><spring:message code="trip.manager"/>: </b> <spring:url value="/profile/details.do" var="editURL">
		<spring:param name="actorId" value="${trip.manager.id}" />
		</spring:url>
		<a href="${editURL}">${trip.manager.name}</a> <br><br>			
		
<b><spring:message code="trip.survivalClasses"/>: </b> <br>
	<jstl:forEach items="${trip.survivalClasses}" var="er" varStatus="loop">
		  <u><spring:message code="trip.title"/></u> : <jstl:out value="${er.title}"/> <br>
		  <u><spring:message code="trip.description"/></u>:<jstl:out value="${er.description}"/> <br>
		  <u><spring:message code="trip.date"/></u>:<jstl:out value="${er.date}"/> <br> <br>
	</jstl:forEach>

<b><spring:message code="trip.stories"/>: </b> <br>
	<jstl:forEach items="${trip.stories}" var="er" varStatus="loop">
		 <u><jstl:out value="${er.title}"/></u>: <jstl:out value="${er.text}"/> <br><br>
	</jstl:forEach>
	
	
	
<%-- <b><spring:message code="trip.notifications"/>: </b> <br>
	<jstl:forEach items="${trip.notifications}" var="er" varStatus="loop">
		  <u><spring:message code="trip.notification.code"/></u> : <jstl:out value="${er.code}"/> <br>
		  
		  
		  <u><spring:message code="trip.notification.indicator"/></u>:<jstl:out value="${er.indicator}"/> <br>
		  <u><spring:message code="trip.notification.moment"/></u>:<jstl:out value="${er.moment}"/> <br> <br>
	</jstl:forEach> --%>
	
		
<security:authorize access="isAnonymous()" >
<b><spring:message code="trip.audits"/>: </b> <br>
	<jstl:forEach items="${trip.audits}" var="er" varStatus="loop">
		 <u><spring:message code="trip.title"/></u> :<jstl:out value="${er.title}"/><br>
		 <u><spring:message code="trip.description"/></u>:<jstl:out value="${er.description}"/> <br><br>
	</jstl:forEach><br>	
</security:authorize>
	
	
</body>
</html>