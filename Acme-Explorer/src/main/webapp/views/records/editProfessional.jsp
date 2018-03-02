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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="professionalRecord/ranger/edit.do" modelAttribute="professionalRecord">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculum" />
	


	<form:label path="companyName">
		<spring:message code="professionalRecord.companyName" />:
	</form:label>
	<form:input path="companyName" />
	<form:errors cssClass="error" path="companyName" />
	<br />
	
	<form:label path="role">
		<spring:message code="professionalRecord.role" />:
	</form:label>
	<form:input path="role" />
	<form:errors cssClass="error" path="role" />
	<br />
	
	<form:label path="startDate">
		<spring:message code="educationRecord.starts" />:
	</form:label>
	<form:input path="startDate" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	
	<form:label path="endDate">
		<spring:message code="educationRecord.ends" />:
	</form:label>
	<form:input path="endDate" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="endDate" />
	<br />
	
	<form:label path="comments">
		<spring:message code="general.comments" />:
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />
	
	<form:label path="attachment">
		<spring:message code="general.attachment" />:
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment" />
	<br />

	<jstl:if test="${professionalRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="template.delete" />"
			onclick="return confirm('<spring:message code="template.confirm" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" class="btn btn-warning" name="cancel" value='<spring:message code="template.cancel"/>' onclick="document.location.href='professionalRecord/ranger/list.do?curriculumId=' + ${curriculumId} ;">
	<input type="submit" class="btn btn-success" name="save" value='<spring:message code="template.save"/>'>
	
	
</form:form>
