/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("/language")
public class LanguageController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public LanguageController() {
		super();
	}


	@Autowired
	private ConfigurationService	configurationService;


	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/en")
	public ModelAndView en() {
		final ModelAndView result;
		final Configuration c = this.configurationService.find();
		c.setWelcome(c.getWelcomeEn());
		this.configurationService.save(c);
		result = new ModelAndView("redirect:../?language=en");
		return result;
	}

	@RequestMapping(value = "/es")
	public ModelAndView es() {
		final ModelAndView result;
		final Configuration c = this.configurationService.find();
		c.setWelcome(c.getWelcomeEs());
		this.configurationService.save(c);
		result = new ModelAndView("redirect:../?language=es");
		return result;
	}
}
