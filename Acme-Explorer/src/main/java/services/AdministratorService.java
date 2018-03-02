
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService			actorService;


	// Supporting repositories ----------------------------------------------------
	//	@Autowired
	//	private ActorRepository			actorRepository;

	// Constructors -----------------------------------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Administrator> findAll() {
		final Collection<Administrator> res = this.administratorRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public void flush() {
		this.administratorRepository.flush();
	}

	public Administrator save(final Administrator a) {
		Assert.notNull(a);
		Assert.isTrue(this.checkPrincipal(a));
		return this.administratorRepository.save(a);
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final Administrator a) {
		Assert.isTrue(a.getUserAccount().equals(LoginService.getPrincipal()), "Se esta intentando editar el perfil de otro administrador");
		return true;
	}

	public Actor adminSave(final Actor a) {
		return this.actorService.adminSave(a);
	}

	public Actor ban(final Actor actor) {

		final UserAccount userAccount = actor.getUserAccount();
		final List<Authority> la = new ArrayList<>();
		final Authority a = new Authority();
		a.setAuthority("BANNED");
		la.add(a);
		userAccount.setAuthorities(la);
		actor.setUserAccount(userAccount);

		//return this.actorRepository.save(actor);
		return this.adminSave(actor);

	}

	public Actor unban(final Actor actor) {

		final UserAccount userAccount = actor.getUserAccount();
		final List<Authority> la = new ArrayList<>();

		String s = "";
		switch (actor.getClass().getSimpleName()) {
		case "Manager":
			s = "MANAGER";
			break;
		case "Auditor":
			s = "AUDITOR";

			break;
		case "Ranger":
			s = "RANGER";

			break;
		case "Administrator":
			s = "ADMIN";

			break;
		case "Sponsor":
			s = "SPONSOR";

			break;
		case "Explorer":
			s = "EXPLORER";
			break;
		}
		final Authority a = new Authority();
		a.setAuthority(s);
		la.add(a);
		userAccount.setAuthorities(la);
		actor.setUserAccount(userAccount);

		//return this.actorRepository.save(actor);
		return this.adminSave(actor);

	}

}
