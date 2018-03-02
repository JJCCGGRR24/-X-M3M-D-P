
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

import services.LegalTextService;
import controllers.AbstractController;
import domain.LegalText;

@Controller
@RequestMapping("/legalText/admin")
public class AdminLegalTextController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private LegalTextService	legalTextService;


	// Constructors -----------------------------------------------------------
	public AdminLegalTextController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<LegalText> legalTexts = this.legalTextService.findAll();
		result = new ModelAndView("legalText/list");
		result.addObject("legalTexts", legalTexts);
		result.addObject("requestURI", "legalText/admin/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final LegalText legalText = this.legalTextService.create();
		result = this.createEditModelAndView(legalText);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalTextId) {

		final ModelAndView result;
		final LegalText legalText = this.legalTextService.findOne(legalTextId);
		Assert.isTrue(legalText.getFinalMode() == false);
		//		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		//		Assert.isTrue(trip.getManager() == actual);
		Assert.notNull(legalText);
		result = this.createEditModelAndView(legalText);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LegalText legalText, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(legalText);
		else
			try {
				this.legalTextService.save(legalText);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(legalText, "legalText.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LegalText legalText, final BindingResult binding) {

		ModelAndView result;
		Assert.isTrue(legalText.getFinalMode() == false);

		try {
			this.legalTextService.delete(legalText);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(legalText, "commit.error");

		}
		return result;
	}

	//Auxiliars-------------------------
	protected ModelAndView createEditModelAndView(final LegalText legalText) {
		ModelAndView result;
		result = this.createEditModelAndView(legalText, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final LegalText legalText, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("legalText/edit");
		result.addObject("legalText", legalText);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "legalText/admin/edit.do");

		return result;
	}

}
