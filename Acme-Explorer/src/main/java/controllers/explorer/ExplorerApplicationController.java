
package controllers.explorer;

import java.util.Date;

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
import services.ApplicationService;
import services.CreditCardService;
import services.SponsorshipService;
import services.TripService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Trip;

@Controller
@RequestMapping("/application/explorer")
public class ExplorerApplicationController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private LoginService		loginService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	// Constructors -----------------------------------------------------------
	public ExplorerApplicationController() {
		super();
	}

	//list ----------------------------------------------------------------

	@SuppressWarnings("deprecation")
	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("application/list");
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		res.addObject("applications", e.getApplications());
		res.addObject("requestURI", "application/explorer/list.do");
		final Date d = new Date();
		res.addObject("dateNow", d);
		final Date dateMonthAfter = new Date();
		dateMonthAfter.setMonth(dateMonthAfter.getMonth() + 1);
		res.addObject("dateMonthAfter", dateMonthAfter);
		return res;
	}

	//Create ----------------------------------------------------------------

	@RequestMapping("/apply")
	public ModelAndView apply(@RequestParam final int tripId) {

		ModelAndView result;
		final Trip t = this.tripService.findOne(tripId);
		final Application application = this.applicationService.create(t);

		Assert.notNull(application);
		Assert.notNull(t);
		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/apply", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "template.commit.error");
			}

		return result;
	}

	//Credit card ----------------------------------------------------------------

	@RequestMapping("/addCreditCard")
	public ModelAndView addCreditCard(@RequestParam final int applicationId) {

		ModelAndView result;

		final Application a = this.applicationService.findOne(applicationId);

		final CreditCard creditCard = new CreditCard();
		creditCard.setApplication(a);
		//final List<Sponsorship> ls = (List<Sponsorship>) this.sponsorshipService.findAll();
		//creditCard.setSponsorship(ls.get(0));
		result = this.createEditModelAndViewCreditCard(creditCard);

		return result;
	}

	@RequestMapping(value = "/addCreditCard", method = RequestMethod.POST, params = "save")
	public ModelAndView addCreditCard(@Valid final CreditCard cc, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewCreditCard(cc);
		else
			try {
				this.creditCardService.save(cc);
				this.applicationService.acceptApplicationExplorer(cc.getApplication().getTrip());
				this.applicationService.NotifyExplorer(cc.getApplication());
				this.applicationService.NotifyManager(cc.getApplication());
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCreditCard(cc, "template.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndViewCreditCard(final CreditCard a) {
		ModelAndView result;

		result = this.createEditModelAndViewCreditCard(a, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewCreditCard(final CreditCard a, final String message) {
		ModelAndView result;

		result = new ModelAndView("application/CreditCard");
		result.addObject("creditCard", a);
		result.addObject("message", message);
		result.addObject("requestURI", "application/explorer/addCreditCard.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Application a) {
		ModelAndView result;

		result = this.createEditModelAndView(a, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application a, final String message) {
		ModelAndView result;

		result = new ModelAndView("application/apply");
		result.addObject("application", a);
		result.addObject("message", message);
		result.addObject("requestURI", "application/explorer/apply.do");

		return result;
	}

	//Cancell ----------------------------------------------------------------

	@RequestMapping("/cancell")
	public ModelAndView cancell(@RequestParam final int applicationId) {
		ModelAndView res = new ModelAndView("application/list");

		final Application a = this.applicationService.findOne(applicationId);
		this.applicationService.cancellApplicationExplorer(a.getTrip());

		this.applicationService.NotifyExplorer(a);
		this.applicationService.NotifyManager(a);
		res = new ModelAndView("redirect:list.do");
		return res;
	}
}
