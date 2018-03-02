
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorRepository	sponsorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------------
	public SponsorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	//	public Sponsor create() {
	//		final Sponsor r = new Sponsor();
	//
	//		r.setSuspicious(false);
	//
	//		final Authority a = new Authority();
	//		a.setAuthority("SPONSOR");
	//		final UserAccount ua = new UserAccount();
	//		ua.addAuthority(a);
	//		r.setUserAccount(ua);
	//
	//		r.setSocialIdentities(new ArrayList<SocialIdentity>());
	//
	//		final List<Message> received = new ArrayList<>();
	//		final List<Message> send = new ArrayList<>();
	//		r.setReceivedMessages(received);
	//		r.setSendMessages(send);
	//
	//		final List<Sponsorship> sponsorships = new ArrayList<>();
	//		r.setSponsorships(sponsorships);
	//
	//		return r;
	//	}

	public Collection<Sponsor> findAll() {
		final Collection<Sponsor> res = this.sponsorRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Sponsor findOne(final int sponsorId) {
		return this.sponsorRepository.findOne(sponsorId);
	}

	public Sponsor save(Sponsor sponsor) {
		Assert.notNull(sponsor);
		if (sponsor.getId() > 0)
			Assert.isTrue(this.checkPrincipal(sponsor));
		if (sponsor.getId() == 0)
			sponsor = (Sponsor) this.folderService.addFolders(sponsor);
		return this.sponsorRepository.save(sponsor);
	}

	//	public void delete(final Sponsor sponsor) {
	//		this.sponsorRepository.delete(sponsor);
	//	}

	public void flush() {
		this.sponsorRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final Sponsor obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getUserAccount()))
			res = true;
		return res;
	}

}
