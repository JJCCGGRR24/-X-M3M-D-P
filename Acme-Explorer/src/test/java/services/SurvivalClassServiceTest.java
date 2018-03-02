
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
import domain.SurvivalClass;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SurvivalClassServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ManagerService			managerService;


	//Tests

	@Test
	public void testCreate() {

		final Trip trip = ((List<Trip>) this.tripService.findAll()).get(0);

		final SurvivalClass sc = this.survivalClassService.create();

		Assert.isTrue(sc.getTitle() == null && sc.getDescription() == null && sc.getDate() == null && sc.getLocation() == null);

	}

	@Test
	public void testSave() {

		final Manager m = ((List<Manager>) this.managerService.findAll()).get(0);
		this.authenticate(m.getUserAccount().getUsername());

		final SurvivalClass result = m.getTrips().get(0).getSurvivalClasses().get(0);

		result.setTitle("Supervivencia");

		result.setDescription("Aprende a sobrevivir en un ambiente hostil");

		final SurvivalClass saved = this.survivalClassService.save(result);

		final Collection<SurvivalClass> sc = this.survivalClassService.findAll();
		Assert.isTrue(sc.contains(saved));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		final Manager m = ((List<Manager>) this.managerService.findAll()).get(0);
		this.authenticate(m.getUserAccount().getUsername());

		final SurvivalClass result = m.getTrips().get(0).getSurvivalClasses().get(0);

		this.survivalClassService.delete(result);
		final Collection<SurvivalClass> sc = this.survivalClassService.findAll();
		Assert.isTrue(!sc.contains(result));

		this.unauthenticate();

	}

	@Test
	public void testFindAllSurvivalClass() {

		super.authenticate("manager");
		final List<SurvivalClass> all = (List<SurvivalClass>) this.survivalClassService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final SurvivalClass l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneSurvivalClass() {

		super.authenticate("manager");
		final List<SurvivalClass> all = (List<SurvivalClass>) this.survivalClassService.findAll();
		final SurvivalClass al = this.survivalClassService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
