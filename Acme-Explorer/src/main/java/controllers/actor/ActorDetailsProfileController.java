
package controllers.actor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Ranger;

@Controller
@RequestMapping("/profile/actor")
public class ActorDetailsProfileController {

	//Services ---------------------------------------------------------------

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public ActorDetailsProfileController() {
		super();
	}

	@RequestMapping(value = "/myDetails", method = RequestMethod.GET)
	public ModelAndView myDetails() {
		final ModelAndView result;

		final Actor e = this.loginService.getPrincipalActor();
		Assert.notNull(e);
		result = new ModelAndView("details/list");
		result.addObject("actor", e);
		result.addObject("requestURI", "details/all/list.do");
		final List<Authority> la = (List<Authority>) e.getUserAccount().getAuthorities();
		final Authority aut = la.get(0);
		try {
			if (aut.getAuthority().equals("RANGER")) {
				result.addObject("isRanger", true);
				final Ranger ranger = (Ranger) e;
				if (ranger.getCurricula() != null)
					result.addObject("curriculaId", ranger.getCurricula().getId());
				else
					result.addObject("curriculaId", 0);
			} else if (aut.getAuthority().equals("EXPLORER"))
				result.addObject("isExplorer", true);
			{

			}
		} catch (final Exception e2) {
			// TODO: handle exception
		}
		return result;
	}

}
