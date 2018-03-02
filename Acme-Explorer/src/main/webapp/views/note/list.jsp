<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

	<display:table sort="list" name="notes" id="row" requestURI="${requestURI}"
		pagesize="4" class="displaytag">

		<spring:message code="note.trip" var="tripHeader" />
		<display:column title="${tripHeader}" sortable="false">
			<spring:url
				value="/all/notes/byTrip.do"
				var="editURL">
				<spring:param name="tripId" value="${row.trip.id}"></spring:param>
			</spring:url>
			<a>${row.trip.ticker} - ${row.trip.title}</a>
		</display:column>

		<security:authorize access="hasRole('MANAGER')">
			<spring:message code="note.auditor" var="auditorHeader" />
			<display:column title="${auditorHeader}" sortable="false">
				<spring:url
					value="all/notes/byAuditor.do"
					var="editURL">
					<spring:param name="auditorId" value="${row.auditor.id}"/>
				</spring:url>
				<a>${row.auditor.userAccount.username}</a>
			</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<spring:message code="note.manager" var="auditorHeader" />
			<display:column title="${auditorHeader}" sortable="false">
				<a>${row.trip.manager.userAccount.username}</a>
			</display:column>
		</security:authorize>


		<spring:message code="note.remark" var="remarkHeader" />
		<display:column property="remark" title="${remarkHeader}"
			sortable="true" />

		

		<%--Aqui compruebo que sea manager y si es asi muestro la respuesta si las hay sino pongo el boton de responder--%>
		<security:authorize access="hasRole('MANAGER')">
		<spring:message code="note.comment" var="commentHeader" />
<%-- 		<display:column property="comment" title="${commentHeader}" sortable="false"> --%>
			<display:column title="${commentHeader}" sortable="false">
				<jstl:choose>
					<jstl:when test="${row.momentReply eq null}">
						<input type="button" name="reply"
							value="<spring:message code="note.reply"/>"
							onclick="document.location.href='note/manager/edit.do?noteId=' + ${row.id};" />
					</jstl:when>
					<jstl:otherwise>
 						<jstl:out value="${row.comment}" />
					</jstl:otherwise>

				</jstl:choose>
			</display:column>
		</security:authorize>

		<%--Aqui compruebo que sea auditor y si es asi muestro la respuesta haya o no --%>
		 <security:authorize access="hasRole('AUDITOR')">
			<spring:message code="note.comment" var="commentHeader" />
			<display:column property="comment" title="${commentHeader}"
				sortable="false">
			</display:column>
			</security:authorize>
			
		<spring:message code="note.momentReply" var="momentReplyHeader" />
		<display:column property="momentReply" title="${momentReplyHeader}"
			sortable="true" format="${event.format.date}"/>
		
	</display:table> 

</body>
</html>