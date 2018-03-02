
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ContactRepository;
import security.LoginService;
import domain.Contact;
import domain.Explorer;

@Service
@Transactional
public class ContactService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ContactRepository	contactRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService		loginService;


	// Constructors -----------------------------------------------------------
	public ContactService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Contact create() {
		final Contact r = new Contact();
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		r.setExplorer(e);
		final List<Contact> contacts = e.getContacts();
		contacts.add(r);
		e.setContacts(contacts);
		return r;
	}
	public Collection<Contact> findAll() {
		final Collection<Contact> res = this.contactRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Contact findOne(final int contactId) {
		return this.contactRepository.findOne(contactId);
	}

	public Contact save(final Contact contact) {
		Assert.notNull(contact);
		Assert.isTrue(this.checkPrincipal(contact));
		return this.contactRepository.save(contact);
	}

	public void delete(final Contact contact) {
		Assert.isTrue(this.checkPrincipal(contact));
		this.contactRepository.delete(contact);
	}

	public void flush() {
		this.contactRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final Contact c) {
		Assert.isTrue(LoginService.getPrincipal().equals(c.getExplorer().getUserAccount()));
		return true;
	}

	public List<Contact> getContact() {
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		return this.contactRepository.getContact(e.getId());
	}

}
