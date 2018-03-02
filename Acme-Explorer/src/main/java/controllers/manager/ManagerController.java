
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/profile/manager")
public class ManagerController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private ManagerService	managerService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public ManagerController() {
		super();
	}

	//edit ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Manager manager;

		final Integer id = this.loginService.getPrincipalActor().getId();
		manager = this.managerService.findOne(id);
		Assert.notNull(manager);
		result = this.createEditModelAndView(manager);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView res = new ModelAndView("edit/manager");
		if (binding.hasErrors())
			res = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);
				res = new ModelAndView("redirect:/profile/details.do?actorId=" + manager.getId());

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndView(manager, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(manager, "template.commit.error");
			}
		return res;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Manager manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {
		ModelAndView result;

		result = new ModelAndView("edit/manager");
		result.addObject("manager", manager);
		result.addObject("message", message);
		result.addObject("requestURI", "profile/manager/edit.do");

		return result;
	}

}
