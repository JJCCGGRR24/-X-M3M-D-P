/*
 * w * Question.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;
	private boolean	modifiable;


	//	@Transient
	//	@AssertTrue(message="Only system folders can be named Inbox, Outbox, Trashbox or Spambox")
	//	public boolean isValid() {
	//		boolean res = false;
	//		String low = this.name.toLowerCase();
	//		boolean reservedName = (low.equals("inbox"))||(low.equals("outbox"))||(low.equals("trashbox"))||(low.equals("spambox"));
	//		if (reservedName) {
	//			res = this.modifiable==true;
	//		} else {
	//			res = this.modifiable==false;
	//		}
	//		return res;
	//	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getModifiable() {
		return this.modifiable;
	}

	public void setModifiable(final boolean modifiable) {
		this.modifiable = modifiable;
	}


	// Relationships -------------------------------------------------------------
	private Actor			actor;
	private List<Mesage>	mesages;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@NotNull
	@OneToMany(mappedBy = "folder")
	public List<Mesage> getMesages() {
		return this.mesages;
	}

	public void setMesages(final List<Mesage> messages) {
		this.mesages = messages;
	}
}
