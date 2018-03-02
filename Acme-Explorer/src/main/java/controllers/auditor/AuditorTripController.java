
package controllers.auditor;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.TripService;
import controllers.AbstractController;
import domain.Trip;

@Controller
@RequestMapping("/trip/auditor")
public class AuditorTripController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private LoginService	loginService;

	@Autowired
	private TripService		tripService;


	// Constructors -----------------------------------------------------------
	public AuditorTripController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("trip/list");
		final List<Trip> l = ((List<Trip>) this.tripService.getTripsVisible());
		res.addObject("trips", l);
		res.addObject("requestURI", "trip/auditor/list.do");
		res.addObject("banners", this.tripService.randomBannerList(l));

		final Authority aut = this.loginService.getPrincipalActor().getUserAccount().getAuthorities().iterator().next();
		if (aut.getAuthority().equals("AUDITOR")) {
			final Collection<Trip> auditsAuditor = this.tripService.getTripsByAuditor();
			res.addObject("auditsAuditor", auditsAuditor);
		}
		return res;
	}
}
