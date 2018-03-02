
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
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date	momentCreation;
	private String	remark;
	private String	comment;
	private Date	momentReply;


	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentCreation() {
		return this.momentCreation;
	}
	public void setMomentCreation(final Date m) {
		this.momentCreation = m;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String c) {
		this.comment = c;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentReply() {
		return this.momentReply;
	}
	public void setMomentReply(final Date d) {
		this.momentReply = d;
	}

	@NotBlank
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(final String r) {
		this.remark = r;
	}


	// Relationships ----------------------------------------------------------

	private Auditor	auditor;
	//	private List<Manager>	managers;
	private Trip	trip;


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Auditor getAuditor() {
		return this.auditor;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	//	@NotNull
	//	@OneToMany(mappedBy = "note")
	//	public List<Manager> getManagers() {
	//		return this.managers;
	//	}
	//
	//	public void setManagers(final List<Manager> managers) {
	//		this.managers = managers;
	//	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
