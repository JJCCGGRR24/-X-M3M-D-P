
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Access(AccessType.PROPERTY)
public class Story extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String			title;
	private String			text;
	private List<String>	attachments;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@EachURL
	@NotNull
	@ElementCollection
	@Value("#{'${list.of.strings}'.split(',')}")
	public List<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final List<String> attachments) {
		this.attachments = attachments;
	}


	// Relationships ----------------------------------------------------------

	private Explorer	explorer;
	private Trip		trip;


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Explorer getExplorer() {
		return this.explorer;
	}

	public void setExplorer(final Explorer explorer) {
		this.explorer = explorer;
	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
