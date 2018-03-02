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


<ul>
	<li><spring:message code="message.recipient" /><b><jstl:out value=': ${mesage.recipient.userAccount.username}'></jstl:out></b></li>
	<li><spring:message code="message.sender" /><b><jstl:out value=': ${mesage.sender.userAccount.username}'></jstl:out></b></li>
	<li><spring:message code="message.priority" /><b><jstl:out value=': ${mesage.priority}'></jstl:out></b></li>
	<li><spring:message code="message.date" /><b><jstl:out value=': ${mesage.date}'></jstl:out></b></li>
	<li><spring:message code="message.subject" /><b><jstl:out value=': ${mesage.subject}'></jstl:out></b></li>
	<li><spring:message code="message.body" /><b><jstl:out value=': ${mesage.body}'></jstl:out></b></li>
</ul>

