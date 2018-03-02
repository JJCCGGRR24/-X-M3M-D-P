
package services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Application;
import domain.Explorer;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ApplicationServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private LoginService		loginService;

	@Autowired
	private FolderService		folderService;


	//Tests

	@Test
	public void testCreate() {

		final int id = super.getEntityId("trip3");
		final Trip trip = this.tripService.findOne(id);

		super.authenticate("explorer");
		final Application a = this.applicationService.create(trip);

		Assert.notNull(a.getMoment());
		Assert.notNull(a.getStatus());
		Assert.notNull(a.getExplorer());
		Assert.notNull(a.getComments());
		Assert.notNull(a.getTrip());

		super.authenticate(null);

	}

	@Test
	public void testSaveApplication() {

		final int id = super.getEntityId("trip1");
		final Trip trip = this.tripService.findOne(id);

		super.authenticate("explorer");
		final Application a = this.applicationService.create(trip);

		final String comment2 = "Probando el save";
		String comments = a.getComments();
		comments += comment2;
		a.setComments(comments);

		final Application save = this.applicationService.save(a);

		final Application find = this.applicationService.findOne(save.getId());

		Assert.isTrue(save.equals(find));
		super.authenticate(null);

	}

	//	@Test
	//	public void testDelete() {
	//
	//		super.authenticate("explorer");
	//
	//		final int id = super.getEntityId("application2");
	//		final Application save = this.applicationService.findOne(id);
	//
	//		this.applicationService.delete(save);
	//
	//		final List<Application> applis = (List<Application>) this.applicationService.findAll();
	//		Assert.isTrue(!applis.contains(save));
	//		super.authenticate(null);
	//
	//		System.out.println("---delete----");
	//
	//		for (final Application a : applis)
	//			System.out.println(a);
	//
	//	}

	@Test
	public void testFindAllApplication() {

		super.authenticate("explorer");
		final List<Application> all = (List<Application>) this.applicationService.findAll();
		Assert.isTrue(all.size() == 4);
		super.authenticate(null);

	}

	@Test
	public void testFindOneApplication() {

		super.authenticate("explorer");

		final int id = super.getEntityId("application2");
		final Application a = this.applicationService.findOne(id);

		final int idTrip = super.getEntityId("trip2");
		final int idExplorer = super.getEntityId("explorer1");

		Assert.isTrue(a.getExplorer().getId() == idExplorer && a.getTrip().getId() == idTrip);
		super.authenticate(null);

	}

	@Test
	public void testChangePendingToReject() {

		super.authenticate("manager");

		final int id = super.getEntityId("application3");
		final Application save = this.applicationService.findOne(id);
		final Application appDue = this.applicationService.changePendingToRejectManager(save);

		Assert.isTrue(appDue.getStatus().equals("REJECTED"));
		super.authenticate(null);

	}

	@Test
	public void testChangePendingToDue() {

		super.authenticate("manager");

		final int id = super.getEntityId("application3");
		final Application save = this.applicationService.findOne(id);

		final Application appDue = this.applicationService.changePendingToDueManager(save);

		Assert.isTrue(appDue.getStatus().equals("DUE"));
		super.authenticate(null);

	}

	@Test
	public void testGetApplicationByExplorer() {

		super.authenticate("explorer");

		final int id = super.getEntityId("trip2");
		final Trip trip = this.tripService.findOne(id);

		final Explorer explorer = (Explorer) this.loginService.getPrincipalActor();
		final Application a = this.applicationService.getApplicationByExplorer(trip, explorer);

		Assert.isTrue(a.getTrip().getTicker() == trip.getTicker());
		super.authenticate(null);

	}
	@Test
	public void testCancellApplication() {

		super.authenticate("explorer3");

		final int id = super.getEntityId("trip2");
		final Trip trip = this.tripService.findOne(id);

		final Application a = this.applicationService.cancellApplicationExplorer(trip);

		Assert.isTrue(a.getStatus().equals("CANCELLED"));

		super.authenticate(null);

	}

	//	
}
