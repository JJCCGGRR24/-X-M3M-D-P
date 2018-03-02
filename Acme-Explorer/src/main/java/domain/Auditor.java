
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private List<Audit>	audits;
	private List<Note>	notes;


	@OneToMany(mappedBy = "auditor")
	@Valid
	public List<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final List<Note> notes) {
		this.notes = notes;
	}

	@OneToMany(mappedBy = "auditor")
	@Valid
	public List<Audit> getAudits() {
		return this.audits;
	}

	public void setAudits(final List<Audit> a) {
		this.audits = a;
	}
}
