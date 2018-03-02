
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ExplorerService;
import services.RangerService;
import domain.Curricula;
import domain.Explorer;
import domain.Ranger;

@Controller
@RequestMapping("/registry")
public class RegistryController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private RangerService	rangerService;

	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------
	public RegistryController() {
		super();
	}

	//Register as ranger ----------------------------------------------------------------

	@RequestMapping(value = "/asRanger", method = RequestMethod.GET)
	public ModelAndView asRanger() {
		ModelAndView res = new ModelAndView("register/ranger");
		final Ranger ranger = this.rangerService.create();
		res = this.createEditModelAndViewRanger(ranger);
		return res;
	}

	@RequestMapping(value = "/asRanger", method = RequestMethod.POST, params = "save")
	public ModelAndView asRanger(@Valid final Ranger ranger, final BindingResult bindingResult) {

		ModelAndView res = new ModelAndView("register/ranger");
		ranger.setCurricula(null);
		if (bindingResult.hasErrors())
			res = this.createEditModelAndViewRanger(ranger);
		else if (this.actorService.exist(ranger.getUserAccount().getUsername()))
			res = this.createEditModelAndViewRanger(ranger, "username.exists");
		else
			try {
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				ranger.getUserAccount().setPassword(encoder.encodePassword((ranger.getUserAccount().getPassword()), null));
				this.rangerService.save(ranger);
				res = new ModelAndView("redirect:../welcome/index.do");

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndViewRanger(ranger, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndViewRanger(ranger, "template.commit.error");
			}
		return res;
	}

	//Register as explorer ----------------------------------------------------------------

	@RequestMapping(value = "/asExplorer", method = RequestMethod.GET)
	public ModelAndView asExplorer() {
		ModelAndView res = new ModelAndView("register/explorer");
		final Explorer explorer = this.explorerService.create();
		res = this.createEditModelAndViewExplorer(explorer);
		return res;
	}

	@RequestMapping(value = "/asExplorer", method = RequestMethod.POST, params = "save")
	public ModelAndView asExplorer(@Valid final Explorer explorer, final BindingResult bindingResult) {

		ModelAndView res = new ModelAndView("register/explorer");

		if (bindingResult.hasErrors())
			res = this.createEditModelAndViewExplorer(explorer);
		else if (this.actorService.exist(explorer.getUserAccount().getUsername()))
			res = this.createEditModelAndViewExplorer(explorer, "username.exists");
		else
			try {
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				explorer.getUserAccount().setPassword(encoder.encodePassword((explorer.getUserAccount().getPassword()), null));
				this.explorerService.save(explorer);
				res = new ModelAndView("redirect:../welcome/index.do");

			} catch (final DataIntegrityViolationException oops) {
				res = this.createEditModelAndViewExplorer(explorer, "template.error.exists");
			} catch (final Throwable oops) {
				res = this.createEditModelAndViewExplorer(explorer, "template.commit.error");
			}
		return res;
	}
	//Ranger
	protected ModelAndView createEditModelAndViewRanger(final Ranger r) {
		ModelAndView result;

		result = this.createEditModelAndViewRanger(r, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewRanger(final Ranger r, final String message) {
		ModelAndView result;
		r.setCurricula(new Curricula());
		result = new ModelAndView("register/ranger");
		result.addObject("ranger", r);
		result.addObject("message", message);
		result.addObject("requestURI", "registry/asRanger.do");

		return result;
	}

	//Explorer
	protected ModelAndView createEditModelAndViewExplorer(final Explorer r) {
		ModelAndView result;

		result = this.createEditModelAndViewExplorer(r, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewExplorer(final Explorer r, final String message) {
		ModelAndView result;

		result = new ModelAndView("register/explorer");
		result.addObject("explorer", r);
		result.addObject("message", message);
		result.addObject("requestURI", "registry/asExplorer.do");

		return result;
	}
}
