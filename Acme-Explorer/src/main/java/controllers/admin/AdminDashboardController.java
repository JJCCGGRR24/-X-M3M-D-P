
package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.AuditService;
import services.LegalTextService;
import services.ManagerService;
import services.NoteService;
import services.RangerService;
import services.TripService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/admin")
public class AdminDashboardController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private NoteService			noteService;

	@Autowired
	private AuditService		auditService;


	// Constructors -----------------------------------------------------------
	public AdminDashboardController() {
		super();
	}

	// Display dashboard ---------------------------------------------------------------
	@RequestMapping("/display")
	public ModelAndView dashboard() {

		final ModelAndView res = new ModelAndView("dashboard");
		res.addObject("queryC1", this.applicationService.queryC1());
		res.addObject("queryC2", this.tripService.queryC2());
		res.addObject("queryC3", this.tripService.queryC3());
		res.addObject("queryC4", this.tripService.queryC4());
		res.addObject("queryC5", this.applicationService.queryC5());
		res.addObject("queryC6", this.applicationService.queryC6());
		res.addObject("queryC7", this.applicationService.queryC7());
		res.addObject("queryC8", this.applicationService.queryC8());
		res.addObject("queryC9", this.tripService.queryC9());
		res.addObject("queryC10", this.tripService.queryC10());
		res.addObject("queryC11", this.legalTextService.queryC11());

		res.addObject("queryB1", this.noteService.queryB1());
		res.addObject("queryB2", this.auditService.queryB2());
		res.addObject("queryB3", this.tripService.queryB3());
		res.addObject("queryB4", this.rangerService.queryB4());
		res.addObject("queryB5", this.rangerService.queryB5());
		res.addObject("queryB6", this.managerService.queryB6());
		res.addObject("queryB7", this.rangerService.queryB7());

		return res;
	}
}
