
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
import domain.Stage;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StageServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private StageService	stageService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	managerService;


	//Tests

	@Test
	public void testCreate() {

		final Trip trip = ((List<Trip>) this.tripService.findAll()).get(0);

		final Stage s = this.stageService.create(trip);

		Assert.isTrue(s.getTitle() == null && s.getDescription() == null && s.getPrice() == 0.);

	}

	@Test
	public void testSave() {

		final Manager a = ((List<Manager>) this.managerService.findAll()).get(0);
		this.authenticate(a.getUserAccount().getUsername());

		final Trip t = a.getTrips().get(0);

		final Stage result = this.stageService.create(t);

		result.setTitle("El Congo");
		result.setDescription("Grandes llanuras");
		result.setPrice(5.0);

		final Stage saved = this.stageService.save(result);

		final Collection<Stage> stageCollection = this.stageService.findAll();
		Assert.isTrue(stageCollection.contains(saved));
		Assert.isTrue(t.getStages().contains(saved));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		final Manager a = ((List<Manager>) this.managerService.findAll()).get(0);
		this.authenticate(a.getUserAccount().getUsername());

		final Stage s = a.getTrips().get(0).getStages().get(0);

		this.stageService.delete(s);

		final Trip t = s.getTrip();
		final List<Stage> ls = t.getStages();
		ls.remove(s);
		t.setStages(ls);
		this.tripService.save(t);
		final Collection<Stage> Stages = this.stageService.findAll();
		Assert.isTrue(!Stages.contains(s));

		this.unauthenticate();

	}

	@Test
	public void testFindAllStage() {

		super.authenticate("manager");
		final List<Stage> all = (List<Stage>) this.stageService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Stage l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneStage() {

		super.authenticate("manager");
		final List<Stage> all = (List<Stage>) this.stageService.findAll();
		final Stage al = this.stageService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
