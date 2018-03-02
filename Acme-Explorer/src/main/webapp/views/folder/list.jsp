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


<display:table name="folders" id="row" requestURI="folder/actor/list.do"
	pagesize="5" class="displaytag">


	<!-- Action links -->



	<!-- Attributes -->
	
	
	<spring:message code="folder.name" var="name"></spring:message>
	<display:column property="name" title="${name}"></display:column>
	
	<security:authorize access="isAuthenticated()">
	<spring:message code="folder.edit" var="edit"/>
	<display:column title="${edit}">
		<jstl:if test="${row.modifiable}">
			<a href="folder/actor/edit.do?folderId=${row.id}"> 
				<spring:message code="folder.edit" />
			</a>	
		</jstl:if>
	</display:column>
	
	<spring:message code="folder.messages" var="messages"/>
	<display:column title="${messages}">
		<a href="message/actor/list.do?folderId=${row.id}"> 
			<spring:message code="folder.messages" /><jstl:out value=" (${row.mesages.size()})"></jstl:out>
		</a>	
	</display:column>
	</security:authorize>


</display:table>


<security:authorize access="isAuthenticated()">
	<div>
		<a href="folder/actor/create.do"> <spring:message
				code="folder.create" />
		</a><br/>
		<a href="message/actor/create.do"> <spring:message
				code="folder.message" />
		</a>
	</div>
</security:authorize>
