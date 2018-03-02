
package controllers.manager;

import java.util.Date;
import java.util.List;

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
import services.CategoryService;
import services.LegalTextService;
import services.RangerService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.Trip;

@Controller
@RequestMapping("/trip/manager")
public class ManagerTripController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private TripService			tripService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private LoginService		loginService;


	// Constructors -----------------------------------------------------------
	public ManagerTripController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer categoryId, @RequestParam(required = false) final String search) {
		final ModelAndView result;
		final List<Trip> trips;
		if (categoryId != null)
			trips = (this.categoryService.findOne(categoryId).getTrips());
		else if (search != null)
			trips = (List<Trip>) this.tripService.search(search);
		else
			trips = ((Manager) this.loginService.getPrincipalActor()).getTrips();
		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		final Date fecha = new Date();
		result.addObject("fechaActual", fecha);
		result.addObject("vistaMyTrips", true);
		result.addObject("requestURI", "trip/manager/list.do");
		result.addObject("banners", this.tripService.randomBannerList(trips));

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Trip trip = this.tripService.create();
		result = this.createEditModelAndView(trip);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {

		final ModelAndView result;

		final Trip trip = this.tripService.findOne(tripId);
		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		Assert.isTrue(trip.getManager() == actual);
		Assert.isTrue(trip.getPublicationDate() == null && trip.getCancelledReason() == null);
		Assert.notNull(trip);
		result = this.createEditModelAndView(trip);
		return result;
	}
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public ModelAndView publish(@Valid final int tripId) {

		ModelAndView result;
		final Trip trip = this.tripService.findOne(tripId);
		final Manager actual = (Manager) this.loginService.getPrincipalActor();

		Assert.isTrue(trip.getManager() == actual);
		Assert.isTrue(trip.getCancelledReason() == null);
		Assert.isTrue(trip.getPublicationDate() == null);

		try {
			this.tripService.publish(trip);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(trip, "trip.commit.error");

		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Trip trip, final BindingResult binding) {

		ModelAndView result;
		final String s = this.tripService.validate(trip);

		if (binding.hasErrors() || s != null)
			result = this.createEditModelAndView(trip, s);
		else
			try {
				this.tripService.save(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int tripId) {

		final ModelAndView result;

		final Trip trip = this.tripService.findOne(tripId);
		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		Assert.isTrue(trip.getManager() == actual);
		Assert.isTrue(trip.getPublicationDate() != null && trip.getCancelledReason() == null);
		Assert.notNull(trip);
		result = this.createEditModelAndViewCancel(trip);
		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "save")
	public ModelAndView cancel(@Valid final Trip trip, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewCancel(trip);
		else
			try {
				this.tripService.save(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCancel(trip, "trip.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Trip trip, final BindingResult binding) {

		ModelAndView result;

		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		Assert.isTrue(trip.getManager() == actual);
		Assert.isTrue(trip.getPublicationDate() == null);

		try {
			this.tripService.delete(trip);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(trip, "trip.commit.error");

		}
		return result;
	}
	//Auxiliars-------------------------
	protected ModelAndView createEditModelAndView(final Trip trip) {
		ModelAndView result;
		result = this.createEditModelAndView(trip, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Trip trip, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("trip/edit");
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("legalTexts", this.legalTextService.getLegalTextsFinalMode());
		result.addObject("rangers", this.rangerService.findAll());
		result.addObject("trip", trip);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "trip/manager/edit.do");

		return result;
	}

	protected ModelAndView createEditModelAndViewCancel(final Trip trip) {
		ModelAndView result;
		result = this.createEditModelAndViewCancel(trip, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewCancel(final Trip trip, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("trip/cancel");
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("legalTexts", this.legalTextService.getLegalTextsFinalMode());
		result.addObject("rangers", this.rangerService.findAll());
		result.addObject("trip", trip);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "trip/manager/cancel.do");

		return result;
	}
}
