
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.LoginService;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService			loginService;


	// Constructors -----------------------------------------------------------
	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Sponsorship create() {
		final Sponsorship r = new Sponsorship();

		final Sponsor sponsor = (Sponsor) this.loginService.getPrincipalActor();
		r.setSponsor(sponsor);

		final List<Sponsorship> sponsorshipsSponsor = sponsor.getSponsorships();
		sponsorshipsSponsor.add(r);
		sponsor.setSponsorships(sponsorshipsSponsor);

		final List<Trip> trips = new ArrayList<>();
		r.setTrips(trips);

		return r;
	}

	public Collection<Sponsorship> findAll() {
		final Collection<Sponsorship> res = this.sponsorshipRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		return this.sponsorshipRepository.findOne(sponsorshipId);
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(this.checkPrincipal(sponsorship));
		return this.sponsorshipRepository.save(sponsorship);
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.isTrue(this.checkPrincipal(sponsorship));
		this.sponsorshipRepository.delete(sponsorship);
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final Sponsorship obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getSponsor().getUserAccount()))
			res = true;
		return res;
	}

}
