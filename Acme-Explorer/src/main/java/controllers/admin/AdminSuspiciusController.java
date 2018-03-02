
package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/suspicius/admin")
public class AdminSuspiciusController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;


	// Constructors -----------------------------------------------------------
	public AdminSuspiciusController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		result = new ModelAndView("suspicius/list");
		result.addObject("suspicius", this.actorService.getSuspiciusRangerAndManager());
		result.addObject("requestURI", "suspicius/admin/list.do");
		final Authority a = new Authority();
		a.setAuthority("BANNED");
		result.addObject("auth", a);
		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam(required = true) final int actorId) {
		final ModelAndView result;

		final Actor actor = this.actorService.findOne(actorId);

		this.adminService.ban(actor);

		result = new ModelAndView("redirect:list.do");
		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam(required = true) final int actorId) {
		final ModelAndView result;

		final Actor actor = this.actorService.findOne(actorId);

		this.adminService.unban(actor);

		result = new ModelAndView("redirect:list.do");
		return result;
	}

}
