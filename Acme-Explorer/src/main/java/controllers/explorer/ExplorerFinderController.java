
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.FinderService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Finder;

@Controller
@RequestMapping("/finder/explorer")
public class ExplorerFinderController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private LoginService	loginService;

	@Autowired
	private FinderService	finderService;


	// Constructors -----------------------------------------------------------
	public ExplorerFinderController() {
		super();
	}

	//list ----------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("finder/list");
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		res.addObject("f", e.getFinder());
		return res;
	}

	//Find ----------------------------------------------------------------

	@RequestMapping("/find")
	public ModelAndView find() {

		final ModelAndView res = new ModelAndView("trip/list");
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();

		res.addObject("trips", this.finderService.updateCache(e.getFinder()));
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editEndorser() {
		ModelAndView result;
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();

		final Finder finder = e.getFinder();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEndorser(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		final String s = this.finderService.validate(finder);
		if (binding.hasErrors() || s != null)
			result = this.createEditModelAndView(finder, s);
		else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:/finder/explorer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "general.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Finder f) {
		ModelAndView result;

		result = this.createEditModelAndView(f, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder f, final String message) {
		ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", f);
		result.addObject("message", message);
		result.addObject("requestURI", "finder/explorer/edit.do");

		return result;
	}

}
