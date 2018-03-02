
package controllers.manager;

import java.util.ArrayList;
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
import services.NulpService;
import services.TripService;
import domain.Manager;
import domain.Nulp;
import domain.Trip;

@Controller
@RequestMapping("/nulp/manager")
public class ManagerNulpController {

	//Services ---------------------------------------------------------------

	@Autowired
	private TripService		tripService;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private NulpService		nulpService;


	// Constructors -----------------------------------------------------------
	public ManagerNulpController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("nulp/myNulps");
		final Manager man = (Manager) this.loginService.getPrincipalActor();
		final List<Trip> trips = man.getTrips();
		final List<Nulp> nulps = new ArrayList<Nulp>();
		for (final Trip t : trips)
			nulps.addAll(t.getNulps());

		res.addObject("nulps", nulps);
		res.addObject("requestURI", "nulp/manager/list.do");

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Nulp notif = this.nulpService.create();
		result = this.createEditModelAndView(notif);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int nulpId) {

		final ModelAndView result;

		final Nulp nulp = this.nulpService.findOne(nulpId);
		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		Assert.isTrue(nulp.getTrip().getManager() == actual);
		Assert.notNull(nulp);
		result = this.createEditModelAndView(nulp);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Nulp nulp, final BindingResult binding) {

		ModelAndView result;
		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		Assert.isTrue(nulp.getTrip().getManager() == actual);

		if (binding.hasErrors())
			result = this.createEditModelAndView(nulp);
		else
			try {
				if (nulp.getMoment() == null)
					nulp.setMoment(new Date());
				this.nulpService.save(nulp);
				this.tripService.save(nulp.getTrip());
				result = new ModelAndView("redirect:/nulp/manager/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(nulp, "nulp.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Nulp nulp, final BindingResult binding) {

		ModelAndView result;
		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		Assert.isTrue(nulp.getTrip().getManager() == actual);

		try {
			this.nulpService.delete(nulp);
			result = new ModelAndView("redirect:/nulp/manager/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(nulp, "nulp.commit.error");

		}
		return result;
	}

	//Auxiliars-------------------------
	protected ModelAndView createEditModelAndView(final Nulp nulp) {
		ModelAndView result;

		result = this.createEditModelAndView(nulp, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Nulp nulp, final String messageCode) {
		ModelAndView result;
		final Manager manager = (Manager) this.loginService.getPrincipalActor();
		final List<Trip> trips = manager.getTrips();

		result = new ModelAndView("nulp/edit");
		result.addObject("trips", trips);
		result.addObject("nulp", nulp);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "nulp/manager/edit.do");
		return result;
	}

}
