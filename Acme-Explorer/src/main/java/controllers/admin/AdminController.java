
package controllers.admin;

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
import services.ActorService;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("/profile/admin")
public class AdminController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private LoginService			loginService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public AdminController() {
		super();
	}

	//edit ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator admin;

		final Integer id = this.loginService.getPrincipalActor().getId();
		admin = this.adminService.findOne(id);
		Assert.notNull(admin);
		result = this.createEditModelAndView(admin);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView res = new ModelAndView("administrator/edit");
		if (binding.hasErrors())
			res = this.createEditModelAndView(admin);
		else
			try {
				this.adminService.save(admin);
				res = new ModelAndView("redirect:/profile/details.do?actorId=" + admin.getId());

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndView(admin, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(admin, "template.commit.error");
			}
		return res;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Administrator admin) {
		ModelAndView result;

		result = this.createEditModelAndView(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", admin);
		result.addObject("message", message);
		result.addObject("requestURI", "profile/admin/edit.do");

		return result;
	}

}
