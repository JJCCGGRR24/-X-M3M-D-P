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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="uN"/>
</security:authorize>






<spring:message code="general.edit" var="edit" />
<spring:message code="curriculum.personalRecord" var="personalRecord"></spring:message>
<h1><jstl:out value="${personalRecord}"/> </h1>

<jstl:if test="${actor.userAccount.username eq uN }">
<input type="button" value="${edit}"
			onclick="javascript: window.location.href = './curricula/ranger/edit.do?curriculumId=${curriculum.id}';" />
			<br>
</jstl:if>
<img style="border:1px solid black; max-width:200px; max-height: 300px;" src="${curriculum.personalRecord.photo}"/> <br/>

<spring:message code="personalRecord.fullName" var="fullName"></spring:message>
<b><jstl:out value="${fullName}"/>: </b> <jstl:out value="${curriculum.personalRecord.fullName}"/><br/>

<spring:message code="personalRecord.email" var="email"></spring:message>
<b><jstl:out value="${email}"/>: </b> <jstl:out value="${curriculum.personalRecord.email}"/><br/>

<spring:message code="personalRecord.phone" var="phone"></spring:message>
<b><jstl:out value="${phone}"/>: </b> <jstl:out value="${curriculum.personalRecord.phone}"/><br/>

<spring:message code="personalRecord.linkedProfile" var="linkedProfile"></spring:message>
<b><jstl:out value="${linkedProfile}"/>: </b> <a href="${curriculum.personalRecord.linkedProfile}"><jstl:out value="${curriculum.personalRecord.linkedProfile}"/></a><br/>


<spring:message code="general.comments" var="comments"></spring:message>
<spring:message code="curriculum.endorserRecords" var="endorserRecords"></spring:message>
<h1><jstl:out value="${endorserRecords}"/> </h1>
<jstl:if test="${actor.userAccount.username eq uN }">
<input type="button" value="${edit}"
			onclick="javascript: window.location.href = './endorserRecord/ranger/list.do?curriculumId=${curriculum.id}';" />
			<br>
</jstl:if>
		<jstl:forEach items="${curriculum.endorserRecords}" var="er" varStatus="loop">
			<b><jstl:out value="${fullName}"/>: </b><jstl:out value="${er.name}"></jstl:out><br/>
			<b><jstl:out value="${email}"/>: </b><jstl:out value="${er.email}"></jstl:out><br/>
			<b><jstl:out value="${phone}"/>: </b><jstl:out value="${er.phone}"></jstl:out><br/>
			<b><jstl:out value="${linkedProfile}"/>: </b><a href="${er.linkedProfile}"><jstl:out value="${er.linkedProfile}"></jstl:out></a><br/>
			<b><jstl:out value="${comments}"/>: </b><jstl:out value="${er.comments}"></jstl:out><br/>
			<hr>
		</jstl:forEach>
		
<spring:message code="general.attachment" var="attachment"></spring:message>
<spring:message code="general.title" var="title"></spring:message>
<spring:message code="curriculum.miscellaneousRecords" var="miscellaneousRecords"></spring:message>
<h1><jstl:out value="${miscellaneousRecords}"/> </h1>
<jstl:if test="${actor.userAccount.username eq uN }">
<input type="button" value="${edit}"
			onclick="javascript: window.location.href = './miscellaneousRecord/ranger/list.do?curriculumId=${curriculum.id}';" />
			<br>
</jstl:if>
		<jstl:forEach items="${curriculum.miscellaneousRecords}" var="mr" varStatus="loop">
			<b><jstl:out value="${title}"/>: </b><jstl:out value="${mr.title}"></jstl:out><br/>
			<b><jstl:out value="${comments}"/>: </b><jstl:out value="${mr.comments}"></jstl:out><br/>
			<b><jstl:out value="${attachment}"/>: </b><a href="${mr.attachment}"><jstl:out value="${mr.attachment}"></jstl:out></a><br/>
			<hr>
		</jstl:forEach>


<spring:message code="educationRecord.starts" var="starts"></spring:message>
<spring:message code="educationRecord.ends" var="ends"></spring:message>
<spring:message code="educationRecord.institution" var="institution"></spring:message>
<spring:message code="curriculum.educationRecords" var="educationRecords"></spring:message>
<h1><jstl:out value="${educationRecords}"/> </h1>
<jstl:if test="${actor.userAccount.username eq uN }">
<input type="button" value="${edit}"
			onclick="javascript: window.location.href = './educationRecord/ranger/list.do?curriculumId=${curriculum.id}';" />
			<br>
</jstl:if>

		<jstl:forEach items="${curriculum.educationRecords}" var="er" varStatus="loop">
			<b><jstl:out value="${title}"/>: </b><jstl:out value="${er.title}"></jstl:out><br/>
			<b><jstl:out value="${starts}"/>: </b><jstl:out value="${er.startDate}"></jstl:out><br/>
			<b><jstl:out value="${ends}"/>: </b><jstl:out value="${er.endDate}"></jstl:out><br/>
			<b><jstl:out value="${institution}"/>: </b><jstl:out value="${er.institution}"></jstl:out><br/>
			<b><jstl:out value="${comments}"/>: </b><jstl:out value="${er.comments}"></jstl:out><br/>
			<b><jstl:out value="${attachment}"/>:  </b><a href="${er.attachment}"><jstl:out value="${er.attachment}"></jstl:out></a><br/>
			<hr>
		</jstl:forEach>

<spring:message code="professionalRecord.companyName" var="companyName"></spring:message>
<spring:message code="professionalRecord.role" var="role"></spring:message>
<spring:message code="curriculum.professionalRecords" var="professionalRecords"></spring:message>
<h1><jstl:out value="${professionalRecords}"/> </h1>
<jstl:if test="${actor.userAccount.username eq uN }">
<input type="button" value="${edit}"
			onclick="javascript: window.location.href = './professionalRecord/ranger/list.do?curriculumId=${curriculum.id}';" />
			<br>
</jstl:if>
		<jstl:forEach items="${curriculum.professionalRecords}" var="pr" varStatus="loop">
			<b><jstl:out value="${companyName}"/>: </b><jstl:out value="${pr.companyName}"></jstl:out><br/>
			<b><jstl:out value="${starts}"/>: </b><jstl:out value="${pr.startDate}"></jstl:out><br/>
			<b><jstl:out value="${ends}"/>: </b><jstl:out value="${pr.endDate}"></jstl:out><br/>
			<b><jstl:out value="${role}"/>: </b><jstl:out value="${pr.role}"></jstl:out><br/>
			<b><jstl:out value="${comments}"/>: </b><jstl:out value="${pr.comments}"></jstl:out><br/>
			<b><jstl:out value="${attachment}"/>:  </b><a href="${pr.attachment}"><jstl:out value="${pr.attachment}"></jstl:out></a><br/>
			<hr>
		</jstl:forEach>




<%-- <input type="button" name="back"
		value="<spring:message code="general.back" />"
		onclick="document.location.href='candidate/curriculum/myList.do';"/> --%>
