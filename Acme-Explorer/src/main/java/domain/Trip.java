
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String			title;
	private String			ticker;
	private String			description;
	private List<String>	requirements;
	private Date			publicationDate;
	private Date			starts;
	private Date			ends;
	private String			cancelledReason;
	private double			price;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@Column(unique = true)
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@ElementCollection
	public List<String> getRequirements() {
		return this.requirements;
	}

	public void setRequirements(final List<String> requirements) {
		this.requirements = requirements;
	}

	@Past
	//	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStarts() {
		return this.starts;
	}

	public void setStarts(final Date starts) {
		this.starts = starts;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEnds() {
		return this.ends;
	}

	public void setEnds(final Date ends) {
		this.ends = ends;
	}

	public String getCancelledReason() {
		return this.cancelledReason;
	}

	public void setCancelledReason(final String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}

	@Min(0)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}


	// Relationships ----------------------------------------------------------

	private List<Stage>			stages;
	private List<Tag>			tags;
	private LegalText			legalText;
	private Ranger				ranger;
	private List<SurvivalClass>	survivalClasses;
	private Manager				manager;
	private List<Finder>		finders;
	private List<Application>	applications;
	private List<Story>			stories;
	private List<Note>			notes;
	private List<Audit>			audits;
	private List<Sponsorship>	sponsorships;
	private Category			category;

	//relationship for exam
	private List<Nulp>			nulps;


	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
	@Valid
	public List<Stage> getStages() {
		return this.stages;
	}

	public void setStages(final List<Stage> stages) {
		this.stages = stages;
	}

	@NotNull
	@ManyToMany(mappedBy = "trips")
	@Valid
	public List<Tag> getTags() {
		return this.tags;
	}

	public void setTags(final List<Tag> tags) {
		this.tags = tags;
	}

	@ManyToOne(optional = true)
	@Valid
	public LegalText getLegalText() {
		return this.legalText;
	}

	public void setLegalText(final LegalText legalText) {
		this.legalText = legalText;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Ranger getRanger() {
		return this.ranger;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	@Valid
	public List<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	public void setSurvivalClasses(final List<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@NotNull
	@ManyToMany(mappedBy = "trips")
	@Valid
	public List<Finder> getFinders() {
		return this.finders;
	}

	public void setFinders(final List<Finder> finders) {
		this.finders = finders;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	@Valid
	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final List<Application> applications) {
		this.applications = applications;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public List<Story> getStories() {
		return this.stories;
	}

	public void setStories(final List<Story> stories) {
		this.stories = stories;
	}

	@OneToMany(mappedBy = "trip")
	@Valid
	public List<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final List<Note> notes) {
		this.notes = notes;
	}

	@OneToMany(mappedBy = "trip")
	@Valid
	public List<Audit> getAudits() {
		return this.audits;
	}

	public void setAudits(final List<Audit> a) {
		this.audits = a;
	}

	@NotNull
	@ManyToMany(mappedBy = "trips")
	@Valid
	public List<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final List<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	@ManyToOne(optional = true)
	@Valid
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	//relationship for exam
	@Valid
	@ElementCollection
	@OneToMany(mappedBy = "trip")
	public Collection<Nulp> getNulps() {
		return this.nulps;
	}

	public void setNulps(final List<Nulp> nulps) {
		this.nulps = nulps;
	}
}
