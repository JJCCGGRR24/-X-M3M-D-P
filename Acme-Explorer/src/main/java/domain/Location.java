
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	// Attributes -------------------------------------------------------------
	private String	name;
	private double	latitude;
	private double	altitude;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Range(min = -90, max = 90)
	@NotNull
	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	@Range(min = -90, max = 90)
	@NotNull
	public double getAltitude() {
		return this.altitude;
	}

	public void setAltitude(final double altitude) {
		this.altitude = altitude;
	}
}
