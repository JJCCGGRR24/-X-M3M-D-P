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



		<spring:message code="error.noCurricula" var="noCurricula" />
		<h3 style="color: red;"> <jstl:out value="${noCurricula }"> </jstl:out> </h3>


	

		<%-- <input type="button" name="socialsIdentities" value="<spring:message code="actor.socialIdentities"/>"
		onclick="javascript: relativeRedir('socialIdentity/all/list.do?actorId=' + ${row.id})">  --%>


