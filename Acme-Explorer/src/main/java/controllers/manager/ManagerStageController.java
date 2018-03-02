
package controllers.manager;

import java.util.Collection;

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
import services.StageService;
import services.TripService;
import domain.Manager;
import domain.Stage;
import domain.Trip;

@Controller
@RequestMapping("/stage/manager")
public class ManagerStageController {

	//Services ---------------------------------------------------------------

	@Autowired
	private StageService	stageService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public ManagerStageController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int tripId) {
		final ModelAndView result;

		final Collection<Stage> stages = this.stageService.stagesByTrip(tripId);
		//		Assert.notNull(socialIdentities);
		final Trip trip = this.tripService.findOne(tripId);
		result = new ModelAndView("stage/list");
		result.addObject("stages", stages);
		result.addObject("trip", trip);
		result.addObject("requestURI", "stage/manager/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		final ModelAndView result;

		final Trip trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		final Stage socialId = this.stageService.create(trip);
		result = this.createEditModelAndView(socialId);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int stageId) {

		final ModelAndView result;

		final Stage stage = this.stageService.findOne(stageId);
		Assert.notNull(stage);
		result = this.createEditModelAndView(stage);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Stage stage, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(stage);
		else
			try {
				this.stageService.save(stage);
				this.tripService.save(stage.getTrip());
				result = new ModelAndView("redirect:list.do?tripId=" + stage.getTrip().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(stage, "stage.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Stage stage, final BindingResult binding) {

		ModelAndView result;
		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		Assert.isTrue(stage.getTrip().getManager() == actual);

		try {
			this.stageService.delete(stage);
			result = new ModelAndView("redirect:list.do?tripId=" + stage.getTrip().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(stage, "socialIdentity.commit.error");

		}
		return result;
	}

	//Auxiliars-------------------------
	protected ModelAndView createEditModelAndView(final Stage stage) {
		ModelAndView result;

		result = this.createEditModelAndView(stage, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Stage stage, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("stage/edit");
		result.addObject("stage", stage);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "stage/manager/edit.do");

		return result;
	}

}
