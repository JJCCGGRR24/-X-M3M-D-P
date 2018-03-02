
package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import controllers.AbstractController;
import domain.Tag;

@Controller
@RequestMapping("/tag/admin")
public class AdminTagController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private TagService	tagService;


	// Constructors -----------------------------------------------------------
	public AdminTagController() {
		super();
	}

	@RequestMapping(value = "/catalogue", method = RequestMethod.GET)
	public ModelAndView catalogue() {
		final ModelAndView result;

		final Collection<Tag> tags = this.tagService.findAll();
		result = new ModelAndView("tag/catalogue");
		result.addObject("tags", tags);
		result.addObject("tagsFree", this.tagService.getTagsFree());
		result.addObject("requestURI", "tag/admin/catalogue.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Tag tag = this.tagService.create();
		result = this.createEditModelAndView(tag);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagId) {

		final ModelAndView result;
		final Tag tag = this.tagService.findOne(tagId);
		//		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		//		Assert.isTrue(trip.getManager() == actual);
		Assert.notNull(tag);
		Assert.isTrue(tag.getTrips().isEmpty());
		result = this.createEditModelAndView(tag);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tag tag, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tag);
		else
			try {
				this.tagService.save(tag);
				result = new ModelAndView("redirect:catalogue.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tag, "tag.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tag tag, final BindingResult binding) {

		ModelAndView result;

		//		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		//		Assert.isTrue(trip.getManager() == actual);
		//		Assert.isTrue(trip.getPublicationDate() == null);
		Assert.isTrue(tag.getTrips().isEmpty());
		try {
			this.tagService.delete(tag);
			result = new ModelAndView("redirect:catalogue.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tag, "tag.commit.error");

		}
		return result;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView deleteRow(@RequestParam(required = true) final int tagId) {

		ModelAndView result = null;
		final Tag tag = this.tagService.findOne(tagId);
		Assert.notNull(tag);
		this.tagService.delete(tag);
		result = new ModelAndView("redirect:catalogue.do");
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
		result.addObject("requestURI", "tag/admin/edit.do");

		return result;
	}

}
