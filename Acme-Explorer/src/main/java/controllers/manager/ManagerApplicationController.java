
package controllers.manager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Application;
import domain.Manager;

@Controller
@RequestMapping("/application/manager")
public class ManagerApplicationController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private ManagerService		managerService;

	@Autowired
	private LoginService		loginService;

	@Autowired
	private ApplicationService	applyService;


	// Constructors -----------------------------------------------------------
	public ManagerApplicationController() {
		super();
	}

	//list ----------------------------------------------------------------

	@SuppressWarnings("deprecation")
	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("application/list");
		final Manager e = (Manager) this.loginService.getPrincipalActor();
		res.addObject("applications", this.managerService.getApplications(e));
		res.addObject("requestURI", "application/manager/list.do");
		final Date d = new Date();
		res.addObject("dateNow", d);
		final Date dateMonthAfter = new Date();
		dateMonthAfter.setMonth(dateMonthAfter.getMonth() + 1);
		res.addObject("dateMonthAfter", dateMonthAfter);
		return res;
	}

	//Rejected ----------------------------------------------------------------

	@RequestMapping("/rejected")
	public ModelAndView rejected(@RequestParam final int applicationId) {
		ModelAndView res = new ModelAndView("application/list");

		final Application a = this.applyService.changePendingToRejectManager(this.applyService.findOne(applicationId));

		this.applyService.NotifyExplorer(a);
		this.applyService.NotifyManager(a);
		res = new ModelAndView("redirect:list.do");
		return res;
	}

	//Due ----------------------------------------------------------------

	@RequestMapping("/due")
	public ModelAndView due(@RequestParam final int applicationId) {
		ModelAndView res = new ModelAndView("application/list");

		final Application a = this.applyService.changePendingToDueManager(this.applyService.findOne(applicationId));

		this.applyService.NotifyExplorer(a);
		this.applyService.NotifyManager(a);

		res = new ModelAndView("redirect:list.do");
		return res;
	}
}
