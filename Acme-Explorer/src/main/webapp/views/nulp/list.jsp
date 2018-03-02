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



<display:table name="nulps" id ="row" requestURI="${requestURI}" pagesize="2" class="displaytag">
	
	
	<spring:message code="nulp.code" var="codeHeader"/>
	<display:column property="code" title="${codeHeader}" sortable="true">
	</display:column>
	
	<jstl:choose>
	<jstl:when test="${row.gauge == 1}">
	<spring:message code="nulp.gauge" var="gaugeHeader"/>
	<display:column  title="${gaugeHeader}" sortable="true">
	<div style="background: black;"> <jstl:out value="${row.gauge}"/></div>
	</display:column>
	</jstl:when>
	
	<jstl:when test="${row.gauge == 2}">
	<spring:message code="nulp.gauge" var="gaugeHeader"/>
	<display:column  title="${gaugeHeader}" sortable="true">
	<div style="background: hotpink;"> <jstl:out value="${row.gauge}"/></div>
	</display:column>
	</jstl:when>
	
	<jstl:when test="${row.gauge == 3}">
	<spring:message code="nulp.gauge" var="gaugeHeader"/>
	<display:column  title="${gaugeHeader}" sortable="true">
	<div style="background: Orchid;"> <jstl:out value="${row.gauge}"/></div>
	</display:column>
	</jstl:when>
	</jstl:choose>
	
	
	<spring:message code="event.format.date" var="pattern"/>
	<spring:message code="nulp.moment" var="momentHeader"/>
	<display:column property="moment" title="${momentHeader}" format="${pattern}" sortable="true">
	</display:column>
	
	<spring:message code="nulp.shortTitle" var="shortTitleHeader"/>
	<display:column property="shortTitle" title="${shortTitleHeader}" sortable="true">
	</display:column>
	
	<spring:message code="nulp.description" var="descriptionHeader"/>
	<display:column property="description" title="${descriptionHeader}" sortable="true">
	</display:column>
	
	<spring:message code="nulp.trip" var="tripHeader"/>
	<display:column title="${tripHeader}" sortable="true">
	<spring:url value="/trip/all/details.do" var="editURL">
		<spring:param name="tripId" value="${row.trip.id}"/>
	</spring:url>
	<a href="${editURL}">${row.trip.title}</a>
	</display:column>
	
	
	<security:authorize access="hasRole('MANAGER')">
	<security:authentication property="principal.username" var="username"/>
	   
	     <jstl:if test="${row.trip.manager.userAccount.username eq username}">	
	 	  <display:column title="${editHeader}">
	      <spring:message code="trip.edit" var="editHeader" />
			<spring:url value="/nulp/manager/edit.do" var="editURL">
		      <spring:param name="nulpId" value="${row.id}"></spring:param>
	      </spring:url>
	      <a href="${editURL}"> <spring:message code="nulp.edit" /></a>
 		  </display:column> 
	     </jstl:if>
	   
	 </security:authorize>


</display:table>


	<input type="button" name="back" value="<spring:message code="nulp.back"/>" 
		onclick="javascript:relativeRedir('trip/all/list.do');"/>

		
</body>
</html>