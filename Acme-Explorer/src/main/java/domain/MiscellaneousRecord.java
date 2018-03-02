
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousRecord extends DomainEntity {

	// Attributes ---------------------------------------------------------------

	private String	title;
	private String	comments;
	private String	attachment;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String t) {
		this.title = t;
	}

	public String getComments() {
		return this.comments;
	}
	public void setComments(final String c) {
		this.comments = c;
	}

	public String getAttachment() {
		return this.attachment;
	}
	public void setAttachment(final String c) {
		this.attachment = c;
	}


	// Relationships ----------------------------------------------------------

	private Curricula	curriculum;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Curricula getCurriculum() {
		return this.curriculum;
	}
	public void setCurriculum(final Curricula curriculum) {
		this.curriculum = curriculum;
	}

}
