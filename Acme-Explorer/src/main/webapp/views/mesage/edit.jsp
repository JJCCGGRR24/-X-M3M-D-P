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


<form:form action="message/actor/edit.do" modelAttribute="mesage">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="date"/>
	<form:hidden path="sender"/>
	<form:hidden path="folder"/>

	<form:label path="recipient">
		<spring:message code="message.recipient" />:
	</form:label>
	<form:select path="recipient">
	<form:options items="${actors}" itemLabel="userAccount.username" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="recipient" />
	<br/><br/>
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br/><br/>
	
	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label><br/>
	<form:textarea path="body" cols="70" rows="10" style="resize: vertical;"/>
	<form:errors cssClass="error" path="body" />
	<br /><br/>
		
	<form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	<form:select path="priority">
		<form:options items="${priorities}"/>
	</form:select>
<%-- 	<form:radiobutton path="priority" id="LOW"/> --%>
	
<%-- 	<form:radiobutton path="priority" id="MEDIUM"/> --%>
	
<%-- 	<form:radiobutton path="priority" id="HIGH"/> --%>
	<form:errors cssClass="error" path="priority" />
	<br />
	<br/>
	
	<input type="submit" name="send"
		value="<spring:message code="message.send" />" />&nbsp;
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="document.location.href= 'folder/actor/list.do'" />
	<br />

</form:form>