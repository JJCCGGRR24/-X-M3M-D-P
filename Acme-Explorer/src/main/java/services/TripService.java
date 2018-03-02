
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TripRepository;
import security.LoginService;
import domain.Actor;
import domain.Application;
import domain.Audit;
import domain.Auditor;
import domain.Explorer;
import domain.Finder;
import domain.Manager;
import domain.Note;
import domain.Nulp;
import domain.Sponsorship;
import domain.Stage;
import domain.Story;
import domain.SurvivalClass;
import domain.Tag;
import domain.Trip;

@Service
@Transactional
public class TripService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TripRepository			tripRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService			loginService;

	@Autowired
	private TagService				tagService;

	@Autowired
	private NulpService				nulpService;

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private StoryService			storyService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------
	public TripService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Trip create() {
		final Trip r = new Trip();
		r.setTicker(this.generateTicker());
		final List<Application> applications = new ArrayList<>();
		r.setApplications(applications);
		final List<Audit> a = new ArrayList<>();
		r.setAudits(a);
		final List<Finder> finders = new ArrayList<>();
		r.setFinders(finders);
		final List<Note> notes = new ArrayList<>();
		r.setNotes(notes);
		final List<String> requirements = new ArrayList<>();
		r.setRequirements(requirements);
		final List<Tag> tags = new ArrayList<>();
		r.setTags(tags);
		final List<SurvivalClass> survivalClasses = new ArrayList<>();
		r.setSurvivalClasses(survivalClasses);
		final List<Story> stories = new ArrayList<>();
		r.setStories(stories);
		final List<Stage> stages = new ArrayList<>();
		r.setStages(stages);
		final List<Sponsorship> sponsorships = new ArrayList<>();
		r.setSponsorships(sponsorships);

		r.setPublicationDate(null);
		final Manager manager = (Manager) this.loginService.getPrincipalActor();
		final List<Trip> tripsManager = manager.getTrips();
		tripsManager.add(r);
		manager.setTrips(tripsManager);
		r.setManager(manager);

		//For exam
		final List<Nulp> nulps = new ArrayList<>();
		r.setNulps(nulps);

		return r;
	}

	public Collection<Trip> findAll() {
		final Collection<Trip> res = this.tripRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Trip findOne(final int tripId) {
		return this.tripRepository.findOne(tripId);
	}

	public Trip save(final Trip trip) {
		//		Assert.notNull(trip);
		//		//		Assert.isTrue(trip.getPublicationDate() == null);
		//		Assert.isTrue(this.checkPrincipal(trip));
		//		trip.setPublicationDate(new Date(System.currentTimeMillis() - 1000));
		//		Double price = this.tripRepository.calculatePrice(trip);
		//		if (price == null)
		//			price = 0.;
		//		trip.setPrice(price * (1 + this.configurationService.find().getTaxVAT()));
		//		return this.tripRepository.save(trip);
		Assert.notNull(trip);
		//    Assert.isTrue(trip.getPublicationDate() == null);
		Assert.isTrue(this.checkPrincipal(trip));
		if ((trip.getStages()).isEmpty())
			trip.setPrice(0.0);
		else
			trip.setPrice(this.tripRepository.calculatePrice(trip) * (1.0 + this.configurationService.find().getTaxVAT()));
		this.detectSpam(trip);
		return this.tripRepository.save(trip);
	}

	public void delete(final Trip trip) {
		Assert.isNull(trip.getPublicationDate());
		Assert.isTrue(this.checkPrincipal(trip));
		for (final Application a : trip.getApplications())
			this.applicationService.delete(a);
		for (final Finder f : trip.getFinders()) {
			f.getTrips().remove(trip);
			this.finderService.crudSave(f);
		}
		for (final Tag f : trip.getTags()) {
			f.getTrips().remove(trip);
			this.tagService.save(f);
		}
		//		for exam
		for (final Nulp f : trip.getNulps())
			this.nulpService.delete(f);

		for (final SurvivalClass f : trip.getSurvivalClasses())
			this.survivalClassService.delete(f);

		for (final Story f : trip.getStories())
			this.storyService.crudDelete(f);
		trip.setFinders(new ArrayList<Finder>());
		this.tripRepository.delete(trip);
	}
	public void flush() {
		this.tripRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final Trip obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getManager().getUserAccount()))
			res = true;
		return res;
	}

	public Collection<Trip> getTripsByManager() {
		final Manager m = (Manager) this.loginService.getPrincipalActor();
		return this.tripRepository.getTripsByManager(m.getId());
	}

	public String randomBanner(final Trip trip) {
		String s = "";
		if (trip.getSponsorships() != null && trip.getSponsorships().size() > 0) {
			final List<Sponsorship> sponsorships = trip.getSponsorships();
			final Sponsorship sp = sponsorships.get((int) (Math.random() * sponsorships.size()));
			s = sp.getBanner();
		}

		return s;
	}

	public Collection<Trip> search(final String search) {
		return this.tripRepository.search(search);

	}

	public Trip publish(final Trip trip) {

		Assert.notNull(trip);
		Assert.isTrue(trip.getPublicationDate() == null);
		trip.setPublicationDate(new Date(System.currentTimeMillis() - 1000));
		return this.tripRepository.save(trip);
	}

	//Generador de ticker
	@SuppressWarnings("deprecation")
	public String generateTicker() {
		final Date date = new Date();
		final Integer s1 = date.getDate();
		String day = s1.toString();
		if (day.length() == 1)
			day = "0" + day;
		final Integer s2 = date.getMonth() + 1;
		String month = s2.toString();
		if (month.length() == 1)
			month = "0" + month;
		final Integer s3 = date.getYear();
		final String year = s3.toString().substring(1);

		return year + month + day + "-" + TripService.generateStringAux();
	}

	private static String generateStringAux() {
		final int length = 4;
		final String characters = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < 4; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public List<Double> queryC2() {
		final List<Double> l = new ArrayList<Double>();
		final Object[] o = this.tripRepository.queryC2();
		l.add((Double) o[0]);
		l.add((Double) o[1]);
		l.add((Double) o[2]);
		l.add((Double) o[3]);
		return l;
	}

	public List<Double> queryC3() {
		final List<Double> l = new ArrayList<Double>();
		final Object[] o = this.tripRepository.queryC3();
		l.add((Double) o[0]);
		l.add((Double) o[1]);
		l.add((Double) o[2]);
		l.add((Double) o[3]);
		return l;
	}

	public List<Double> queryC4() {
		final List<Double> l = new ArrayList<Double>();
		final Object[] o = this.tripRepository.queryC4();
		l.add((Double) o[0]);
		l.add((Double) o[1]);
		l.add((Double) o[2]);
		l.add((Double) o[3]);
		return l;
	}

	public Double queryC9() {
		return this.tripRepository.queryC9();
	}

	public List<Trip> queryC10() {
		return this.tripRepository.queryC10();
	}

	public Double queryB3() {
		return this.tripRepository.queryB3();
	}

	public Trip cancellTrip(final Trip trip, final String cancelledReason) {
		//		final Collection<Trip> trips = this.tripRepository.cancellableTrips();
		//		Assert.isTrue(trips.contains(trip));
		Assert.isTrue(!trip.getStarts().before(new Date()));
		Assert.isTrue(!trip.getPublicationDate().after(new Date()));
		trip.setCancelledReason(cancelledReason);
		return this.tripRepository.save(trip);
	}

	public List<Trip> getTripsByAuditor(final Actor a) {
		return this.tripRepository.getTripsByAuditor(a.getId());
	}

	public Collection<Trip> getTripsVisible() {
		final Date dateNow = new Date();
		return this.tripRepository.getTripsVisible(dateNow);
	}

	public List<String> randomBannerList(final List<Trip> l) {
		final List<String> s = new ArrayList<String>();
		for (final Trip t : l)
			s.add(this.randomBanner(t));
		return s;
	}

	public Collection<Trip> getTripsByExplorer() {
		final Explorer explorer = (Explorer) this.loginService.getPrincipalActor();
		final Collection<Application> aplications = explorer.getApplications();
		final Collection<Trip> trips = new ArrayList<Trip>();
		for (final Application a : aplications)
			trips.add(a.getTrip());
		return trips;
	}

	public Collection<Trip> getTripsByAuditor() {
		final Auditor auditor = (Auditor) this.loginService.getPrincipalActor();
		final Collection<Audit> audits = auditor.getAudits();
		final Collection<Trip> trips = new ArrayList<Trip>();
		for (final Audit a : audits)
			trips.add(a.getTrip());
		return trips;
	}

	public String validate(final Trip f) {
		String s = null;
		if (f.getStarts() != null && f.getEnds() != null && f.getStarts().after(f.getEnds()))
			s = "date.reverse";
		return s;
	}

	private boolean detectSpam(final Trip t) {
		boolean res = false;
		for (final String sw : this.configurationService.find().getSpamWords()) {
			if (t.getCancelledReason() != null)
				if (t.getCancelledReason().contains(sw)) {
					res = true;
					final Actor a = t.getManager();
					a.setSuspicious(res);
					this.actorService.save(a);
					break;
				}

			if (t.getDescription().contains(sw) || t.getTitle().contains(sw)) {
				res = true;
				final Actor a = t.getManager();
				a.setSuspicious(res);
				this.actorService.save(a);
				break;
			} else if (!t.getRequirements().isEmpty())
				for (final String req : t.getRequirements())
					if (req.contains(sw)) {
						res = true;
						final Actor a = t.getManager();
						a.setSuspicious(res);
						this.actorService.save(a);
						break;
					}
		}
		return res;
	}
}
