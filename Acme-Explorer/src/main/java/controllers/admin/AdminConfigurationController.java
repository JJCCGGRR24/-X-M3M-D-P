/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/admin")
public class AdminConfigurationController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public AdminConfigurationController() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// Cache time
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editCache() {
		ModelAndView result;
		Configuration config;
		config = this.configurationService.find();
		Assert.notNull(config);
		result = this.createEditModelAndViewCache(config);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCache(@Valid final Configuration c, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndViewCache(c);
		else
			try {
				this.configurationService.save(c);
				result = new ModelAndView("redirect:../../language/en.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCache(c, "general.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewCache(final Configuration c) {
		ModelAndView result;

		result = this.createEditModelAndViewCache(c, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewCache(final Configuration config, final String message) {
		ModelAndView result;

		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", config);
		result.addObject("message", message);

		return result;
	}

}
