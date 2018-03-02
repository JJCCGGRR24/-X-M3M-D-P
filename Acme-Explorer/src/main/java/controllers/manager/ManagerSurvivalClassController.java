
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ManagerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.SurvivalClass;

@Controller
@RequestMapping("/survivalClass/manager")
public class ManagerSurvivalClassController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private LoginService			loginService;


	// Constructors -----------------------------------------------------------
	public ManagerSurvivalClassController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView res = new ModelAndView("survivalClass/create");
		final SurvivalClass sc = this.survivalClassService.create();
		res.addObject("survivalClass", sc);
		res = this.createEditModelAndView(sc);
		return res;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam final int survivalClassId) {
		ModelAndView res = new ModelAndView("survivalClass/edit");
		final SurvivalClass sc = this.survivalClassService.findOne(survivalClassId);
		res.addObject("survivalClass", sc);
		res = this.createEditModelAndView(sc);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMiscellaneous(@Valid final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClass);
		else
			try {
				this.survivalClassService.save(survivalClass);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(survivalClass, "general.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteMiscellaneous(final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;

		try {
			this.survivalClassService.delete(survivalClass);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(survivalClass, "general.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final SurvivalClass sc) {
		ModelAndView result;

		result = this.createEditModelAndView(sc, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SurvivalClass sc, final String message) {
		ModelAndView result;

		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalclass", sc);
		result.addObject("message", message);
		final Manager m = (Manager) this.loginService.getPrincipalActor();
		result.addObject("trips", this.managerService.getTripsByManager(m));

		return result;
	}
	//list ----------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("survivalClass/list");
		final Manager e = (Manager) this.loginService.getPrincipalActor();
		res.addObject("survivalClasses", this.managerService.getSurvivalClasses(e));
		res.addObject("requestURI", "survivalClass/manager/list.do");
		//res.addObject("dateNow", new Date());
		return res;
	}

	//details ----------------------------------------------------------------

	@RequestMapping("/details")
	public ModelAndView listSurvClass(@RequestParam final int tripId) {
		final ModelAndView res = new ModelAndView("survivalClass/list");

		res.addObject("survivalClasses", this.survivalClassService.getSurvivalClassByTrips(tripId));
		res.addObject("requestURI", "survivalClass/manager/details.do");

		return res;
	}
}
