
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Attributes ---------------------------------------------------------------

	private String			ticker;
	private PersonalRecord	personalRecord;


	@NotBlank
	@Pattern(regexp = "^\\d\\d(0[1-9]|1[0-2])([0][1-9]|[1-2][0-9]|3[0-1])-([A-Z]){4}$")
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String t) {
		this.ticker = t;
	}

	@Valid
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}
	public void setPersonalRecord(final PersonalRecord pr) {
		this.personalRecord = pr;
	}


	// Relationships ----------------------------------------------------------

	private List<EndorserRecord>		endorserRecords;
	private List<MiscellaneousRecord>	miscellaneousRecords;
	private List<EducationRecord>		educationRecords;
	private List<ProfessionalRecord>	professionalRecords;
	private Ranger						ranger;


	@NotNull
	@OneToMany(mappedBy = "curriculum")
	public List<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}
	public void setEndorserRecords(final List<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	@NotNull
	@OneToMany(mappedBy = "curriculum")
	public List<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}
	public void setMiscellaneousRecords(final List<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

	@NotNull
	@OneToMany(mappedBy = "curriculum")
	public List<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}
	public void setEducationRecords(final List<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	@NotNull
	@OneToMany(mappedBy = "curriculum")
	public List<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}
	public void setProfessionalRecords(final List<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Ranger getRanger() {
		return this.ranger;
	}
	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

}
