
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ProfessionalRecord extends DomainEntity {

	// Attributes ---------------------------------------------------------------

	private String	companyName;
	private Date	startDate;
	private Date	endDate;
	private String	role;
	private String	comments;
	private String	attachment;


	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}
	public void setCompanyName(final String cn) {
		this.companyName = cn;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date p) {
		this.startDate = p;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(final Date p) {
		this.endDate = p;
	}

	@NotBlank
	public String getRole() {
		return this.role;
	}
	public void setRole(final String r) {
		this.role = r;
	}

	public String getComments() {
		return this.comments;
	}
	public void setComments(final String c) {
		this.comments = c;
	}

	@URL
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
