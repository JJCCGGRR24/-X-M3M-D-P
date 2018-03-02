
package services;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Manager;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	managerService;


	//Tests
	@Test
	public void testCreateAndGenerateTicker() {

		final Manager m = ((List<Manager>) this.managerService.findAll()).get(0);
		this.authenticate(m.getUserAccount().getUsername());

		final Trip t = this.tripService.create();

		//t.setPublicationDate(new Date(new Date().getTime() - 5000000));
		Assert.isTrue(t.getTitle() == null);
		Assert.isTrue(t.getDescription() == null);
		//		Assert.isTrue(t.getRequirements() == null);

		Assert.isTrue(t.getStarts() == null);
		Assert.isTrue(t.getEnds() == null);
		Assert.isTrue(t.getCancelledReason() == null);
		Assert.isTrue(t.getPrice() == 0.);
		System.out.println("Ticket generado: " + t.getTicker());

		this.unauthenticate();
	}

	@Test
	public void testSave() {

		final Manager a = ((List<Manager>) this.managerService.findAll()).get(0);
		this.authenticate(a.getUserAccount().getUsername());

		final Trip t = a.getTrips().get(0);

		t.setTitle("Viaje a Fernado Po");
		t.setDescription("Un viaje por paisajes que nunca antes habías visto");

		final Trip saved = this.tripService.save(t);
		final Collection<Trip> tripCollection = this.tripService.findAll();
		Assert.isTrue(tripCollection.contains(saved));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		//		final Manager a = ((List<Manager>) this.managerService.findAll()).get(0);
		//		this.authenticate(a.getUserAccount().getUsername());

		final Trip t = this.tripService.findOne(super.getEntityId("trip5"));
		super.authenticate(t.getManager().getUserAccount().getUsername());
		//		final Trip t = a.getTrips().get(0);
		//		System.out.println(a.getId());
		System.out.println(t.getId());
		//		t.setPublicationDate(null);
		this.tripService.delete(t);

		//		final Trip tt = s.
		//		final List<Stage> ls = t.getStages();
		//		ls.remove(s);
		//		t.setStages(ls);
		//this.tripService.save(t);

		final Collection<Trip> tripis = this.tripService.findAll();
		Assert.isTrue(!tripis.contains(t));

		this.unauthenticate();

	}
	@Test
	public void testFindAllTrip() {

		super.authenticate("manager");
		final List<Trip> all = (List<Trip>) this.tripService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Trip l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneTrip() {

		super.authenticate("manager");
		final List<Trip> all = (List<Trip>) this.tripService.findAll();
		final Trip al = this.tripService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

	@Test
	public void testRandomBanner() {
		final Trip trip = ((List<Trip>) this.tripService.findAll()).get(0);
		final String b = this.tripService.randomBanner(trip);
		Assert.notNull(b);
		//		final List<String> banners = new ArrayList<>();
		//		for (final Sponsorship s : sponsorships)
		//			banners.add(s.getBanner());
		//		final String banner = this.tripService.randomBanner(trip);
		//		Assert.isTrue(banners.contains(banner));
	}

	@Test
	public void testPublish() {
		final Trip trip = ((List<Trip>) this.tripService.findAll()).get(0);
		trip.setPublicationDate(null);
		final Trip tripPublished = this.tripService.publish(trip);
		Assert.isTrue(tripPublished.getPublicationDate() != null);
	}

	@Test
	public void testCancellTrip() {
		final Trip trip = this.tripService.findOne(super.getEntityId("trip4"));
		final String cancelledReason = "Va a llover mucho";
		final Trip tripCancelled = this.tripService.cancellTrip(trip, cancelledReason);
		Assert.isTrue(tripCancelled.getCancelledReason() != null);
	}

	@Test
	public void testgetTripsByExplorer() {
		super.authenticate("explorer");
		final List<Trip> trips = (List<Trip>) this.tripService.getTripsByExplorer();
		Assert.isTrue(trips.size() == 2);
		System.out.println("-------------Get Trips by explorer----------");
		System.out.println(trips.get(0) + "\n" + trips.get(1));
	}
}
