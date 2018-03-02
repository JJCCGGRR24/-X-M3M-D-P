
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.TagService;
import services.TripService;
import controllers.AbstractController;
import domain.Tag;
import domain.Trip;

@Controller
@RequestMapping("/tag/manager")
public class ManagerTagController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private TripService		tripService;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private TagService		tagService;


	// Constructors -----------------------------------------------------------
	public ManagerTagController() {
		super();
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int tagId, @RequestParam final int tripId) {

		ModelAndView result;
		final Trip trip = this.tripService.findOne(tripId);
		final Tag tag = this.tagService.findOne(tagId);
		Assert.isTrue(trip.getManager() == this.loginService.getPrincipalActor());
		try {
			this.tagService.add(tag, trip);
			result = new ModelAndView("redirect:/tag/all/list.do?tripId=" + tripId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tag, "tag.commit.error");

		}
		return result;
	}

	@RequestMapping(value = "/takeOff", method = RequestMethod.GET)
	public ModelAndView takeOff(@RequestParam final int tagId, @RequestParam final int tripId) {

		ModelAndView result;
		final Trip trip = this.tripService.findOne(tripId);
		final Tag tag = this.tagService.findOne(tagId);
		Assert.isTrue(trip.getManager() == this.loginService.getPrincipalActor());
		try {
			this.tagService.takeOff(tag, trip);
			result = new ModelAndView("redirect:/tag/all/list.do?tripId=" + tripId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tag, "tag.commit.error");

		}
		return result;
	}

	//Auxiliars-------------------------
	protected ModelAndView createEditModelAndView(final Tag tag) {
		ModelAndView result;
		result = this.createEditModelAndView(tag, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Tag tag, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("tag/edit");
		result.addObject("tag", tag);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "tag/manager/edit.do");

		return result;
	}

}
