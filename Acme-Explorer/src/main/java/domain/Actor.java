/*
 * Actor.java
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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	address;
	private boolean	suspicious;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	public boolean getSuspicious() {
		return this.suspicious;
	}

	public void setSuspicious(final boolean suspicious) {
		this.suspicious = suspicious;
	}


	// Relationships ----------------------------------------------------------

	private UserAccount				userAccount;
	private List<Mesage>			sendMesages;
	private List<Mesage>			receivedMesages;
	private List<Folder>			folders;
	private List<SocialIdentity>	socialIdentities;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public List<Mesage> getSendMesages() {
		return this.sendMesages;
	}

	public void setSendMesages(final List<Mesage> r) {
		this.sendMesages = r;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "recipient")
	public List<Mesage> getReceivedMesages() {
		return this.receivedMesages;
	}

	public void setReceivedMesages(final List<Mesage> r) {
		this.receivedMesages = r;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
	public List<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final List<Folder> folders) {
		this.folders = folders;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "actor")
	public List<SocialIdentity> getSocialIdentities() {
		return this.socialIdentities;
	}

	public void setSocialIdentities(final List<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

}
