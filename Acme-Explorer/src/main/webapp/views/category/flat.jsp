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

<ul>
	<li><a href="trip/all/list.do?categoryId=${category.id}">${category.name}</a>
		<ul>
			<jstl:forEach items="${category.categoriesChildren}" var="cat">
				<li><a href="trip/all/list.do?categoryId=${cat.id}">${cat.name}</a>
					<ul>
						<jstl:forEach items="${cat.categoriesChildren}" var="cat2">
							<li><a href="trip/all/list.do?categoryId=${cat2.id}">${cat2.name}</a>
								<ul>
									<jstl:forEach items="${cat2.categoriesChildren}" var="cat3">
										<li><a href="trip/all/list.do?categoryId=${cat3.id}">${cat3.name}</a>
											<ul>
												<jstl:forEach items="${cat3.categoriesChildren}" var="cat4">
													<li><a href="trip/all/list.do?categoryId=${cat4.id}">${cat4.name}</a>
														<ul>
															<jstl:forEach items="${cat4.categoriesChildren}" var="cat5">
																<li><a href="trip/all/list.do?categoryId=${cat5.id}">${cat5.name}</a>
																	<ul>
																		<jstl:forEach items="${cat5.categoriesChildren}" var="cat6">
																			<li><a href="trip/all/list.do?categoryId=${cat6.id}">${cat6.name}</a>
																				<ul>
																					<jstl:forEach items="${cat6.categoriesChildren}" var="cat7">
																						<li><a href="trip/all/list.do?categoryId=${cat7.id}">${cat7.name}</a>
																							<ul>
																								<jstl:forEach items="${cat7.categoriesChildren}" var="cat8">
																									<li><a href="trip/all/list.do?categoryId=${cat8.id}">${cat8.name}</a>
																										<ul>
																											<jstl:forEach items="${cat8.categoriesChildren}" var="cat9">
																												<li><a href="trip/all/list.do?categoryId=${cat9.id}">${cat9.name}</a>
																													<ul>
																														<jstl:forEach items="${cat9.categoriesChildren}" var="cat10">
																															<li><a href="trip/all/list.do?categoryId=${cat10.id}">${cat10.name}</a>
																																<ul>
																																	<jstl:forEach items="${cat10.categoriesChildren}" var="cat11">
																																		<li><a href="trip/all/list.do?categoryId=${cat11.id}">${cat11.name}</a>
																																			<ul>
																																				<jstl:forEach items="${cat11.categoriesChildren}" var="cat12">
																																					<li><a href="trip/all/list.do?categoryId=${cat12.id}">${cat12.name}</a>
																																						<ul>
																																							<jstl:forEach items="${cat12.categoriesChildren}" var="cat13">
																																								<li><a href="trip/all/list.do?categoryId=${cat13.id}">${cat13.name}</a>
																																									<ul>
																																										<jstl:forEach items="${cat13.categoriesChildren}" var="cat14">
																																											<li><a href="trip/all/list.do?categoryId=${cat14.id}">${cat14.name}</a>
																																												<ul>
																																													<jstl:forEach items="${cat14.categoriesChildren}" var="cat15">
																																														<li><a href="trip/all/list.do?categoryId=${cat15.id}">${cat15.name}</a>
																																															<ul>
																																																<jstl:forEach items="${cat15.categoriesChildren}" var="cat16">
																																																	<li><a href="trip/all/list.do?categoryId=${cat16.id}">${cat16.name}</a>
																																																		<ul>
																																																			<jstl:forEach items="${cat16.categoriesChildren}" var="cat17">
																																																				<li><a href="trip/all/list.do?categoryId=${cat17.id}">${cat17.name}</a>
																																																					<ul>
																																																					</ul>
																																																				</li>
																																																			</jstl:forEach>
																																																		</ul>
																																																	</li>
																																																</jstl:forEach>
																																															</ul>
																																														</li>
																																													</jstl:forEach>
																																												</ul>
																																											</li>
																																										</jstl:forEach>
																																									</ul>
																																								</li>
																																							</jstl:forEach>
																																						</ul>
																																					</li>
																																				</jstl:forEach>
																																			</ul>
																																		</li>
																																	</jstl:forEach>
																																</ul>
																															</li>
																														</jstl:forEach>
																													</ul>
																												</li>
																											</jstl:forEach>
																										</ul>
																									</li>
																								</jstl:forEach>
																							</ul>
																						</li>
																					</jstl:forEach>
																				</ul>
																			</li>
																		</jstl:forEach>
																	</ul>
																</li>
															</jstl:forEach>
														</ul>
													</li>
												</jstl:forEach>
											</ul>
										</li>
									</jstl:forEach>
								</ul>
							</li>
						</jstl:forEach>
					</ul>
				</li>
			</jstl:forEach>
		</ul>
	</li>
</ul>