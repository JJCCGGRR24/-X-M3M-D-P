/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorsController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ErrorsController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/noCurricula")
	public ModelAndView noCurricula() {
		ModelAndView result;

		result = new ModelAndView("error/noCurricula");

		return result;
	}

}
