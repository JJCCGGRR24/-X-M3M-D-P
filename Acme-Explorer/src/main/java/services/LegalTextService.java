
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.LegalText;
import domain.Trip;

@Service
@Transactional
public class LegalTextService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private LegalTextRepository	legalTextRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public LegalTextService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public LegalText create() {
		final LegalText r = new LegalText();
		r.setFinalMode(false);
		r.setLaws(new ArrayList<String>());
		r.setMoment(new Date(System.currentTimeMillis() - 1000));
		r.setTrips(new ArrayList<Trip>());
		return r;
	}

	public Collection<LegalText> findAll() {
		final Collection<LegalText> res = this.legalTextRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public LegalText findOne(final int legalTextId) {
		return this.legalTextRepository.findOne(legalTextId);
	}

	public LegalText save(final LegalText legalText) {
		Assert.notNull(legalText);
		legalText.setMoment(new Date(System.currentTimeMillis() - 1000));
		//		Assert.isTrue(this.checkPrincipal(legalText));
		//		Assert.isTrue(legalText.getFinalMode() == false);
		final List<String> l = new ArrayList<>();
		for (final String a : legalText.getLaws())
			if (a.trim().length() == 0)
				l.add(a);
		legalText.getLaws().removeAll(l);
		return this.legalTextRepository.save(legalText);
	}

	public void delete(final LegalText legalText) {
		Assert.isTrue(this.checkPrincipal(legalText));
		Assert.isTrue(legalText.getFinalMode() == false);
		this.legalTextRepository.delete(legalText);
	}

	public void flush() {
		this.legalTextRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Trip tripByLegalText(final String NameLegalText) {
		return this.legalTextRepository.tripByLegalText(NameLegalText);
	}

	public Boolean checkPrincipal(final LegalText l) {
		//		Admins pueden guardar LT sin asociarlos a Trips
		//		final Trip t = this.tripByLegalText(l.getTitle());
		//		Assert.isTrue(LoginService.getPrincipal().equals(t.getManager().getUserAccount()));
		return true;
	}

	public LegalText publish(final LegalText legalText) {
		Assert.notNull(legalText);
		Assert.isTrue(legalText.getFinalMode() == false);
		legalText.setFinalMode(true);
		legalText.setMoment(new Date());
		return this.legalTextRepository.save(legalText);
	}

	public Collection<LegalText> getLegalTextsFinalMode() {
		return this.legalTextRepository.getLegalTextsFinalMode();
	}

	public LegalText doFinalMode(final LegalText legalText) {
		Assert.isTrue(legalText.getFinalMode() == false);
		legalText.setFinalMode(true);
		return this.save(legalText);
	}

	public List<Object[]> queryC11() {
		return this.legalTextRepository.queryC11();
	}

}
