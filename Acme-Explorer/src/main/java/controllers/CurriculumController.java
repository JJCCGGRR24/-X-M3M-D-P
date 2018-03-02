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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculaService;
import services.RangerService;
import domain.Curricula;
import domain.Ranger;

@Controller
@RequestMapping("/curricula")
public class CurriculumController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculaService	curriculumService;
	@Autowired
	private LoginService		loginService;
	@Autowired
	private RangerService		rangerService;


	// Constructors -----------------------------------------------------------

	public CurriculumController() {
		super();
	}

	// My curriculum
	@RequestMapping(value = "/get")
	public ModelAndView list(@RequestParam(required = true) final int rangerId) {

		ModelAndView result = null;
		final Ranger r = this.rangerService.findOne(rangerId);
		final Curricula c = r.getCurricula();
		final List<Curricula> curricula = new ArrayList<>();
		curricula.add(c);
		result = new ModelAndView("curriculum/myCurricula");
		result.addObject("curricula", c);
		try {
			if (this.loginService.getPrincipalActor().equals(r))
				result.addObject("owner", true);
			result.addObject("ranger", r);
		} catch (final Exception e) {
		}
		return result;

	}

	//Details ----------------------------------------------------------------
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam(required = true) final Integer curriculumId) {
		ModelAndView result;
		result = new ModelAndView("curriculum/details");
		final Curricula curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);
		result.addObject("curriculum", curriculum);
		final Ranger a = curriculum.getRanger();
		result.addObject("actor", a);
		return result;
	}

}
