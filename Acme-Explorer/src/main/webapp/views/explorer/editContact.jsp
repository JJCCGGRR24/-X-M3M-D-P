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


<form:form action="${requestURI}" modelAttribute="contact">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="explorer" />
		



	<form:label path="name">
		<spring:message code="contact.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	
	<form:label path="email">
		<spring:message code="contact.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phone">
		<spring:message code="contact.phone" />:
	</form:label>
	<form:input path="phone" id="tlf"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	
	
	

	<input type="submit" name="save" 
		value="<spring:message code="template.save" />" onclick=" javascript: return isValid();"/>&nbsp; 
	
	<jstl:if test="${contact.id != 0}">	
	<input type="submit" name="delete" 
	onclick="javascript: relativeRedir('contact/explorer/edit.do');"
		value="<spring:message code="template.delete" />" />&nbsp; 
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="template.cancel" />"
		onclick="javascript: relativeRedir('contact/explorer/myList.do');" />
	<br />
</form:form>


