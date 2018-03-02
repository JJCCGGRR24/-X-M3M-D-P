
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StageRepository;
import security.LoginService;
import domain.Actor;
import domain.Stage;
import domain.Trip;

@Service
@Transactional
public class StageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private StageRepository			stageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private TripService				tripService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------
	public StageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Stage create(final Trip trip) {

		final Stage r = new Stage();
		r.setTrip(trip);
		//		final List<Stage> stagesTrip = trip.getStages();
		//		stagesTrip.add(r);
		//		trip.setStages(stagesTrip);

		return r;
	}

	public Collection<Stage> findAll() {
		final Collection<Stage> res = this.stageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Stage findOne(final int stageId) {
		return this.stageRepository.findOne(stageId);
	}

	public Stage save(final Stage stage) {
		Assert.notNull(stage);
		Assert.isTrue(this.checkPrincipal(stage));
		this.detectSpam(stage);
		return this.stageRepository.save(stage);
	}

	public void delete(final Stage stage) {
		Assert.isTrue(this.checkPrincipal(stage));
		this.stageRepository.delete(stage);
	}

	public void flush() {
		this.stageRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final Stage obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getTrip().getManager().getUserAccount()))
			res = true;
		return res;
	}

	public Collection<Stage> stagesByTrip(final int tripId) {
		final Trip trip = this.tripService.findOne(tripId);
		return this.stageRepository.stagesByTrip(trip);
	}

	private boolean detectSpam(final Stage stage) {
		boolean res = false;
		for (final String sw : this.configurationService.find().getSpamWords())
			if (stage.getDescription().contains(sw) || stage.getTitle().contains(sw)) {
				res = true;
				final Actor a = stage.getTrip().getManager();
				a.setSuspicious(res);
				this.actorService.save(a);
				break;
			}
		return res;
	}

}
