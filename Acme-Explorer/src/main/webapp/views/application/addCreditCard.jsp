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




<form:form action="${requestURI }" modelAttribute="creditCard">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="application" />
		
		



	<form:label path="holderName">
		<spring:message code="cc.holderName" />:
	</form:label>
	<form:input path="holderName" />
	<form:errors cssClass="error" path="holderName" />
	<br />
	<form:label path="brandName">
		<spring:message code="cc.brandName" />:
	</form:label>
	<form:input path="brandName" />
	<form:errors cssClass="error" path="brandName" />
	<br />
	<form:label path="number">
		<spring:message code="cc.number" />:
	</form:label>
	<form:input path="number" />
	<form:errors cssClass="error" path="number" />
	<br />
	<form:label path="expirationYear">
		<spring:message code="cc.expirationYear" />:
	</form:label>
	<form:input path="expirationYear" />
	<form:errors cssClass="error" path="expirationYear" />
	<br />
	<form:label path="expirationMonth">
		<spring:message code="cc.expirationMonth" />:
	</form:label>
	<form:input path="expirationMonth" />
	<form:errors cssClass="error" path="expirationMonth" />
	<br />
	<form:label path="CVV">
		<spring:message code="cc.CVV" />:
	</form:label>
	<form:input path="CVV" />
	<form:errors cssClass="error" path="CVV" />
	<br />
	
	
	

	<input type="submit" name="save" 
		value="<spring:message code="template.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="template.cancel" />"
		onclick="document.location.href='application/explorer/list.do';" />
	<br />
</form:form>


