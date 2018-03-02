
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import services.TripService;
import controllers.AbstractController;
import domain.Tag;
import domain.Trip;

@Controller
@RequestMapping("/tag/all")
public class AllTagController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private TripService	tripService;

	@Autowired
	private TagService	tagService;


	// Constructors -----------------------------------------------------------
	public AllTagController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		final ModelAndView result;

		final Trip trip = this.tripService.findOne(tripId);
		final Collection<Tag> tags = trip.getTags();
		final Collection<Tag> allTags = this.tagService.findAll();
		allTags.removeAll(tags);
		result = new ModelAndView("tag/list");
		result.addObject("tags", tags);
		result.addObject("tripId", tripId);
		result.addObject("trip", trip);
		result.addObject("allTag", allTags);
		result.addObject("requestURI", "tag/all/list.do");
		return result;
	}

}
