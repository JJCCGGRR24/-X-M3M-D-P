<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

<input type="text" id="textSearch" value="">
<input type="button" id="buttonSearch"
	value="<spring:message code="trip.search"/>" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#buttonSearch").click(function() {
			if ($("#textSearch").val()!="")
				window.location.replace('trip/all/list.do?search='+ $("#textSearch").val());
		});
		$("#textSearch").on('keyup',function(e) {
			if (e.keyCode === 13 && $("#textSearch").val()!="")
				window.location.replace('trip/all/list.do?search='+ $("#textSearch").val());
			e.preventDefault();
		});
	});
</script>

<display:table name="trips" id ="row" requestURI="${requestURI}" pagesize="3" class="displaytag">
	<jstl:if test="${vistaMyTrips }">
	<security:authorize access="hasRole('MANAGER')" >	
		<security:authentication property="principal.username" var="username"/>
		<spring:message code="trip.edit" var="editHeader" />
			<display:column title="${editHeader}">
			<jstl:if test ="${empty row.publicationDate}">
			<jstl:if test="${empty row.cancelledReason}">
			<jstl:if test="${row.manager.userAccount.username eq username}">
			<spring:url
					value="/trip/manager/edit.do"
					var="editURL">
					<spring:param name="tripId" value="${row.id}"></spring:param>
				</spring:url>
				<a href="${editURL}"> <spring:message code="trip.edit" /></a>
			</jstl:if>
			</jstl:if>
			</jstl:if>
		</display:column> 
	</security:authorize>
	</jstl:if>
	
	<spring:message code="trip.ticker" var="tickerHeader"/>
	<display:column title="${tickerHeader}" sortable="true">
	<spring:url value="/trip/all/details.do" var="editURL">
			<spring:param name="tripId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}">${row.ticker}</a><br/>
	</display:column>
	
	<spring:message code="trip.title"  var="titleHeader"/>
	<display:column property="title" title="${titleHeader}" sortable ="true"/>
	
	<spring:message code="trip.description" var="descriptionHeader"/>
	<display:column property="description" title="${descriptionHeader}" sortable ="true"/>
	
	
	<spring:message code="trip.category" var="categoryHeader"/>	
		<display:column  title="${categoryHeader}" sortable="true">
			<security:authorize access="!hasRole('MANAGER')">
			<spring:url value="/trip/all/list.do" var="editURL">
			<spring:param name="categoryId" value="${row.category.id}" />
			</spring:url>
			<a href="${editURL}">${row.category.name}</a><br/>
			</security:authorize>
			
			<security:authorize access="hasRole('MANAGER')">
			<spring:url value="/trip/manager/list.do" var="editURL2">
			<spring:param name="categoryId" value="${row.category.id}" />
			</spring:url>
			<a href="${editURL2}">${row.category.name}</a><br/>
			</security:authorize>
			
		</display:column>
		
	
	<spring:message  code="trip.tags" var="tagsHeader" />
		<display:column title="${tagsHeader}" sortable="true">
		<spring:url
				value="/tag/all/list.do"
				var="editURL">
				<spring:param name="tripId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${editURL}"> <spring:message code="trip.tags" /></a>
		</display:column> 

	 
	<spring:message code="trip.manager" var="managerHeader"/>
	<display:column  title="${managerHeader}" sortable ="true">
	 <spring:url value="/profile/details.do" var="editURL">
		<spring:param name="actorId" value="${row.manager.id}" />
		</spring:url>
		<a href="${editURL}">${row.manager.name}</a>
	  </display:column>
	
	
	<spring:message code="trip.ranger" var="rangerHeader"/>
	<display:column title="${rangerHeader}" sortable ="true">
	<spring:url value="/profile/details.do" var="editURL">
		<spring:param name="actorId" value="${row.ranger.id}" />
		</spring:url>
		<a href="${editURL}">${row.ranger.name}</a>
	</display:column>	
	
	<spring:message code="event.format.date" var="pattern"/>
	<spring:message code="trip.starts" var="startsHeader"/>
		<display:column property="starts" title="${startsHeader}" sortable ="true" format="${pattern}">
		</display:column>
	

	<spring:message code="event.format.date" var="pattern"/>	
	<spring:message code="trip.ends" var="endsHeader"/>
		<display:column property="ends" title="${endsHeader}" sortable ="true" format="${pattern}">
		</display:column>
	
	<spring:message code="event.format.price" var="patternPrice"/>
	<spring:message code="trip.price" var="priceHeader" />
		<display:column property="price" title="${priceHeader}" sortable ="true" format="${patternPrice}"/>

	<security:authorize access="hasRole('MANAGER')" >	
	<security:authentication property="principal.username" var="username"/>	
		<spring:message code="trip.cancelledReason" var="cancelledReasonHeader" />
		<display:column  title="${cancelledReasonHeader}" sortable ="false">
			<jstl:choose>
				<jstl:when test="${empty row.cancelledReason}">
					<jstl:if test="${not empty row.publicationDate}">
					<jstl:if test="${row.manager.userAccount.username eq username}">
					<jstl:if test="${fechaActual lt row.starts}">
					
						<spring:message code="trip.cancel" var="editHeader" />
						<spring:url
								value="/trip/manager/cancel.do"
								var="editURL">
								<spring:param name="tripId" value="${row.id}"></spring:param>
						</spring:url>
						<a href="${editURL}"> <spring:message code="trip.cancel" /></a>
					</jstl:if>
					</jstl:if>
					</jstl:if>
				</jstl:when> 	
				<jstl:otherwise>
					<jstl:out value="${row.cancelledReason}"></jstl:out>
				</jstl:otherwise>
			</jstl:choose> 
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')" >		
	<security:authentication property="principal.username" var="username"/>
	 <spring:message code="trip.publicationDate" var="publicationDateReasonHeader" />
		<display:column  title="${publicationDateReasonHeader}" sortable ="false">
		
		<jstl:choose>
		
		<jstl:when test="${empty row.publicationDate}">
		<jstl:if test="${empty row.cancelledReason}">
		<jstl:if test="${row.manager.userAccount.username eq username}">
		
		<spring:url
				value="/trip/manager/publish.do"
				var="editURL">
				<spring:param name="tripId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${editURL}"> <spring:message code="trip.publish" /></a>
		</jstl:if>
		</jstl:if>
	
		</jstl:when> 
		<jstl:otherwise>
		
		<jstl:out value="${row.publicationDate}"></jstl:out>
		</jstl:otherwise>
		</jstl:choose> 
		</display:column>
	</security:authorize>
	
	<spring:message code="trip.sponsorshipBanner" var="sponsorshipBanner"/>
	<display:column  title="${sponsorshipBanner}" sortable ="false">
		<img style="max-height: 100px; max-width: 100px;" src="${banners.get(row_rowNum-1)}" />
	</display:column>
	
	<security:authorize access="hasRole('AUDITOR')" >		
	<spring:message code="trip.audit" var="audit" />
		<display:column  title="${audit}" sortable ="false">
		
		<jstl:choose>
		<jstl:when test="${auditsAuditor.contains(row)}">
		
		</jstl:when>
		<jstl:otherwise>
		<input type="button" name="new" value="<spring:message code="template.new"/>"
		onclick="javascript:relativeRedir('audit/auditor/create.do?tripId='+ ${row.id});"/>
		</jstl:otherwise>
		</jstl:choose>

		</display:column>
		
		
		<display:column>
		<input type="button" name="note" value="<spring:message code="trip.note"/>" 
				onclick="javascript:relativeRedir('note/auditor/create.do?tripId=' + ${row.id});"/>
	
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
	<spring:message  code="trip.stages" var="stagesHeader" />
		<display:column title="${stagesHeader}">
		<spring:url
				value="/stage/manager/list.do"
				var="editURL">
				<spring:param name="tripId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${editURL}"> <spring:message code="trip.stages" /></a>
		</display:column> 
	</security:authorize>
	
	
	<security:authorize access="hasRole('MANAGER')" >		
	<spring:message code="trip.survivalClasses" var="sc" />
		<display:column  title="${sc}" sortable ="false">
		<input type="button" name="survivalClasses" value="<spring:message code="template.survivalClasses"/>"
		onclick="javascript: window.location.href = './survivalClass/manager/details.do?tripId=${row.id}';"/>
		</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('EXPLORER')" >	
	
	<display:column>
	
		<jstl:choose>
		<jstl:when test="${tripsExplorer.contains(row)}">
		
		</jstl:when>
		<jstl:otherwise>
		<input type="button" name="apply" value="<spring:message code="trip.apply"/>" 
				onclick="javascript:relativeRedir('application/explorer/apply.do?tripId='+ ${row.id});"/>
		</jstl:otherwise>
		</jstl:choose>
	
	</display:column>
	</security:authorize>
	

	<security:authorize access="isAnonymous()" >		
		<spring:message code="trip.audits" var="auditsHeader"/>	
		<display:column  title="${auditsHeader}" sortable="false">
			<spring:url value="/audit/all/list.do" var="editURL">
			<spring:param name="tripId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message code="trip.audits"/></a><br/>
		</display:column>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
	<display:column>
				<spring:url
				value="/nulp/actor/list.do"
				var="editURL">
				<spring:param name="tripId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${editURL}"> <spring:message code="trip.nulp" /></a>
	
	</display:column>
	</security:authorize>
	



</display:table>
<security:authorize access="hasRole('MANAGER')">
	<input type="button" name="create" value="<spring:message code="trip.create"/>" 
		onclick="javascript:relativeRedir('trip/manager/create.do');"/>
</security:authorize>

</body>
</html>