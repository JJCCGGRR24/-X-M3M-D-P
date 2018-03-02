
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.LoginService;
import domain.Explorer;
import domain.Finder;
import domain.Trip;

@Service
@Transactional
public class FinderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FinderRepository		finderRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationService	configutarionService;


	// Constructors -----------------------------------------------------------
	public FinderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Finder create(final Explorer e) {
		final Finder r = new Finder();
		//final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		r.setExplorer(e);
		final List<Trip> lt = new ArrayList<>();
		r.setTrips(lt);
		//r.setMomentCache(new Date(System.currentTimeMillis() - 1000));
		//e.setFinder(r);
		return r;
	}

	public Collection<Finder> findAll() {
		final Collection<Finder> res = this.finderRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Finder findOne(final int finderId) {
		return this.finderRepository.findOne(finderId);
	}

	public Finder crudSave(final Finder finder) {
		Assert.notNull(finder);
		//		Assert.isTrue(this.checkPrincipal(finder));
		//		if (finder.getVersion() > 0)
		//			finder.setMomentCache(new Date(System.currentTimeMillis() - 1000));
		return this.finderRepository.save(finder);
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(this.checkPrincipal(finder));
		if (finder.getVersion() > 0) {
			finder.setMomentCache(new Date(System.currentTimeMillis() - 1000));
			final List<Trip> trips = this.findTripsByFinder(finder);
			finder.setTrips(trips);
		}
		return this.finderRepository.save(finder);
	}

	//	public void delete(final Finder finder) {
	//		Assert.isTrue(this.checkPrincipal(finder));
	//		this.finderRepository.delete(finder);
	//	}

	public void flush() {
		this.finderRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final Finder finder) {
		Assert.isTrue(LoginService.getPrincipal().equals(finder.getExplorer().getUserAccount()));
		return true;
	}

	@SuppressWarnings("unused")
	public List<Trip> updateCache(final Finder finder) {
		final Double timeCache = this.configutarionService.find().getTimeCache();
		final Date dnow = new Date();
		final Long now = dnow.getTime();

		List<Trip> trips = new ArrayList<>();

		if (finder.getMomentCache() == null || dnow.getTime() - finder.getMomentCache().getTime() > (timeCache * 3600000)) {
			trips = this.findTripsByFinder(finder);
			trips = (trips.subList(0, Math.min(trips.size(), this.configutarionService.find().getNumResults())));
			finder.setMomentCache(new Date(System.currentTimeMillis() - 1000));
			finder.setTrips(trips);
			this.save(finder);
		} else
			trips = finder.getTrips();

		return trips;

	}

	@SuppressWarnings("deprecation")
	public List<Trip> findTripsByFinder(final Finder f) {
		final Finder finder = new Finder();
		finder.setDateMax(f.getDateMax());
		finder.setDateMin(f.getDateMin());
		finder.setKeyword(f.getKeyword());
		finder.setMaximumPrice(f.getMaximumPrice());
		finder.setMinimumPrice(f.getMinimumPrice());
		if (finder.getDateMax() == null) {
			final Date dmax = new Date();
			dmax.setYear(dmax.getYear() + 100);
			finder.setDateMax(dmax);
		}

		if (finder.getDateMin() == null) {
			final Date dmin = new Date();
			dmin.setYear(dmin.getYear() - 100);
			finder.setDateMin(dmin);
		}

		if (finder.getKeyword() == null)
			finder.setKeyword("");

		if (finder.getMaximumPrice() == null)
			finder.setMaximumPrice(Double.MAX_VALUE);

		if (finder.getMinimumPrice() == null)
			finder.setMinimumPrice(0.0);

		return this.finderRepository.finderAllFields(finder.getMaximumPrice(), finder.getMinimumPrice(), finder.getKeyword(), finder.getDateMin(), finder.getDateMax());
	}

	public String validate(final Finder f) {
		String s = null;
		if (f.getMinimumPrice() != 0. && f.getMaximumPrice() != 0. && f.getMinimumPrice() > f.getMaximumPrice())
			s = "price.reverse";
		if (f.getDateMax() != null && f.getDateMin() != null && f.getDateMin().after(f.getDateMax()))
			s = "date.reverse";
		return s;
	}
}
