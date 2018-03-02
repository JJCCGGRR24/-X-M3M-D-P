
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.LoginService;
import domain.Actor;
import domain.Auditor;
import domain.Note;
import domain.Trip;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private NoteRepository			noteRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService			loginService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public NoteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Note create(final Trip trip) {
		final Note r = new Note();
		final Auditor auditor = (Auditor) this.loginService.getPrincipalActor();
		r.setAuditor(auditor);

		final List<Note> notesAuditor = auditor.getNotes();
		notesAuditor.add(r);
		auditor.setNotes(notesAuditor);

		r.setMomentCreation(new Date(System.currentTimeMillis() - 1000));

		r.setTrip(trip);

		final List<Note> notes = trip.getNotes();
		notes.add(r);
		trip.setNotes(notes);

		return r;
	}

	public Collection<Note> findAll() {
		final Collection<Note> res = this.noteRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Note findOne(final int noteId) {
		return this.noteRepository.findOne(noteId);
	}

	public Note save(final Note note) {
		Assert.notNull(note);
		note.setMomentCreation(new Date(System.currentTimeMillis() - 1000));
		if (note.getId() != 0 && this.noteRepository.findOne(note.getId()).getRemark() != note.getRemark())
			note.setMomentReply(new Date(System.currentTimeMillis() - 1000));
		this.detectSpam(note);
		return this.noteRepository.save(note);
	}

	public void delete(final Note note) {
		this.noteRepository.delete(note);
	}

	public void flush() {
		this.noteRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public List<Double> queryB1() {
		final List<Double> l = new ArrayList<Double>();
		final Object[] o = this.noteRepository.queryB1();
		l.add((Double) o[0]);
		l.add((Double) o[1]);
		l.add((Double) o[2]);
		l.add((Double) o[3]);
		return l;
	}

	private boolean detectSpam(final Note t) {
		boolean res = false;
		for (final String sw : this.configurationService.find().getSpamWords())
			if (t.getComment().contains(sw)) {
				res = true;
				final Actor a = t.getTrip().getManager();
				a.setSuspicious(res);
				this.actorService.save(a);
				break;
			}
		return res;
	}
}
