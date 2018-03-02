
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.FolderService;
import services.MesageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Mesage;

@Controller
@RequestMapping("/broadcast/admin")
public class AdminBroadcastController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MesageService	mesageService;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private FolderService	folderService;


	// Constructors -----------------------------------------------------------

	public AdminBroadcastController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Mesage message;
		final Actor logueado = this.loginService.getPrincipalActor();
		message = this.mesageService.create();
		message.setSender(logueado);
		message.setRecipient(logueado);
		message.setFolder(this.folderService.getOutbox(logueado));
		result = new ModelAndView("admin/broadcast/edit");
		result.addObject("mesage", message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "send")
	public ModelAndView send(@Valid final Mesage mesage, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == mesage.getFolder().getActor().getId());
		if (binding.hasErrors()) {
			result = new ModelAndView("admin/broadcast/edit");
			result.addObject("mesage", mesage);
			result.addObject("priorities", this.mesageService.getPriorities());
		} else
			try {
				this.mesageService.sendBroadcast(mesage);
				result = new ModelAndView("redirect:../../welcome/index.do");
				result.addObject("message", "message.commit.ok");
			} catch (final Throwable oops) {
				result = new ModelAndView("admin/broadcast/edit");
				result.addObject("mesage", mesage);
				result.addObject("priorities", this.mesageService.getPriorities());
				result.addObject("message", "message.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Mesage message) {
		ModelAndView result;
		result = this.createEditModelAndView(message, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Mesage mensaje, final String message) {
		ModelAndView result;

		result = new ModelAndView("admin/broadcast/edit");
		result.addObject("mesage", mensaje);
		result.addObject("message", message);

		return result;
	}
}
