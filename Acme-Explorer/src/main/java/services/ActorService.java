
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActorRepository	actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService	rangerService;

	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll() {
		final Collection<Actor> res = this.actorRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public void flush() {
		this.actorRepository.flush();
	}

	public Actor save(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(this.checkPrincipal(a));
		return this.actorRepository.save(a);
	}

	// Other business methods -------------------------------------------------

	//		public Authority getAuthority(final Actor actor) {
	//			final UserAccount userAccount = actor.getUserAccount();
	//			final List<Authority> auts = (List<Authority>) userAccount.getAuthorities();
	//			return auts.get(0);
	//		}

	public Actor adminSave(final Actor a) {
		return this.actorRepository.save(a);
	}
	public Boolean checkPrincipal(final Actor a) {
		Boolean res = false;
		if ((a.getUserAccount().equals(LoginService.getPrincipal())))
			res = true;
		return res;
	}

	public Actor findByUserAccount(final UserAccount u) {
		return this.actorRepository.findByUsername(u.getUsername());
	}

	public List<Actor> getSuspiciusRangerAndManager() {
		final List<Actor> res = new ArrayList<>();
		final List<Actor> rangs = this.rangerService.getSuspiciusRanger();
		res.addAll(rangs);
		final List<Actor> mans = this.managerService.getSuspiciusManager();
		res.addAll(mans);
		return res;
	}

	public boolean exist(final String username) {
		boolean r = false;
		if (this.actorRepository.findByUsername(username) != null)
			r = true;
		return r;
	}

}
