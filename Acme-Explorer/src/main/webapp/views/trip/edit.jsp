<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<form:form action="${requestURI}" modelAttribute="trip">
	

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	<form:hidden path="price"/>
	<form:hidden path="stages"/>
	<form:hidden path="tags"/>
<%-- 	<form:hidden path="legalText"/> --%>
	<form:hidden path="survivalClasses"/>
	<form:hidden path="manager"/>
	<form:hidden path="finders"/>
	<form:hidden path="applications"/>
	<form:hidden path="stories"/>
	<form:hidden path="notes"/>
	<form:hidden path="audits"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="nulps"/>
	
	
	
	
	<form:label path="title">
		<spring:message code="trip.title"/>:
	</form:label>
	<form:input path="title" type="text"/>
	<form:errors cssClass="error" path="title"/>
	<br />
	
	
	<form:label path="description">
		<spring:message code="trip.description"/>:
	</form:label>
	<form:input path="description" type="text"/>
	<form:errors cssClass="error" path="description"/>
	<br />
	
	
	<form:label path="requirements">
		<spring:message code="trip.requirements"/>(<spring:message code="general.split" />):
	</form:label>
	<form:textarea path="requirements" type="text"/>
	<form:errors cssClass="error" path="requirements"/>
	
	<br />
	
	<spring:message code="trip.placeholderDate" var="patternDate"/>
	<form:label path="starts">
		<spring:message code="trip.starts"/>:
	</form:label>	
	<form:input path="starts" type="text" placeholder = "${patternDate}"/>
	<form:errors cssClass="error" path="starts"/>
	<br />
	
	<spring:message code="trip.placeholderDate" var="patternDate"/>
	<form:label path="ends">
		<spring:message code="trip.ends"/>:
	</form:label>
	<form:input path="ends" type="text" placeholder = "${patternDate}"/>
	<form:errors cssClass="error" path="ends"/>
	<br />
	
	<spring:message code="trip.ranger"/>:
	<form:select path="ranger" >
	<form:options items="${rangers}" itemLabel="name" itemValue="id"/>
	</form:select>
	<br />
	
	<spring:message code="trip.category"/>:
	<form:select path="category" >
	<form:options items="${categories}" itemLabel="name" itemValue="id"/>
	</form:select>
	<br />
	
	<spring:message code="trip.legalText"/>:
	<form:select path="legalText" >
	<form:option value="0" label="---------"/>
	<form:options items="${legalTexts}" itemLabel="title" itemValue="id"/>
	</form:select>
	<br />
	

	<input type="submit" name="save" value="<spring:message code="trip.save"/>"
	onclick="javascript:relativeRedir('trip/manager/edit.do');"/> &nbsp;

	<jstl:if test="${trip.id != 0}">	
	<input type="submit" name="delete" 
	onclick="javascript: relativeRedir('trip/manager/edit.do');"
		value="<spring:message code="template.delete" />" />&nbsp; 
	</jstl:if>
	
	<input type="button" name="cancel" value="<spring:message code="template.back"/>" 
	onclick="javascript:relativeRedir('trip/manager/list.do');"/>
	
	
</form:form>




</body>
</html>