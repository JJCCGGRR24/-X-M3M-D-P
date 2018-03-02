
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Ranger extends Actor {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private List<Trip>	trips;
	private Curricula	curricula;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "ranger")
	public List<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final List<Trip> trips) {
		this.trips = trips;
	}

	@OneToOne(optional = true, mappedBy = "ranger")
	@Valid
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

}
