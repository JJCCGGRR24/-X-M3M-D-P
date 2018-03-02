
package controllers.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;

@Controller
@RequestMapping("/actor/all")
public class AllActorController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------
	public AllActorController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping("/profile.do")
	public ModelAndView list(@RequestParam final int actorId) {
		final ModelAndView res = new ModelAndView("actor/profile");
		res.addObject("actor", this.actorService.findOne(actorId));
		return res;
	}

}
