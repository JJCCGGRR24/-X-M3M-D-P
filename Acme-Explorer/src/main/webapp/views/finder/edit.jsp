<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<form:form action="finder/explorer/edit.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="momentCache"/>
	<form:hidden path="explorer"/>
	<form:hidden path="trips"/>

	<form:label path="minimumPrice">
		<spring:message code="finder.minimumPrice" />:
	</form:label>
	<form:input path="minimumPrice" />
	<form:errors cssClass="error" path="minimumPrice" />
	<br />
	
	<form:label path="maximumPrice">
		<spring:message code="finder.maximumPrice" />:
	</form:label>
	<form:input path="maximumPrice" />
	<form:errors cssClass="error" path="maximumPrice" />
	<br />
	
	<form:label path="keyword">
		<spring:message code="finder.keyword" />:
	</form:label>
	<form:input path="keyword" />
	<form:errors cssClass="error" path="keyword" />
	<br />
	
	<form:label path="dateMin">
		<spring:message code="finder.dateMin" />:
	</form:label>
	<form:input path="dateMin" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="dateMin" />
	<br />
	
	<form:label path="dateMax">
		<spring:message code="finder.dateMax" />:
	</form:label>
	<form:input path="dateMax" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="dateMax" />
	<br />
	
	<br/>
	
	<input type="submit" name="save"
		value="<spring:message code="folder.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="folder.cancel" />"
		onclick="document.location.href= 'finder/explorer/list.do';" />
	<br />

</form:form>
