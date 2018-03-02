
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import controllers.AbstractController;
import domain.Audit;
import domain.Trip;

@Controller
@RequestMapping("/audit/all")
public class AllAuditController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private TripService	tripService;


	// Constructors -----------------------------------------------------------
	public AllAuditController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		final ModelAndView result;

		final Trip trip = this.tripService.findOne(tripId);
		final Collection<Audit> audits = trip.getAudits();
		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("requestURI", "audit/all/list.do");
		return result;
	}

}
