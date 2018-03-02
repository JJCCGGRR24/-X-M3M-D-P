
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ManagerService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Manager;
import domain.Ranger;

@Controller
@RequestMapping("/registry/admin")
public class AdminRegistryController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private RangerService	rangerService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------
	public AdminRegistryController() {
		super();
	}

	//Registy ranger ----------------------------------------------------------------

	@RequestMapping(value = "/ranger", method = RequestMethod.GET)
	public ModelAndView asRanger() {
		ModelAndView res = new ModelAndView("register/ranger");
		final Ranger ranger = this.rangerService.create();
		res = this.createEditModelAndViewRanger(ranger);
		return res;
	}

	@RequestMapping(value = "/ranger", method = RequestMethod.POST, params = "save")
	public ModelAndView asRanger(@Valid final Ranger ranger, final BindingResult bindingResult) {

		ModelAndView res = new ModelAndView("register/ranger");
		ranger.setCurricula(null);
		if (bindingResult.hasErrors())
			res = this.createEditModelAndViewRanger(ranger);
		else if (this.actorService.exist(ranger.getUserAccount().getUsername()))
			res = this.createEditModelAndViewRanger(ranger, "username.exists");
		else
			try {
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				ranger.getUserAccount().setPassword(encoder.encodePassword((ranger.getUserAccount().getPassword()), null));
				this.rangerService.save(ranger);
				res = new ModelAndView("welcome/index");
				res.addObject("message", "register.admin.correct");

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndViewRanger(ranger, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndViewRanger(ranger, "template.commit.error");
			}
		return res;
	}
	protected ModelAndView createEditModelAndViewRanger(final Ranger r) {
		ModelAndView result;

		result = this.createEditModelAndViewRanger(r, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewRanger(final Ranger r, final String message) {
		ModelAndView result;
		r.setCurricula(new Curricula());
		result = new ModelAndView("register/ranger");
		result.addObject("ranger", r);
		result.addObject("message", message);
		result.addObject("requestURI", "registry/admin/ranger.do");

		return result;
	}

	//Register manager ----------------------------------------------------------------

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public ModelAndView manager() {
		ModelAndView res = new ModelAndView("register/manager");
		final Manager manager = this.managerService.create();
		res = this.createEditModelAndViewManager(manager);
		return res;
	}

	@RequestMapping(value = "/manager", method = RequestMethod.POST, params = "save")
	public ModelAndView manager(@Valid final Manager manager, final BindingResult bindingResult) {

		ModelAndView res = new ModelAndView("register/manager");
		if (bindingResult.hasErrors())
			res = this.createEditModelAndViewManager(manager);
		else if (this.actorService.exist(manager.getUserAccount().getUsername()))
			res = this.createEditModelAndViewManager(manager, "username.exists");
		else
			try {
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				manager.getUserAccount().setPassword(encoder.encodePassword((manager.getUserAccount().getPassword()), null));
				this.managerService.save(manager);
				res = new ModelAndView("welcome/index");
				res.addObject("message", "register.admin.correct");

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndViewManager(manager, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndViewManager(manager, "template.commit.error");
			}
		return res;
	}

	protected ModelAndView createEditModelAndViewManager(final Manager r) {
		ModelAndView result;

		result = this.createEditModelAndViewManager(r, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewManager(final Manager r, final String message) {
		ModelAndView result;
		result = new ModelAndView("register/manager");
		result.addObject("manager", r);
		result.addObject("message", message);
		result.addObject("requestURI", "registry/admin/ranger.do");

		return result;
	}

}
