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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div style="color:darkred;"><h2><b><jstl:out value="${mensajes[0].folder.name}"/>
<jstl:if test="${mensajes.size()==0}">
	<jstl:out value="${box}"/>
</jstl:if>
</b></h2></div>

<display:table name="mensajes" id="row" requestURI="message/actor/list.do"
	pagesize="5" class="displaytag">


	<!-- Action links -->

	<spring:message code="message.subject" var="subject"></spring:message>
	<display:column property="subject" title="${subject}"></display:column>
	<spring:message code="message.sender" var="sender"></spring:message>
	<display:column property="sender.userAccount.username" title="${sender}"></display:column>
	<spring:message code="message.recipient" var="recipient"></spring:message>
	<display:column property="recipient.userAccount.username" title="${recipient}"></display:column>
	<spring:message code="message.date" var="date"></spring:message>
	<display:column property="date" title="${date}"></display:column>
	<spring:message code="message.priority" var="priority"></spring:message>
	<display:column title="${priority}">
		<jstl:if test="${row.priority=='LOW'}">
			<div style="color:green;">
				<b>${row.priority}</b>
			</div>
		</jstl:if>
		<jstl:if test="${row.priority=='MEDIUM'}">
			<div style="color:blue;">
				<b>${row.priority}</b>
			</div>
		</jstl:if>
		<jstl:if test="${row.priority=='HIGH'}">
			<div style="color:red;">
				<b>${row.priority}</b>
			</div>
		</jstl:if>
	</display:column>
	
	<spring:message code="message.see" var="see"/>
	<display:column title="${see}">
		<input type="button"
				value="${see}"
				onclick="document.location.href= 'message/actor/details.do?messageId=${row.id}';" />
	</display:column>
	
	<spring:message code="message.delete" var="delete"/>
	<display:column title="${delete}">
		<input type="button"
				value="${delete}"
				onclick="document.location.href= 'message/actor/delete.do?messageId=${row.id}';" />
	</display:column>
	
	<spring:message code="message.move" var="move"/>
	<display:column title="${move}">
		<input type="button"
				value="${move}"
				onclick="document.location.href= 'message/actor/move.do?messageId=${row.id}';" />
	</display:column>
</display:table>


<security:authorize access="isAuthenticated()">
	<div>
		<a href="message/actor/create.do"> <spring:message
				code="folder.message" />
		</a>
	</div>
</security:authorize>
