
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Mesage;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RangerRepository		rangerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public RangerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Ranger create() {
		final Ranger r = new Ranger();
		final List<Trip> lt = new ArrayList<>();
		r.setTrips(lt);
		r.setSuspicious(false);

		final Collection<Authority> auths = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority("RANGER");
		auths.add(a);
		final UserAccount ua = new UserAccount();
		ua.setUsername("");
		ua.setPassword("");
		ua.setAuthorities(auths);
		r.setUserAccount(ua);
		r.setCurricula(null);
		r.setSocialIdentities(new ArrayList<SocialIdentity>());

		final List<Mesage> received = new ArrayList<>();
		final List<Mesage> send = new ArrayList<>();
		r.setReceivedMesages(received);
		r.setSendMesages(send);

		return r;
	}

	public Collection<Ranger> findAll() {
		final Collection<Ranger> res = this.rangerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Ranger findOne(final int rangerId) {
		return this.rangerRepository.findOne(rangerId);
	}

	public Ranger save(Ranger ranger) {
		Assert.notNull(ranger);
		if (ranger.getId() > 0)
			Assert.isTrue(this.checkPrincipal(ranger));
		if (ranger.getId() == 0)
			ranger = (Ranger) this.folderService.addFolders(ranger);
		this.detectSpam(ranger);
		return this.rangerRepository.save(ranger);
	}

	//	public void delete(final Ranger ranger) {
	//		this.rangerRepository.delete(ranger);
	//	}

	public void flush() {
		this.rangerRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final Ranger obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getUserAccount()))
			res = true;
		return res;
	}

	public Double queryB4() {
		return this.rangerRepository.queryB4();
	}

	public Double queryB5() {
		return this.rangerRepository.queryB5();
	}

	public Double queryB7() {
		return this.rangerRepository.queryB7();
	}

	public List<Actor> getSuspiciusRanger() {
		return this.rangerRepository.getSuspiciusRanger();
	}

	private boolean detectSpam(final Ranger t) {
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
