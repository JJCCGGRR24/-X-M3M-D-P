

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message code="dashboard.query1" var="q1"/>
<spring:message code="dashboard.query2" var="q2"/>
<spring:message code="dashboard.query3" var="q3"/>
<spring:message code="dashboard.query4" var="q4"/>
<spring:message code="dashboard.query5" var="q5"/>
<spring:message code="dashboard.query6" var="q6"/>
<spring:message code="dashboard.query7" var="q7"/>
<spring:message code="dashboard.query8" var="q8"/>
<spring:message code="dashboard.query9" var="q9"/>
<spring:message code="dashboard.query10" var="q10"/>
<spring:message code="dashboard.query11" var="q11"/>
<spring:message code="dashboard.queryB1" var="qb1"/>
<spring:message code="dashboard.queryB2" var="qb2"/>
<spring:message code="dashboard.queryB3" var="qb3"/>
<spring:message code="dashboard.queryB4" var="qb4"/>
<spring:message code="dashboard.queryB5" var="qb5"/>
<spring:message code="dashboard.queryB6" var="qb6"/>
<spring:message code="dashboard.queryB7" var="qb7"/>


<jstl:out value="${q1}:"/><br/><b>
	<spring:message code="dashboard.avg"/> <fmt:formatNumber value="${queryC1[0]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.min"/> <fmt:formatNumber value="${queryC1[1]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.max"/> <fmt:formatNumber value="${queryC1[2]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.stdev"/> <fmt:formatNumber value="${queryC1[3]}" maxFractionDigits="2"/><br/>
	
</b><br/><br/>

<jstl:out value="${q2}:"/><br/><b>
	<spring:message code="dashboard.avg"/> <fmt:formatNumber value="${queryC2[0]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.min"/> <fmt:formatNumber value="${queryC2[1]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.max"/> <fmt:formatNumber value="${queryC2[2]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.stdev"/> <fmt:formatNumber value="${queryC2[3]}" maxFractionDigits="2"/><br/>
</b><br/><br/>

<jstl:out value="${q3}:"/><br/><b>
	<spring:message code="dashboard.avg"/> <fmt:formatNumber value="${queryC3[0]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.min"/> <fmt:formatNumber value="${queryC3[1]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.max"/> <fmt:formatNumber value="${queryC3[2]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.stdev"/> <fmt:formatNumber value="${queryC3[3]}" maxFractionDigits="2"/><br/>
</b><br/><br/>

<jstl:out value="${q4}:"/><br/><b>
	<spring:message code="dashboard.avg"/> <fmt:formatNumber value="${queryC4[0]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.min"/> <fmt:formatNumber value="${queryC4[1]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.max"/> <fmt:formatNumber value="${queryC4[2]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.stdev"/> <fmt:formatNumber value="${queryC4[3]}" maxFractionDigits="2"/><br/>
</b><br/><br/>

<jstl:out value="${q5}:"/><br/><b>
<fmt:formatNumber value="${queryC5}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${q6}:"/><br/><b>
<fmt:formatNumber value="${queryC6}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${q7}:"/><br/><b>
<fmt:formatNumber value="${queryC7}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${q8}:"/><br/><b>
<fmt:formatNumber value="${queryC8}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${q9}:"/><br/><b>
<fmt:formatNumber value="${queryC9}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${q10}:"/><br/><b>
<jstl:forEach var="act" items="${queryC10}">
	<jstl:out value="${act.ticker}, ${act.title}"/><br/>
</jstl:forEach></b><br/><br/>

<jstl:out value="${q11}:"/>
<table border="1" style="width:auto;"> 
<jstl:forEach var="act" items="${queryC11}">
<tr><td>
	<jstl:out value="${act[0]}"/>
</td>
<td>
	<fmt:formatNumber value="${act[1]}" maxFractionDigits="2"/>
</td></tr>
</jstl:forEach>
</table><br/>


<jstl:out value="${qb1}:"/><br/><b>
	<spring:message code="dashboard.min"/> <fmt:formatNumber value="${queryB1[0]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.avg" /> <fmt:formatNumber value="${queryB1[1]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.max"/> <fmt:formatNumber value="${queryB1[2]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.stdev"/> <fmt:formatNumber value="${queryB1[3]}" maxFractionDigits="2"/><br/>
</b><br/><br/>

<jstl:out value="${qb2}:"/><br/><b>
	<spring:message code="dashboard.avg"/> <fmt:formatNumber value="${queryB2[0]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.min"/> <fmt:formatNumber value="${queryB2[1]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.max"/> <fmt:formatNumber value="${queryB2[2]}" maxFractionDigits="2"/><br/>
	<spring:message code="dashboard.stdev"/> <fmt:formatNumber value="${queryB2[3]}" maxFractionDigits="2"/><br/>
</b><br/><br/>

<jstl:out value="${qb3}:"/><br/><b>
<fmt:formatNumber value="${queryB3}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${qb4}:"/><br/><b>
<fmt:formatNumber value="${queryB4}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${qb5}:"/><br/><b>
<fmt:formatNumber value="${queryB5}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${qb6}:"/><br/><b>
<fmt:formatNumber value="${queryB6}" maxFractionDigits="2"/></b><br/><br/>

<jstl:out value="${qb7}:"/><br/><b>
<fmt:formatNumber value="${queryB7}" maxFractionDigits="2"/></b><br/><br/>




