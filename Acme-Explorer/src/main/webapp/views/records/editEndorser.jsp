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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<form:form action="endorserRecord/ranger/edit.do" modelAttribute="endorserRecord">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculum" />
	
	
	<form:label path="name">
		<spring:message code="personalRecord.fullName" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="email">
		<spring:message code="personalRecord.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phone">
		<spring:message code="personalRecord.phone" />
	</form:label>	
	<form:input path="phone" id="tlf" />	
	<form:errors path="phone" cssClass="error" />
	<br />
	
	<form:label path="linkedProfile">
		<spring:message code="personalRecord.linkedProfile" />:
	</form:label>
	<form:input path="linkedProfile" />
	<form:errors cssClass="error" path="linkedProfile" />
	<br />
	
	<form:label path="comments">
		<spring:message code="general.comments" />:
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />


	

	<jstl:if test="${endorserRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="template.delete" />"
			onclick="return confirm('<spring:message code="template.confirm" />')" />&nbsp;
	</jstl:if>
	
	
	
		<script type="text/javascript">
  
  function isValid() {
        var phoneRe = /^(((\+[1-9][0-9]{0,2}) \(([1-9][0-9]{0,2})\) (\d\d\d\d+))|((\+[1-9][0-9]{0,2}) (\d\d\d\d+))|((\d\d\d\d+)))$/ ;
        var digits = document.getElementById('tlf').value;
        var res = phoneRe.test(digits);
        if (res){
          return true;
        }else{
          return confirm('<spring:message code="phone.confirm" />');
        }
    }
  
  </script>
	
	<input type="button" class="btn btn-warning" name="cancel" value='<spring:message code="template.cancel"/>' onclick="document.location.href='professionalRecord/ranger/list.do?curriculumId=' + ${curriculumId};">
	<input type="submit" class="btn btn-success" name="save" value='<spring:message code="template.save"/>' onclick=" javascript: return isValid();" >
	
	
</form:form>
