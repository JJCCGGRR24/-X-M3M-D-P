
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	banner;
	private String	infoPage;


	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@URL
	public String getInfoPage() {
		return this.infoPage;
	}

	public void setInfoPage(final String infoPage) {
		this.infoPage = infoPage;
	}


	// Relationships ----------------------------------------------------------

	private Sponsor		sponsor;
	private List<Trip>	trips;

	private CreditCard	creditCard;


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@ManyToMany
	@NotNull
	@Valid
	public List<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final List<Trip> trips) {
		this.trips = trips;
	}

	@Valid
	@OneToOne(optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
