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




<form:form action="${requestURI}" modelAttribute="legalText">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
		<form:hidden path="trips" />



	<form:label path="title">
		<spring:message code="legalText.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="body">
		<spring:message code="legalText.body" />:
	</form:label>
	<br />
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="laws">
		<spring:message code="legalText.laws" /> (<spring:message code="general.split" />):
	</form:label>
	<br />
	<form:textarea path="laws" />
	<form:errors cssClass="error" path="laws" />
	<br />
	
	<form:label path="finalMode">
		<spring:message code="legalText.finalMode" />:
	</form:label>
	<spring:message code="template.no" var="no"/>
	<spring:message code="template.yes" var="yes"/>
	<form:select path="finalMode" >
		<form:option value="false" label="${no}"/>
		<form:option value="true" label = "${yes}"/>
	</form:select>
	<form:errors cssClass="error" path="finalMode" />
	<br />
	
<br /><br />
	<input type="submit" name="save" 
	onclick="return confirm('<spring:message code="legalText.confirm.save" />')"
		value="<spring:message code="legalText.save" />" />&nbsp; 


	<jstl:if test="${legalText.id != 0}">	
	<input type="submit" name="delete" 
	onclick="javascript: relativeRedir('legalText/admin/edit.do');"
		value="<spring:message code="legalText.delete" />" />&nbsp; 
	</jstl:if>
		
	
	
	<input type="button" name="cancel"
		value="<spring:message code="legalText.cancel" />"
		onclick="javascript: relativeRedir('legalText/admin/list.do');" />
	<br />
</form:form>


