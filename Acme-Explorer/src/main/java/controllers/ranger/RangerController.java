
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.Ranger;

@Controller
@RequestMapping("/profile/ranger")
public class RangerController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private RangerService	rangerService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public RangerController() {
		super();
	}

	//edit ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Ranger ranger;
		final Integer id = this.loginService.getPrincipalActor().getId();
		ranger = this.rangerService.findOne(id);
		Assert.notNull(ranger);
		result = this.createEditModelAndView(ranger);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Ranger ranger, final BindingResult binding) {
		ModelAndView res = new ModelAndView("ranger/edit");
//		if (ranger.getCurricula() == null)
//			ranger.setCurricula(new Curricula());
		if (binding.hasErrors() && !(binding.getAllErrors().size()==1 && binding.getAllErrors().get(0).getDefaultMessage().contains("curricula")))
			res = this.createEditModelAndView(ranger);
		else
			try {
				this.rangerService.save(ranger);
				res = new ModelAndView("redirect:/profile/details.do?actorId=" + ranger.getId());
			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndView(ranger, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(ranger, "template.commit.error");
			}
		return res;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Ranger ranger) {
		ModelAndView result;

		result = this.createEditModelAndView(ranger, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Ranger ranger, final String message) {
		ModelAndView result;

//		if (ranger.getCurricula() == null)
//			ranger.setCurricula(new Curricula());
		result = new ModelAndView("edit/ranger");
		result.addObject("ranger", ranger);
		result.addObject("message", message);
		result.addObject("requestURI", "profile/ranger/edit.do");

		return result;
	}

}
