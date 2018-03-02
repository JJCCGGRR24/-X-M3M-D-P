
package controllers.sponsor;

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
import services.SponsorService;
import controllers.AbstractController;
import domain.Sponsor;

@Controller
@RequestMapping("/profile/sponsor")
public class SponsorController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private SponsorService	sponsorService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public SponsorController() {
		super();
	}

	//edit ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Sponsor sponsor;

		final Integer id = this.loginService.getPrincipalActor().getId();
		sponsor = this.sponsorService.findOne(id);
		Assert.notNull(sponsor);
		result = this.createEditModelAndView(sponsor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView res = new ModelAndView("sponsor/edit");
		if (binding.hasErrors())
			res = this.createEditModelAndView(sponsor);
		else
			try {
				this.sponsorService.save(sponsor);
				res = new ModelAndView("redirect:/profile/details.do?actorId=" + sponsor.getId());

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndView(sponsor, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(sponsor, "template.commit.error");
			}
		return res;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String message) {
		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("message", message);

		return result;
	}

}
