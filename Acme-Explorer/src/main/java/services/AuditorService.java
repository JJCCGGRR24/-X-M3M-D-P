
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.LoginService;
import domain.Auditor;

@Service
@Transactional
public class AuditorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AuditorRepository	auditorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------------
	public AuditorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	/*
	 * public Auditor create() {
	 * Auditor r = new Auditor();
	 * 
	 * r.setSuspicious(false);
	 * 
	 * 
	 * final Authority a = new Authority();
	 * a.setAuthority("AUDITOR");
	 * final UserAccount ua = new UserAccount();
	 * ua.addAuthority(a);
	 * r.setUserAccount(ua);
	 * 
	 * r.setSocialIdentities(new ArrayList<SocialIdentity>());
	 * 
	 * final List<Message> received = new ArrayList<>();
	 * final List<Message> send = new ArrayList<>();
	 * r.setreceivedMessages(received);
	 * r.setSendMessages(send);
	 * 
	 * final List<Audit> audits = new ArrayList<>();
	 * r.setAudits(audits);
	 * final List<Note> notes = new ArrayList<>();
	 * r.setNotes(notes);
	 * 
	 * return r;
	 * }
	 */

	public Collection<Auditor> findAll() {
		final Collection<Auditor> res = this.auditorRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Auditor findOne(final int auditorId) {
		return this.auditorRepository.findOne(auditorId);
	}

	public Auditor save(Auditor auditor) {
		Assert.notNull(auditor);
		if (auditor.getId() > 0)
			Assert.isTrue(this.checkPrincipal(auditor));
		if (auditor.getId() == 0)
			auditor = (Auditor) this.folderService.addFolders(auditor);
		return this.auditorRepository.save(auditor);
	}

	/*
	 * public void delete(final Auditor auditor) {
	 * this.auditorRepository.delete(auditor);
	 * }
	 */

	public void flush() {
		this.auditorRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final Auditor obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getUserAccount()))
			res = true;
		return res;
	}

}
