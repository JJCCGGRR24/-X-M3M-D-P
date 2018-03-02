
package controllers.explorer;

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
import services.ExplorerService;
import controllers.AbstractController;
import domain.Explorer;

@Controller
@RequestMapping("/profile/explorer")
public class ExplorerController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public ExplorerController() {
		super();
	}

	//edit ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Explorer exp;

		final Integer id = this.loginService.getPrincipalActor().getId();
		exp = this.explorerService.findOne(id);
		Assert.notNull(exp);
		result = this.createEditModelAndView(exp);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Explorer exp, final BindingResult binding) {
		ModelAndView res = new ModelAndView("edit/explorer");
		if (binding.hasErrors())
			res = this.createEditModelAndView(exp);
		else
			try {
				this.explorerService.save(exp);
				res = new ModelAndView("redirect:/profile/details.do?actorId=" + exp.getId());

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndView(exp, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(exp, "template.commit.error");
			}
		return res;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Explorer explorer) {
		ModelAndView result;

		result = this.createEditModelAndView(explorer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Explorer explorer, final String message) {
		ModelAndView result;

		result = new ModelAndView("edit/explorer");
		result.addObject("explorer", explorer);
		result.addObject("message", message);
		result.addObject("requestURI", "profile/explorer/edit.do");

		return result;
	}

}
