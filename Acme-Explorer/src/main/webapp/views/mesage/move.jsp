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



<form:form action="message/actor/move.do" modelAttribute="mensaje">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="date"/>
	<form:hidden path="sender"/>
	<form:hidden path="recipient"/>
	<form:hidden path="subject"/>
	<form:hidden path="body"/>
	<form:hidden path="priority"/>

	<form:label path="folder">
		<spring:message code="message.folder" />:
	</form:label>
	<form:select path="folder">
		<form:options items="${folders}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="folder" />
	<br />
	<br/>
	
	<input type="submit" name="save"
		value="<spring:message code="message.save" />" />&nbsp;
		
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="document.location.href= 'message/actor/list.do?folderId=${mensaje.folder.id}';" />
	<br />

</form:form>
