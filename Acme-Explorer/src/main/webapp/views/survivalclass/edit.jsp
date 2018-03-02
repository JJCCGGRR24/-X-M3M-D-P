<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="survivalClass/manager/edit.do" modelAttribute="survivalclass">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="explorers" />
	


<form:label path="trip">
	<spring:message code="survivalClass.trip" />:
	</form:label>
	<jstl:if test="${survivalclass.id eq 0}">
	<form:select path="trip">
		<form:options items="${trips}" itemLabel="ticker"
			itemValue="id" />
	</form:select>
	</jstl:if>
	<jstl:if test="${not(survivalclass.id eq 0)}">
		<jstl:out value="${survivalclass.trip.ticker}"/>
		<form:hidden path="trip" />
	</jstl:if>
	<form:errors cssClass="error" path="trip" />
	<br />

	<form:label path="title">
		<spring:message code="educationRecord.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="description">
		<spring:message code="survivalClass.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="date">
		<spring:message code="survivalClass.date" />:
	</form:label>
	<form:input path="date" />
	<form:errors cssClass="error" path="date" />
	<br />
	
	<form:label path="location.name">
		<spring:message code="location.name" />:
	</form:label>
	<form:input path="location.name" />
	<form:errors cssClass="error" path="location.name" />
	<br />
	
	<form:label path="location.latitude">
		<spring:message code="location.latitude" />:
	</form:label>
	<form:input path="location.latitude" />
	<form:errors cssClass="error" path="location.latitude" />
	<br />
	
	<form:label path="location.altitude">
		<spring:message code="location.altitude" />:
	</form:label>
	<form:input path="location.altitude" />
	<form:errors cssClass="error" path="location.altitude" />
	<br />
	
	<jstl:if test="${survivalclass.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="template.delete" />"
			onclick="return confirm('<spring:message code="template.confirm" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" class="btn btn-warning" name="cancel" value='<spring:message code="template.cancel"/>' onclick="document.location.href='survivalClass/manager/list.do' ;">
	<input type="submit" class="btn btn-success" name="save" value='<spring:message code="template.save"/>'>
	
	
</form:form>
