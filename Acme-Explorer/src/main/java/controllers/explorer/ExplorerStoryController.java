/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.explorer;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ExplorerService;
import services.StoryService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Story;

@Controller
@RequestMapping("/story/explorer")
public class ExplorerStoryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private StoryService	storyService;
	@Autowired
	private LoginService	loginService;
	@Autowired
	private ExplorerService	explorerService;


	// Constructors -----------------------------------------------------------

	public ExplorerStoryController() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// 
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/list")
	public ModelAndView list() {

		ModelAndView result = null;
		Collection<Story> records = new ArrayList<Story>();
		final Explorer explorer = (Explorer) this.loginService.getPrincipalActor();
		records = explorer.getStories();
		result = new ModelAndView("story/list");
		result.addObject("requestURI", "story/explorer/list.do");
		result.addObject("stories", records);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Story story = this.storyService.create();
		result = this.createEditModelAndView(story);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Story story, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(story);
		else
			try {
				this.storyService.save(story);
				result = new ModelAndView("redirect:/story/explorer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(story, "general.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Story record) {
		ModelAndView result;

		result = this.createEditModelAndView(record, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Story record, final String message) {
		ModelAndView result;

		result = new ModelAndView("story/edit");
		result.addObject("story", record);
		result.addObject("message", message);
		final Explorer explorer = (Explorer) this.loginService.getPrincipalActor();
		result.addObject("tripsConAppAceptadas", this.explorerService.getTripsWithApplicationAccepted(explorer));
		result.addObject("requestURI", "story/explorer/create.do");
		return result;
	}

}
