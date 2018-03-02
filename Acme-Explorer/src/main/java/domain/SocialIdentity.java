/*
 * Question.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	link;
	private String	network;
	private String	photo;
	private String	nick;


	@URL
	@NotBlank
	public String getLink() {
		return this.link;

	}

	public void setLink(final String link) {
		this.link = link;
	}

	@NotBlank
	public String getNetwork() {
		return this.network;
	}

	public void setNetwork(final String network) {
		this.network = network;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String picture) {
		this.photo = picture;
	}

	@NotBlank
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}


	// Relationships ----------------------------------------------------------
	private Actor	actor;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
