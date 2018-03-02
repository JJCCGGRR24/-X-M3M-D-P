
package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorSponsorshipController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private LoginService		loginService;


	// Constructors -----------------------------------------------------------
	public SponsorSponsorshipController() {
		super();
	}

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("sponsorship/list");
		res.addObject("sponsorships", ((Sponsor) this.loginService.getPrincipalActor()).getSponsorships());
		return res;
	}

	// Create ---------------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create() {
		final ModelAndView res = this.createEditModelAndView(this.sponsorshipService.create());
		return res;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		final ModelAndView res = this.createEditModelAndView(this.sponsorshipService.findOne(sponsorshipId));
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult bindingResult) {
		ModelAndView res;
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res = this.createEditModelAndView(sponsorship);
		} else
			try {
				this.sponsorshipService.save(sponsorship);
				res = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				e.printStackTrace();
				res = this.createEditModelAndView(sponsorship, "commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Sponsorship sponsorship, final BindingResult bindingResult) {
		ModelAndView res;
		try {
			this.sponsorshipService.delete(sponsorship);
			res = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			e.printStackTrace();
			res = this.createEditModelAndView(sponsorship, "commit.error");
		}
		return res;
	}
	// Ancient methods ---------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;
		result = this.createEditModelAndView(sponsorship, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView result;
		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("message", message);
		return result;
	}
}
