
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Nulp extends DomainEntity {

	//Attributes
	private String	code;
	private int		gauge;
	private Date	moment;
	private String	shortTitle;
	private String	description;


	@NotBlank
	public String getShortTitle() {
		return this.shortTitle;
	}

	public void setShortTitle(final String shortTitle) {
		this.shortTitle = shortTitle;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Column(unique = true)
	@NotBlank
	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	@NotNull
	@Range(min = 1, max = 3)
	public int getGauge() {
		return this.gauge;
	}

	public void setGauge(final int gauge) {
		this.gauge = gauge;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	//Relationship

	//	private Manager	manager;
	private Trip	trip;


	//	@NotNull
	//	@Valid
	//	@ManyToOne(optional = false)
	//	public Manager getManager() {
	//		return this.manager;
	//	}
	//
	//	public void setManager(final Manager manager) {
	//		this.manager = manager;
	//	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
