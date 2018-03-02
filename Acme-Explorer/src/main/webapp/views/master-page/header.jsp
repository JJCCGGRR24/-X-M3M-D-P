<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div align="center">
			<img src="http://creek-tours.com/wp-content/uploads/Kenya-Tanzania-Family-Safari-banner.jpg" height="38%" width="38%" alt="Explorer Co., Inc." />
		


<br>
<security:authorize access="hasRole('BANNED')">
<div align="center" style="color: red"><h1>  <spring:message code="master.page.banned" />  </h1></div>
</security:authorize>
<div>
	<ul id="jMenu">
	<!-- ACCEDEN TODOS -->
	<security:authorize access="!(hasRole('AUDITOR') OR hasRole('EXPLORER'))">
	<li><a class="fNiv" href="trip/all/list.do"><spring:message code="master.page.trips" /> <img src="https://i.imgur.com/x17mz6p.png" height="12px"></a></li>
	</security:authorize>
	<security:authorize access="hasRole('EXPLORER')">
	<li><a class="fNiv" href="trip/explorer/list.do"><spring:message code="master.page.trips" /> <img src="https://i.imgur.com/x17mz6p.png" height="12px"></a></li>
	</security:authorize>
	<security:authorize access="hasRole('AUDITOR')">
	<li><a class="fNiv" href="trip/auditor/list.do"><spring:message code="master.page.trips" /> <img src="https://i.imgur.com/x17mz6p.png" height="12px"></a></li>
	</security:authorize>
	<li><a class="fNiv"><spring:message code="master.page.categories" /> <img src="https://i.imgur.com/4wTE64w.png" height="12px"></a>
		<ul>
			<li class="arrow"></li>	
			<li><a href="category/all/flat.do"><spring:message code="master.page.categories.list" /></a></li>
			<li><a href="category/all/list	.do"><spring:message code="master.page.categories.flat" /></a></li>
		</ul>
	</li>
	<security:authorize access="isAuthenticated()">
		<security:authorize access="!hasRole('BANNED')">
		<li><a class="fNiv" href="folder/actor/list.do"><spring:message code="master.page.messagerie"/> <img src="https://i.imgur.com/KWsTAb0.png" height="12px"></a></li>
		</security:authorize>
	</security:authorize>
			
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>	
					<li><a href="configuration/admin/edit.do"><spring:message code="configuration.edit" /></a></li>
					<li><a href="legalText/admin/list.do"><spring:message code="master.page.legalTexts" /></a></li>
					<li><a href="tag/admin/catalogue.do"><spring:message code="master.page.tags" /></a></li>
					<li><a href="broadcast/admin/create.do"><spring:message code="master.page.broadcast" /></a></li>
					<li><a href="dashboard/admin/display.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a href="suspicius/admin/list.do"><spring:message code="master.page.suspicious" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>	
					<li><a href="registry/admin/ranger.do"><spring:message code="registry.ranger" /></a></li>
					<li><a href="registry/admin/manager.do"><spring:message code="registry.manager" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv" href="note/auditor/list.do"><spring:message	code="master.page.notes" /> <img src="https://i.imgur.com/BzLMNhs.png" height="12px"></a></li>
			<li><a class="fNiv" href="audit/auditor/list.do"><spring:message	code="master.page.audits" /> <img src="https://i.imgur.com/ZJUphGq.png" height="12px"></a>
			</li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.mySponsorships" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('RANGER')">
			<li><a class="fNiv"><spring:message	code="master.page.ranger" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curricula/ranger/myCurricula.do"><spring:message code="ranger.myCurricula" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('EXPLORER')">
			<li><a class="fNiv"><spring:message	code="master.page.explorer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="contact/explorer/myList.do"><spring:message code="actor.contact" /></a></li>
					<li><a href="finder/explorer/list.do"><spring:message code="master.page.finder" /></a></li>
					<li><a href="application/explorer/list.do"><spring:message code="master.page.applications" /></a></li>
					<li><a href="survivalClass/explorer/list.do"><spring:message code="master.page.survivalClasses" /></a></li>
					<li><a href="story/explorer/list.do"><spring:message code="master.page.stories" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message	code="master.page.manager" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/manager/list.do"><spring:message code="master.page.myTrips" /> </a></li>
					<li><a href="application/manager/list.do"><spring:message code="master.page.applications" /> </a></li>
					<li><a href="note/manager/list.do"><spring:message code="master.page.notes" /></a></li>
					<li><a href="survivalClass/manager/list.do"><spring:message code="master.page.survivalClasses" /></a></li>
					<li><a href="nulp/manager/list.do"><spring:message code="master.page.nulps" /></a></li>
				
				</ul>
			</li>
<%-- 			<li><a class="fNiv" href="trip/manager/list.do"><spring:message code="master.page.myTrips" /> <img src="https://i.imgur.com/x17mz6p.png" height="12px"></a></li> --%>
			
		</security:authorize>
		
		
			
			
			<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /> <img src="https://i.imgur.com/NmAaRkH.png" height="10px"></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /> <img src="https://i.imgur.com/s0HbOzI.png" height="12px"></a>
				<ul>
					<li class="arrow"></li>	
					<li><a href="registry/asRanger.do"><spring:message code="registry.asRanger" /></a></li>
					<li><a href="registry/asExplorer.do"><spring:message code="registry.asExplorer" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				
				
				
				<ul>
					<li class="arrow"></li>
					<security:authorize access="!hasRole('BANNED')">
					<li><a href="profile/actor/myDetails.do"><spring:message code="actor.details" /></a></li>
					<li><a href="socialIdentity/actor/myList.do"><spring:message code="actor.socialIdentities" /></a></li>
					</security:authorize>
					<li class="logout" style="border-bottom: indianred;background-color: indianred;"><a href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
				</ul>
			</li>
			
		</security:authorize>
	</ul>
</div>



<div>
	<a href="language/en.do">en</a> | <a href="language/es.do">es</a>
</div>


</div>

