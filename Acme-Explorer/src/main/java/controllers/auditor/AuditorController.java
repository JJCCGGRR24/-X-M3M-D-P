
package controllers.auditor;

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
import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;

@Controller
@RequestMapping("/profile/auditor")
public class AuditorController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public AuditorController() {
		super();
	}

	//edit ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Auditor auditor;

		final Integer id = this.loginService.getPrincipalActor().getId();
		auditor = this.auditorService.findOne(id);
		Assert.notNull(auditor);
		result = this.createEditModelAndView(auditor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Auditor auditor, final BindingResult binding) {
		ModelAndView res = new ModelAndView("auditor/edit");
		if (binding.hasErrors())
			res = this.createEditModelAndView(auditor);
		else
			try {
				this.auditorService.save(auditor);
				res = new ModelAndView("redirect:/profile/details.do?actorId=" + auditor.getId());

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndView(auditor, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(auditor, "template.commit.error");
			}
		return res;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Auditor auditor) {
		ModelAndView result;

		result = this.createEditModelAndView(auditor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Auditor auditor, final String message) {
		ModelAndView result;

		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditor);
		result.addObject("message", message);
		result.addObject("requestURI", "profile/auditor/edit.do");

		return result;
	}

}
