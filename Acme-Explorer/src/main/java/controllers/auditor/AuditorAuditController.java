
package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuditService;
import services.TripService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Trip;

@Controller
@RequestMapping("/audit/auditor")
public class AuditorAuditController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private AuditService	auditService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public AuditorAuditController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("audit/list");
		res.addObject("audits", ((Auditor) this.loginService.getPrincipalActor()).getAudits());
		return res;
	}

	// Create ---------------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create(@RequestParam final int tripId) {
		final Trip trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		Assert.isTrue(!this.tripService.getTripsByAuditor(this.loginService.getPrincipalActor()).contains(trip));
		final ModelAndView res = new ModelAndView("audit/edit");
		res.addObject("audit", this.auditService.create(trip));
		return res;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editAudit(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;
		audit = this.auditService.findOne(auditId);
		Assert.notNull(audit);
		Assert.isTrue(audit.getAuditor().getId() == this.loginService.getPrincipalActor().getId());
		Assert.isTrue(audit.getFinalMode() == false);
		result = this.createEditModelAndViewAudit(audit);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAudit(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;
		final int auditorId = this.loginService.getPrincipalActor().getId();
		if (audit.getId() != 0) {
			final Audit auditBBDD = this.auditService.findOne(audit.getId());
			Assert.notNull(auditBBDD);
			Assert.isTrue(auditBBDD.getAuditor().getId() == auditorId);
		}
		Assert.isTrue(audit.getAuditor().getId() == auditorId);
		if (binding.hasErrors())
			result = this.createEditModelAndViewAudit(audit);
		else
			try {
				this.auditService.save(audit);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewAudit(audit, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteAudit(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		final int auditorId = this.loginService.getPrincipalActor().getId();
		final Audit auditBBDD = this.auditService.findOne(audit.getId());
		Assert.notNull(auditBBDD);
		Assert.isTrue(auditBBDD.getAuditor().getId() == auditorId);
		Assert.isTrue(audit.getAuditor().getId() == auditorId);
		Assert.isTrue(audit.getFinalMode() == false);
		try {
			this.auditService.delete(audit);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewAudit(audit, "commit.error");
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------------------------
	protected ModelAndView createEditModelAndViewAudit(final Audit audit) {
		ModelAndView result;

		result = this.createEditModelAndViewAudit(audit, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAudit(final Audit audit, final String message) {
		ModelAndView result;

		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("message", message);

		return result;
	}
}
