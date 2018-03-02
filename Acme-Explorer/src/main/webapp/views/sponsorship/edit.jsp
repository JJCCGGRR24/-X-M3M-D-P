<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="sponsor"/>
	<form:hidden path="trips"/>
	
	
	<form:label path="banner">
		<spring:message code="sponsorship.banner"/>:
	</form:label>
	<form:input path="banner" type="url" placeholder="Introduzca una url válida"/>
	<form:errors cssClass="error" path="banner"/>
	<br />
	
	<form:label path="infoPage">
		<spring:message code="sponsorship.infoPage"  />:
	</form:label>
	<form:input path="infoPage" type="url" placeholder="Introduzca una url válida"/>
	<form:errors cssClass="error" path="infoPage" />
	<br />
	
	
	
	<form:label path="creditCard.holderName">
		<spring:message code="sponsorship.creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" type="text"/>
	<form:errors cssClass="error" path="creditCard.holderName"/>
	<br />
	
	<spring:message code="sponsorship.creditCard.brandName"/>
	<form:select path="creditCard.brandName" >
		<form:option value="0" label="---------"/>
		<form:option value="VISA" label="VISA"/>
		<form:option value="MASTERCARD" label="MASTERCARD"/>
		<form:option value="DISCOVER" label="DISCOVER"/>
		<form:option value="DINNERS" label="DINNERS"/>
		<form:option value="AMEX" label="AMEX"/>
	
	</form:select>
	<br />
	
	<form:label path="creditCard.number">
		<spring:message code="sponsorship.creditCard.number" />:
	</form:label>
	<form:input path="creditCard.number" type="text"/>
	<form:errors cssClass="error" path="creditCard.number"/>
	<br />
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="sponsorship.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" type="text"/>
	<form:errors cssClass="error" path="creditCard.expirationYear"/>
	<br />
	
	<form:label path="creditCard.expirationMonth">
		<spring:message code="sponsorship.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" type="text"/>
	<form:errors cssClass="error" path="creditCard.expirationMonth"/>
	<br />
	
	<form:label path="creditCard.CVV">
		<spring:message code="sponsorship.creditCard.CVV" />:
	</form:label>
	<form:input path="creditCard.CVV" type="text"/>
	<form:errors cssClass="error" path="creditCard.CVV"/>
	<br />
	
<input type="submit" name="save" value="<spring:message code="sponsorship.save"/>" />

	<jstl:if test="${sponsoship.id != 0}">
		<input type="submit" name="delete" value='<spring:message code="template.delete"/>'>
	</jstl:if>


<input type="button" value="<spring:message code="sponsorship.cancel"/>" 
onclick="document.location.href='sponsorship/sponsor/list.do'"/>

</form:form>


</body>
</html>