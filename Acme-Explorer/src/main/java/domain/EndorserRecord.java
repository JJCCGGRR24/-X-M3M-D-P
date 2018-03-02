
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	// Attributes ----------------------------------------------------------

	private String	name;
	private String	email;
	private String	phone;
	private String	linkedProfile;
	private String	comments;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}
	@NotBlank
	@URL
	public String getLinkedProfile() {
		return this.linkedProfile;
	}
	public void setLinkedProfile(final String linkedProfile) {
		this.linkedProfile = linkedProfile;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
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
