
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Manager;
import domain.Mesage;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class ManagerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ManagerRepository		managerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public ManagerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Manager create() {
		final Manager r = new Manager();

		r.setSuspicious(false);

		final Authority a = new Authority();
		a.setAuthority("MANAGER");
		final UserAccount ua = new UserAccount();
		ua.addAuthority(a);
		r.setUserAccount(ua);

		r.setSocialIdentities(new ArrayList<SocialIdentity>());

		final List<Mesage> received = new ArrayList<>();
		final List<Mesage> send = new ArrayList<>();
		r.setReceivedMesages(received);
		r.setSendMesages(send);

		final List<Trip> trips = new ArrayList<>();
		r.setTrips(trips);

		return r;
	}

	public Collection<Manager> findAll() {
		final Collection<Manager> res = this.managerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Manager findOne(final int managerId) {
		return this.managerRepository.findOne(managerId);
	}

	public Manager save(Manager manager) {
		Assert.notNull(manager);
		if (manager.getId() > 0)
			Assert.isTrue(this.checkPrincipal(manager));
		if (manager.getId() == 0)
			manager = (Manager) this.folderService.addFolders(manager);

		this.detectSpam(manager);
		return this.managerRepository.save(manager);
	}

	//	public void delete(final Manager manager) {
	//		this.managerRepository.delete(manager);
	//	}

	public void flush() {
		this.managerRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Double queryB6() {
		return this.managerRepository.queryB6();
	}

	public boolean checkPrincipal(final Manager obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getUserAccount()))
			res = true;
		return res;
	}

	public List<Application> getApplications(final Manager manager) {
		return this.managerRepository.getApplications(manager);
	}
	public List<Application> getSurvivalClasses(final Manager manager) {
		return this.managerRepository.getSurvivalClasses(manager);
	}

	public List<Actor> getSuspiciusManager() {
		return this.managerRepository.getSuspiciusManager();
	}

	public List<Actor> getTripsByManager(final Manager m) {
		return this.managerRepository.getTripsByManager(m);
	}

	private boolean detectSpam(final Manager t) {
		boolean res = false;
		for (final String sw : this.configurationService.find().getSpamWords())
			if (t.getAddress().contains(sw) || t.getEmail().contains(sw) || t.getName().contains(sw) || t.getSurname().contains(sw)) {
				res = true;
				t.setSuspicious(res);
				this.actorService.save(t);
				break;
			}
		return res;
	}

}
