<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>











<display:table sort="list" name="actor" id="row" requestURI="${requestURI}"
		pagesize="1" class="displaytag">


		<spring:message code="actor.name" var="nameHeader" />
		<display:column property="name" title="${nameHeader}"/>

		<spring:message code="actor.surname" var="surnameHeader" />
		<display:column property="surname" title="${surnameHeader}" />
	
		<spring:message code="actor.email" var="emailHeader" />
		<display:column property="email" title="${emailHeader}" />
		
		<spring:message code="actor.phone" var="phoneHeader" />
		<display:column property="phone" title="${phoneHeader}" />	
	
		<spring:message code="actor.address" var="addressHeader" />
		<display:column property="address" title="${addressHeader}" />
		
		<jstl:if test="${isRanger and curriculaId != 0 }">
		<display:column>
		<input type="button" name="curriculum" value="<spring:message code="actor.curriculum"/>"
		onclick="javascript: relativeRedir('curricula/details.do?curriculumId=' + ${curriculaId})"> 
		</display:column>
		</jstl:if>
		
		<jstl:if test="${isRanger and curriculaId eq 0 }">
		<display:column>
		<input type="button" name="curriculum" value="<spring:message code="actor.curriculum"/>"
		onclick="javascript: relativeRedir('error/noCurricula.do')"> 
		</display:column>
		</jstl:if>
		
		<security:authorize access="hasRole('ADMIN')"> 
		<display:column>
		<input type="button" name="edit" value="<spring:message code="template.edit"/>"
		onclick="javascript: relativeRedir('profile/admin/edit.do?actorId='+ ${row.id})"> 
		</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('EXPLORER')"> 
		<display:column>
		<input type="button" name="edit" value="<spring:message code="template.edit"/>"
		onclick="javascript: relativeRedir('profile/explorer/edit.do?actorId='+ ${row.id})"> 
		</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')"> 
		<security:authentication property="principal.username" var="username"/>
		<jstl:if test="${row.userAccount.username eq username}">
		<display:column>
		<input type="button" name="edit" value="<spring:message code="template.edit"/>"
		onclick="javascript: relativeRedir('profile/manager/edit.do?actorId='+ ${row.id})"> 
		</display:column>
		</jstl:if>
		</security:authorize>
		
		
		<security:authorize access="hasRole('RANGER')"> 
		<security:authentication property="principal.username" var="username"/>
		<jstl:if test="${row.userAccount.username eq username}">
		<display:column>
		<input type="button" name="edit" value="<spring:message code="template.edit"/>"
		onclick="javascript: relativeRedir('profile/ranger/edit.do?actorId='+ ${row.id})"> 
		</display:column>
		</jstl:if>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')"> 
		<display:column>
		<input type="button" name="edit" value="<spring:message code="template.edit"/>"
		onclick="javascript: relativeRedir('profile/sponsor/edit.do?actorId='+ ${row.id})"> 
		</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')"> 
		<display:column>
		<input type="button" name="edit" value="<spring:message code="template.edit"/>"
		onclick="javascript: relativeRedir('profile/auditor/edit.do?actorId='+ ${row.id})"> 
		</display:column>
		</security:authorize>
		
</display:table>



<security:authorize access="hasRole('ADMIN')"> 

		<input type="button" name="socialsIdentities" value="<spring:message code="actor.socialIdentities"/>"
		onclick="javascript: relativeRedir('socialIdentity/actor/list.do?actorId=' + ${row.id})"> 
		
		<jstl:if test="${isExplorer}">
		<input type="button" name="contact" value="<spring:message code="actor.contact"/>"
		onclick="javascript: relativeRedir('contact/explorer/list.do?explorerId='+ ${row.id})"> 
		</jstl:if>
		
</security:authorize>


		
		

