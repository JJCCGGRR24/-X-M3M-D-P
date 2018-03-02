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


<form:form action="curricula/ranger/edit.do" modelAttribute="curricula">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	
	<form:hidden path="endorserRecords" />
	<form:hidden path="miscellaneousRecords" />
	<form:hidden path="educationRecords" />
	<form:hidden path="professionalRecords" />
	
	<form:hidden path="ranger" />
	


	<form:label path="personalRecord.fullName">
		<spring:message code="personalRecord.fullName" />:
	</form:label>
	<form:input path="personalRecord.fullName" />
	<form:errors cssClass="error" path="personalRecord.fullName" />
	<br />
	
	<form:label path="personalRecord.email">
		<spring:message code="personalRecord.email" />:
	</form:label>
	<form:input path="personalRecord.email" />
	<form:errors cssClass="error" path="personalRecord.email" />
	<br />
	
	<form:label path="personalRecord.photo">
		<spring:message code="personalRecord.picture" />:
	</form:label>
	<form:input path="personalRecord.photo" />
	<form:errors cssClass="error" path="personalRecord.photo" />
	<br />
	
	<form:label path="personalRecord.linkedProfile">
		<spring:message code="personalRecord.linkedProfile" />:
	</form:label>
	<form:input path="personalRecord.linkedProfile" />
	<form:errors cssClass="error" path="personalRecord.linkedProfile" />
	<br />
	
	<form:label path="personalRecord.phone">
		<spring:message code="personalRecord.phone" />
	</form:label>	
	<form:input path="personalRecord.phone" id="tlf" />	
	<form:errors path="personalRecord.phone" cssClass="error" />
	<br />
	
	



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
	
	<input type="button" class="btn btn-warning" name="cancel" value='<spring:message code="template.cancel"/>' onclick="document.location.href='curricula/ranger/myCurricula.do';">
	<input type="submit" class="btn btn-success" name="save" value='<spring:message code="template.save"/>' onclick=" javascript: return isValid();">
	
	
</form:form>
