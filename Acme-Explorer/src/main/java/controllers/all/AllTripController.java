
package controllers.all;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.TripService;
import controllers.AbstractController;
import domain.Trip;

@Controller
@RequestMapping("/trip/all")
public class AllTripController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private TripService		tripService;

	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------------------------------------------------------
	public AllTripController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer categoryId, @RequestParam(required = false) final String search) {
		final ModelAndView res = new ModelAndView("trip/list");
		final List<Trip> l;
		if (categoryId != null) {
			final List<Trip> trips = this.categoryService.findOne(categoryId).getTrips();

			l = (this.categoryService.getTripVisibleByCategory(trips));
		} else if (search != null)
			l = (List<Trip>) this.tripService.search(search);
		else
			l = ((List<Trip>) this.tripService.getTripsVisible());
		res.addObject("trips", l);
		final Date fecha = new Date();
		res.addObject("fechaActual", fecha);
		res.addObject("requestURI", "trip/all/list.do");
		res.addObject("banners", this.tripService.randomBannerList(l));

		return res;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam final int tripId) {
		final ModelAndView result;

		final Trip trip = this.tripService.findOne(tripId);
		result = new ModelAndView("trip/details");
		result.addObject("trip", trip);
		result.addObject("requestURI", "trip/all/details.do");
		return result;
	}

}
