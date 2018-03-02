
package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import domain.Manager;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/manager")
public class ManagerNoteController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private NoteService		noteService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public ManagerNoteController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("note/list");
		final Manager actual = (Manager) this.loginService.getPrincipalActor();
		final Collection<Trip> trips = actual.getTrips();
		final List<Note> notes = new ArrayList<Note>();
		for (final Trip t : trips)
			notes.addAll(t.getNotes());
		res.addObject("notes", notes);
		res.addObject("requestURI", "note/manager/list.do");

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {

		final ModelAndView result;

		final Note note = this.noteService.findOne(noteId);
		final Manager a = (Manager) this.loginService.getPrincipalActor();
		Assert.notNull(note);
		Assert.isTrue(note.getTrip().getManager() == a);
		Assert.isTrue(note.getMomentReply() == null);
		result = this.createEditModelAndView(note);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {

		ModelAndView result;
		Assert.isTrue(note.getMomentReply() == null);
		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				if (note.getComment().isEmpty())
					result = this.createEditModelAndView(note, "note.comment.error");
				else {
					this.noteService.save(note);
					result = new ModelAndView("redirect:list.do");
				}
			} catch (final Throwable oops) {

				result = this.createEditModelAndView(note, "note.commit.error");
			}
		return result;
	}

	//Auxiliars-------------------------
	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;
		result = this.createEditModelAndView(note, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/reply");
		result.addObject("note", note);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "note/manager/edit.do");

		return result;
	}
}
