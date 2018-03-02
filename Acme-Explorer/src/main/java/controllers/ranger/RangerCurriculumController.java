/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.ranger;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculaService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Ranger;

@Controller
@RequestMapping("/curricula/ranger")
public class RangerCurriculumController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculaService	curriculumService;
	@Autowired
	private LoginService		loginService;


	// Constructors -----------------------------------------------------------

	public RangerCurriculumController() {
		super();
	}

	// My curriculum
	@RequestMapping(value = "/myCurricula")
	public ModelAndView myList() {

		ModelAndView result = null;
		final Ranger logueado = (Ranger) this.loginService.getPrincipalActor();
		final Curricula c = logueado.getCurricula();
		final List<Curricula> curricula = new ArrayList<>();
		curricula.add(c);
		result = new ModelAndView("curriculum/myCurricula");
		result.addObject("curricula", c);
		try {
			if (this.loginService.getPrincipalActor().equals(logueado))
				result.addObject("owner", true);
			result.addObject("ranger", logueado);
		} catch (final Exception e) {
		}

		return result;
	}

	//Create PersonalRecord ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Curricula c;

		c = this.curriculumService.create();

		result = this.createEditModelAndView(c);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumId) {
		ModelAndView result;
		Curricula cu;

		cu = this.curriculumService.findOne(curriculumId);
		Assert.isTrue(this.loginService.getPrincipalActor().equals(cu.getRanger()));
		Assert.notNull(cu);
		result = this.createEditModelAndView(cu);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Curricula c, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(c);
		else
			try {
				c = this.curriculumService.save(c);
				result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + c.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(c, "general.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int curriculumId) {
		ModelAndView result;
		final Curricula c = this.curriculumService.findOne(curriculumId);
		final Ranger r = c.getRanger();
		try {

			this.curriculumService.delete(c);
			result = new ModelAndView("redirect:/curricula/get.do?rangerId=" + r.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(c, "general.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Curricula c) {
		ModelAndView result;

		result = this.createEditModelAndView(c, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Curricula c, final String message) {
		ModelAndView result;

		result = new ModelAndView("curriculum/edit");
		result.addObject("curricula", c);
		result.addObject("message", message);

		return result;
	}

}
