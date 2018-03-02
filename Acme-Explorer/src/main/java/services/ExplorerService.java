
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Contact;
import domain.Explorer;
import domain.Finder;
import domain.Mesage;
import domain.SocialIdentity;
import domain.Story;
import domain.Trip;

@Service
@Transactional
public class ExplorerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ExplorerRepository	explorerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FolderService		folderService;

	@Autowired
	private FinderService		finderService;


	// Constructors -----------------------------------------------------------
	public ExplorerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Explorer create() {
		final Explorer r = new Explorer();

		final List<Application> la = new ArrayList<>();
		final List<Contact> lc = new ArrayList<>();
		final List<Story> ls = new ArrayList<>();
		r.setApplications(la);
		r.setContacts(lc);
		r.setStories(ls);
		r.setSuspicious(false);

		final Authority a = new Authority();
		a.setAuthority("EXPLORER");
		final UserAccount ua = new UserAccount();
		ua.addAuthority(a);
		r.setUserAccount(ua);

		r.setSocialIdentities(new ArrayList<SocialIdentity>());

		final List<Mesage> received = new ArrayList<>();
		final List<Mesage> send = new ArrayList<>();
		r.setReceivedMesages(received);
		r.setSendMesages(send);

		final Finder finder = this.finderService.create(r);
		r.setFinder(finder);
		finder.setExplorer(r);
		return r;
	}

	public Collection<Explorer> findAll() {
		final Collection<Explorer> res = this.explorerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Explorer findOne(final int explorerId) {
		return this.explorerRepository.findOne(explorerId);
	}

	public Explorer save(Explorer explorer) {
		Assert.notNull(explorer);
		if (explorer.getId() == 0) {
			explorer = (Explorer) this.folderService.addFolders(explorer);
			explorer.setFinder(this.finderService.create(explorer));
		} else
			Assert.isTrue(this.checkPrincipal(explorer));
		final Explorer e = this.explorerRepository.save(explorer);
		return e;
	}

	//	
	//	public void delete(final Explorer explorer) {
	//		this.explorerRepository.delete(explorer);
	//	}

	public void flush() {
		this.explorerRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final Explorer obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getUserAccount()))
			res = true;
		return res;
	}

	public List<Application> getSurvivalClassesApplyAccepted(final Explorer explorer) {
		return this.explorerRepository.getSurvivalClassesApplyAccepted(explorer, new Date());
	}

	public List<Trip> getTripsWithApplicationAccepted(final Explorer explorer) {
		return this.explorerRepository.getTripsWithApplicationAccepted(explorer);
	}
}
