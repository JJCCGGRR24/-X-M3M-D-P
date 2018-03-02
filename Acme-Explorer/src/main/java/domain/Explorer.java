
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Explorer extends Actor {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Finder				finder;
	private List<Application>	applications;
	private List<Contact>		contacts;
	private List<Story>			stories;
	private List<SurvivalClass>	survivalClasses;


	@OneToOne(optional = true, mappedBy = "explorer", cascade = CascadeType.ALL)
	@Valid
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final List<Application> applies) {
		this.applications = applies;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(final List<Contact> contacts) {
		this.contacts = contacts;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public List<Story> getStories() {
		return this.stories;
	}

	public void setStories(final List<Story> stories) {
		this.stories = stories;
	}

	@NotNull
	@Valid
	@ManyToMany
	public List<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	public void setSurvivalClasses(final List<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

}
