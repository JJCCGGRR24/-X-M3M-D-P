
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
import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/auditor")
public class AuditorNoteController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private NoteService		noteService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public AuditorNoteController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("note/list");

		final Auditor a = (Auditor) this.loginService.getPrincipalActor();
		res.addObject("notes", a.getNotes());
		res.addObject("requestURI", "note/auditor/list.do");

		return res;
	}
	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int tripId) {
		final ModelAndView result;

		final Trip trip = this.tripService.findOne(tripId);
		final Note note = this.noteService.create(trip);
		result = this.createEditModelAndView(note);
		return result;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam final int noteId) {
		final ModelAndView result;

		final Note note = this.noteService.findOne(noteId);
		Assert.notNull(note);

		final Auditor a = (Auditor) this.loginService.getPrincipalActor();
		Assert.notNull(note);
		Assert.isTrue(note.getAuditor() == a);

		Assert.isTrue(noteId == 0 && note.getMomentCreation() == null);
		result = this.createEditModelAndView(note);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");

			}
		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Note note, final BindingResult binding) {
	//
	//		ModelAndView result;
	//
	//		try {
	//			this.noteService.delete(note);
	//			result = new ModelAndView("redirect:list.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(note, "note.commit.error");
	//
	//		}
	//		return result;
	//	}

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "note/auditor/edit.do");

		return result;
	}

}
