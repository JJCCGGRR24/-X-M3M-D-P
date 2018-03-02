
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.NulpService;
import services.TripService;
import domain.Nulp;

@Controller
@RequestMapping("/nulp/actor")
public class ActorNulpController {

	//Services ---------------------------------------------------------------
	@Autowired
	private NulpService		nulpService;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private TripService		tripService;


	// Constructors -----------------------------------------------------------
	public ActorNulpController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list(final int tripId) {
		final ModelAndView res = new ModelAndView("nulp/list");
		//		final Trip trip = this.tripService.findOne(tripId);
		final Collection<Nulp> nulps = this.nulpService.getNulpsVisibles(tripId);

		//		if (LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("MANAGER"))
		//			nulps = this.nulpService.getNulpsByManager((Manager) this.loginService.getPrincipalActor());
		//		else if (LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("EXPLORER"))
		//			nulps = this.nulpService.getNulpsByExplorer((Explorer) this.loginService.getPrincipalActor());
		//		else if (LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("RANGER"))
		//			nulps = this.nulpService.getNulpsByRanger((Ranger) this.loginService.getPrincipalActor());
		//		else if (LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("AUDITOR"))
		//			nulps = this.nulpService.getNulpsByAuditor((Auditor) this.loginService.getPrincipalActor());

		res.addObject("nulps", nulps);
		res.addObject("requestURI", "nulp/actor/list.do");
		return res;
	}
}
