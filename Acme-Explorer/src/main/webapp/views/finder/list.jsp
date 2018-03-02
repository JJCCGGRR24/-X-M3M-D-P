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

<spring:message code="general.edit" var="edit" />
<spring:message code="finder.find" var="find" />


<spring:message code="finder.minimumPrice" var="minimumPrice"></spring:message>
<b><jstl:out value="${minimumPrice} : "/></b> <jstl:out value="${f.minimumPrice }"/>
<br>
<spring:message code="finder.maximumPrice" var="maximumPrice"></spring:message>
<b><jstl:out value="${maximumPrice} : "/></b>  <jstl:out value="${f.maximumPrice }"/>
<br>
<spring:message code="finder.keyword" var="keyword"></spring:message>
<b><jstl:out value="${keyword} : "/></b>  <jstl:out value="${f.keyword }"/>
<br>
<spring:message code="finder.dateMin" var="dateMin"></spring:message>
<b><jstl:out value="${dateMin} : "/></b>  <jstl:out value="${f.dateMin }"/>
<br>
<spring:message code="finder.dateMax" var="dateMax"></spring:message>
<b><jstl:out value="${dateMax} : "/></b>  <jstl:out value="${f.dateMax }"/>
<br>
<br>


<input type="button" value="${edit}"
			onclick="javascript: window.location.href = './finder/explorer/edit.do';" />

			
<input type="button" value="${find}"
			onclick="javascript: window.location.href = './finder/explorer/find.do';" />
			