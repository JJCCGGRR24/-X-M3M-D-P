
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class LegalText extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String			title;
	private String			body;
	private Date			moment;
	private List<String>	laws;
	private boolean			finalMode;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	@ElementCollection
	public List<String> getLaws() {
		return this.laws;
	}

	public void setLaws(final List<String> laws) {
		this.laws = laws;
	}

	@NotNull
	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}


	// Relationships ----------------------------------------------------------

	private List<Trip>	trips;


	@OneToMany(mappedBy = "legalText")
	public List<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final List<Trip> trip) {
		this.trips = trip;
	}

}
