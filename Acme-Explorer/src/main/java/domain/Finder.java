
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Double	minimumPrice;
	private Double	maximumPrice;
	private String	keyword;
	private Date	momentCache;
	private Date	dateMin;
	private Date	dateMax;


	@Min(0)
	public Double getMinimumPrice() {
		return this.minimumPrice;
	}
	public void setMinimumPrice(final Double m) {
		this.minimumPrice = m;
	}

	@Min(0)
	public Double getMaximumPrice() {
		return this.maximumPrice;
	}
	public void setMaximumPrice(final Double m) {
		this.maximumPrice = m;
	}

	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(final String k) {
		this.keyword = k;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentCache() {
		return this.momentCache;
	}
	public void setMomentCache(final Date m) {
		this.momentCache = m;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDateMin() {
		return this.dateMin;
	}
	public void setDateMin(final Date d) {
		this.dateMin = d;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDateMax() {
		return this.dateMax;
	}
	public void setDateMax(final Date d) {
		this.dateMax = d;
	}


	// Relationships ----------------------------------------------------------

	private Explorer	explorer;
	private List<Trip>	trips;


	@OneToOne(optional = false)
	@NotNull
	@Valid
	public Explorer getExplorer() {
		return this.explorer;
	}

	public void setExplorer(final Explorer explorer) {
		this.explorer = explorer;
	}

	@NotNull
	@Valid
	@ManyToMany
	public List<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final List<Trip> trips) {
		this.trips = trips;
	}

}
