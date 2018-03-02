
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import security.LoginService;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService				loginService;


	// Constructors -----------------------------------------------------------
	public SocialIdentityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SocialIdentity create() {
		final SocialIdentity r = new SocialIdentity();

		final Actor actor = this.loginService.getPrincipalActor();
		final List<SocialIdentity> socialIdentitiesActor = actor.getSocialIdentities();
		socialIdentitiesActor.add(r);
		actor.setSocialIdentities(socialIdentitiesActor);

		r.setActor(actor);

		return r;
	}

	public Collection<SocialIdentity> findAll() {
		final Collection<SocialIdentity> res = this.socialIdentityRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public SocialIdentity findOne(final int socialIdentityId) {
		return this.socialIdentityRepository.findOne(socialIdentityId);
	}

	public SocialIdentity save(final SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		Assert.isTrue(this.checkPrincipal(socialIdentity));
		return this.socialIdentityRepository.save(socialIdentity);
	}

	public void delete(final SocialIdentity socialIdentity) {
		Assert.isTrue(this.checkPrincipal(socialIdentity));
		this.socialIdentityRepository.delete(socialIdentity);
	}

	public void flush() {
		this.socialIdentityRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final SocialIdentity obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getActor().getUserAccount()))
			res = true;
		return res;
	}

	public List<SocialIdentity> socialId() {
		final Actor a = this.loginService.getPrincipalActor();
		return this.socialIdentityRepository.socialId(a.getId());
	}

}
