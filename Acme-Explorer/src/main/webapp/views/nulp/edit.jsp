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

<form:form action="${requestURI}" modelAttribute="nulp">
	

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="code"/>
	
	
	
	<form:label path="shortTitle">
		<spring:message code="nulp.shortTitle"/>:
	</form:label>
	<form:input path="shortTitle" type="text" maxlength="100" />
	<form:errors cssClass="error" path="shortTitle"/>
	<br />
	
	<form:label path="description">
		<spring:message code="nulp.description"/>:
	</form:label>
	<form:input path="description" type="text" maxlength="250"/>
	<form:errors cssClass="error" path="description"/>
	<br />
	
	<form:label path="gauge">
		<spring:message code="nulp.gauge"/>:
	</form:label>
	<form:input path="gauge" type="number" max="3" min="1" value = "1"/>
	<form:errors cssClass="error" path="gauge"/>
	<br />
	
	<form:label path="moment">
		<spring:message code="nulp.moment"/>:
	</form:label>
	<form:input path="moment" type="text" placeholder="dd/MM/yyyy HH:mm" title="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="moment"/>
	<br />
	
	<form:label path="trip">
    <spring:message code="nulp.create.trip" />:
    </form:label>
    <form:select id="trips" path="trip" >   
    <form:options items="${trips}" itemValue="id"
      itemLabel="title" />
  </form:select>
  <form:errors cssClass="error" path="trip" />
  <br />
	
	
	

	<input type="submit" name="save" value="<spring:message code="nulp.save"/>"
	onclick="javascript:relativeRedir('nulp/manager/edit.do');"/> &nbsp;

	<jstl:if test="${nulp.id != 0}">	
	<input type="submit" name="delete" 
	onclick="javascript: relativeRedir('nulp/manager/edit.do');"
		value="<spring:message code="nulp.delete" />" />&nbsp; 
	</jstl:if>
	
	<input type="button" name="cancel" value="<spring:message code="nulp.cancel"/>" 
	onclick="javascript:relativeRedir('nulp/manager/list.do');"/>
	
	
</form:form>




</body>
</html>