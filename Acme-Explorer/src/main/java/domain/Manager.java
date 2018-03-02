
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private List<Trip>	trips;


	//	private List<Notification>	notifications;

	//	private List<Note>	notes;

	@NotNull
	@Valid
	@OneToMany(mappedBy = "manager")
	public List<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final List<Trip> trips) {
		this.trips = trips;
	}

	//	@OneToMany(mappedBy = "manager")
	//	@Valid
	//	@ElementCollection
	//	public List<Notification> getNotifications() {
	//		return this.notifications;
	//	}
	//
	//	public void setNotifications(final List<Notification> notifications) {
	//		this.notifications = notifications;
	//	}

	//	@ManyToOne(optional = false)
	//	@NotNull
	//	@Valid
	//	public List<Note> getNotes() {
	//		return this.notes;
	//	}
	//
	//	public void setNotes(final List<Note> notes) {
	//		this.notes = notes;
	//	}

}
