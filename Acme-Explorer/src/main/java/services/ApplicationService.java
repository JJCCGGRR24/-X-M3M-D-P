
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Actor;
import domain.Application;
import domain.Explorer;
import domain.Trip;

@Service
@Transactional
public class ApplicationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService			loginService;

	@Autowired
	private MesageService			messageService;


	// Constructors -----------------------------------------------------------
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Application create(final Trip trip) {
		final Application r = new Application();
		r.setComments("");
		r.setExplorer((Explorer) this.loginService.getPrincipalActor());
		r.setMoment(new Date(System.currentTimeMillis() - 1000));
		r.setStatus("PENDING");
		r.setTrip(trip);
		final List<Application> applications = trip.getApplications();
		applications.add(r);
		trip.setApplications(applications);
		return r;
	}

	public Collection<Application> findAll() {
		final Collection<Application> res = this.applicationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(this.checkPrincipal(application));
		application.setMoment(new Date(System.currentTimeMillis() - 1000));

		return this.applicationRepository.save(application);
	}
	public void delete(final Application application) {
		//		Assert.isTrue(this.checkPrincipal(application));
		this.applicationRepository.delete(application);
	}

	public void flush() {
		this.applicationRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final Application appli) {
		Boolean res = false;
		Assert.isTrue(appli.getExplorer().getUserAccount().equals(LoginService.getPrincipal()));
		res = true;
		return res;
	}

	public Application changePendingToRejectManager(final Application application) {

		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isTrue(application.getTrip().getManager().equals(this.loginService.getPrincipalActor()));
		application.setStatus("REJECTED");

		return this.applicationRepository.save(application);
	}

	public void NotifyExplorer(final Application apli) {
		final Actor explorer = apli.getExplorer();
		this.messageService.sendNotificationBox(explorer);

	}
	public void NotifyManager(final Application apli) {
		final Actor manager = apli.getTrip().getManager();
		this.messageService.sendNotificationBox(manager);

	}
	public Application changePendingToDueManager(final Application application) {

		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isTrue(application.getTrip().getManager().equals(this.loginService.getPrincipalActor()));

		application.setStatus("DUE");

		return this.applicationRepository.save(application);
	}

	public List<Double> queryC1() {
		final List<Double> l = new ArrayList<Double>();
		final Object[] o = this.applicationRepository.queryC1();
		l.add((Double) o[0]);
		l.add((Double) o[1]);
		l.add((Double) o[2]);
		l.add((Double) o[3]);
		return l;
	}

	public Double queryC5() {
		return this.applicationRepository.queryC5();
	}

	public Double queryC6() {
		return this.applicationRepository.queryC6();
	}

	public Double queryC7() {
		return this.applicationRepository.queryC7();
	}

	public Double queryC8() {
		return this.applicationRepository.queryC8();
	}

	public Application getApplicationByExplorer(final Trip trip, final Explorer explorer) {
		return this.applicationRepository.getApplicationByExplorer(trip, explorer);
	}

	public Application cancellApplicationExplorer(final Trip trip) {
		final Explorer explorer = (Explorer) this.loginService.getPrincipalActor();
		final Application a = this.getApplicationByExplorer(trip, explorer);
		Assert.isTrue(a.getStatus().equals("ACCEPTED"));
		Assert.isTrue(trip.getStarts().after(new Date(System.currentTimeMillis())));
		a.setStatus("CANCELLED");

		return this.applicationRepository.save(a);

	}

	public Application acceptApplicationExplorer(final Trip trip) {
		final Explorer explorer = (Explorer) this.loginService.getPrincipalActor();
		final Application a = this.getApplicationByExplorer(trip, explorer);
		Assert.isTrue(a.getStatus().equals("DUE"));
		Assert.isTrue(trip.getStarts().after(new Date(System.currentTimeMillis())));
		a.setStatus("ACCEPTED");

		return this.applicationRepository.save(a);

	}

}
