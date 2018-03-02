<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<form:form action="audit/auditor/edit.do" modelAttribute="audit">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="auditor"/>
	<form:hidden path="trip"/> 
<%-- 	<form:hidden path="attachments"/>  --%>
	
	<form:label path="title">
		<spring:message code="audit.title"/>:
	</form:label>
	<form:input path="title" type="text"/>
	<form:errors cssClass="error" path="title"/>
	<br />
	
	<form:label path="description">
		<spring:message code="audit.description"/>:
	</form:label>
	<form:input path="description" type="text"/>
	<form:errors cssClass="error" path="description"/>
	<br />
	
	<form:label path="attachments">
		<spring:message code="audit.attachments"/> (<spring:message code="general.split" />):
	</form:label>
	<form:textarea path="attachments" type="text"/>
	<form:errors cssClass="error" path="attachments"/>
	<br />
	<br />
	
	<form:label path="finalMode">
		<spring:message code="audit.finalMode" />:
	</form:label>
	<spring:message code="template.no" var="no"/>
	<spring:message code="template.yes" var="yes"/>
	<form:select path="finalMode" >
		<form:option value="false" label="${no}"/>
		<form:option value="true" label = "${yes}"/>
	</form:select>
	<form:errors cssClass="error" path="finalMode" />
	<br />
	
	<input type="submit" name="save" value='<spring:message code="template.save"/>'>
	
	<jstl:if test="${audit.id != 0}">
		<input type="submit" name="delete" value='<spring:message code="template.delete"/>'>
	</jstl:if>
	
	<input type="button" name="cancel" value='<spring:message code="template.cancel"/>' onclick="document.location.href='audit/auditor/list.do';">


	

</form:form>


</body>
</html>