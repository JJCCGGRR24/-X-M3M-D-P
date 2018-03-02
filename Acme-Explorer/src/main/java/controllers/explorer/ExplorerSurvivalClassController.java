
package controllers.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ExplorerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.SurvivalClass;

@Controller
@RequestMapping("/survivalClass/explorer")
public class ExplorerSurvivalClassController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private LoginService			loginService;

	@Autowired
	private ExplorerService			explorerService;


	// Constructors -----------------------------------------------------------
	public ExplorerSurvivalClassController() {
		super();
	}

	// Create ---------------------------------------------------------------

	// Edit ---------------------------------------------------------------
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam final int survivalClassId) {
		final ModelAndView res = new ModelAndView("survivalClass/edit");
		res.addObject("survivalClass", this.survivalClassService.findOne(survivalClassId));
		return res;
	}

	//list ----------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("survivalClass/list");
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		res.addObject("survivalClasses", this.explorerService.getSurvivalClassesApplyAccepted(e));
		res.addObject("requestURI", "survivalClass/manager/list.do");
		try {
			res.addObject("explorer", this.loginService.getPrincipalActor());
		} catch (final Exception e2) {

		}

		return res;
	}
	//Enrol ----------------------------------------------------------------

	@RequestMapping("/enrol")
	public ModelAndView enrol(@RequestParam final int survivalClassId) {
		ModelAndView res = null;

		final SurvivalClass a = this.survivalClassService.findOne(survivalClassId);
		this.survivalClassService.enrolExplorer(a);

		res = new ModelAndView("redirect:list.do");
		return res;
	}

	//Unenrol ----------------------------------------------------------------

	@RequestMapping("/unenrol")
	public ModelAndView unenrol(@RequestParam final int survivalClassId) {
		ModelAndView res = null;

		final SurvivalClass a = this.survivalClassService.findOne(survivalClassId);
		this.survivalClassService.unenrolExplorer(a);

		res = new ModelAndView("redirect:list.do");
		return res;
	}
}
