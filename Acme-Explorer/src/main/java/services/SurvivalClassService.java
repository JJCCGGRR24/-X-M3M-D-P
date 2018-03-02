
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SurvivalClassRepository;
import security.LoginService;
import domain.Actor;
import domain.Application;
import domain.Explorer;
import domain.SurvivalClass;

@Service
@Transactional
public class SurvivalClassService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SurvivalClassRepository	survivalClassRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService			loginService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public SurvivalClassService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SurvivalClass create() {
		final SurvivalClass r = new SurvivalClass();

		return r;
	}

	public Collection<SurvivalClass> findAll() {
		final Collection<SurvivalClass> res = this.survivalClassRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public SurvivalClass findOne(final int survivalClassId) {
		return this.survivalClassRepository.findOne(survivalClassId);
	}

	public SurvivalClass save(final SurvivalClass survivalClass) {
		Assert.notNull(survivalClass);
		Assert.isTrue(this.checkPrincipal(survivalClass));
		this.detectSpam(survivalClass);
		return this.survivalClassRepository.save(survivalClass);
	}

	public void delete(final SurvivalClass survivalClass) {
		Assert.isTrue(this.checkPrincipal(survivalClass));
		this.survivalClassRepository.delete(survivalClass);
	}

	public void flush() {
		this.survivalClassRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final SurvivalClass obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getTrip().getManager().getUserAccount()))
			res = true;
		return res;
	}

	public SurvivalClass enrolExplorer(final SurvivalClass survival) {
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		final Application a = this.applicationService.getApplicationByExplorer(survival.getTrip(), e);
		final List<Explorer> exps = survival.getExplorers();
		Assert.isTrue(!exps.contains(e));
		Assert.isTrue(a.getStatus().equals("ACCEPTED"));
		final List<Explorer> explorers = survival.getExplorers();
		explorers.add(e);
		survival.setExplorers(explorers);
		final SurvivalClass res = this.survivalClassRepository.save(survival);
		final List<SurvivalClass> classes = e.getSurvivalClasses();
		classes.add(res);
		this.explorerService.save(e);
		return res;
	}
	public SurvivalClass unenrolExplorer(final SurvivalClass survival) {
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		final Application a = this.applicationService.getApplicationByExplorer(survival.getTrip(), e);
		final List<Explorer> exps = survival.getExplorers();
		Assert.isTrue(exps.contains(e));
		Assert.isTrue(a.getStatus().equals("ACCEPTED"));
		final List<Explorer> explorers = survival.getExplorers();
		explorers.remove(e);
		survival.setExplorers(explorers);
		final SurvivalClass res = this.survivalClassRepository.save(survival);
		final List<SurvivalClass> classes = e.getSurvivalClasses();
		classes.remove(res);
		this.explorerService.save(e);
		return res;
	}

	public Collection<SurvivalClass> getSurvivalClassByTrips(final int tripId) {

		return this.survivalClassRepository.getSurvivalClassByTrips(tripId);
	}

	private boolean detectSpam(final SurvivalClass t) {
		boolean res = false;
		for (final String sw : this.configurationService.find().getSpamWords())
			if (t.getDescription().contains(sw) || t.getTitle().contains(sw) || t.getLocation().getName().contains(sw)) {
				res = true;
				final Actor a = t.getTrip().getManager();
				a.setSuspicious(res);
				this.actorService.save(a);
				break;
			}
		return res;
	}

}
