
package services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
//@TransactionConfiguration(defaultRollback = true)
public class QueriesServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private LegalTextService	legalText;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private NoteService			noteService;

	@Autowired
	private RangerService		rangerService;
	@Autowired
	private TripService			tripService;


	//Tests

	@Test
	public void testQueries() {

		final List<Double> queryC1 = this.applicationService.queryC1();
		Assert.notEmpty(queryC1);

		final List<Double> queryC2 = this.tripService.queryC2();
		Assert.notEmpty(queryC2);

		final List<Double> queryC3 = this.tripService.queryC3();
		Assert.notEmpty(queryC3);

		final List<Double> queryC4 = this.tripService.queryC4();
		Assert.notEmpty(queryC4);

		final Double queryC5 = this.applicationService.queryC5();
		Assert.notNull(queryC5);

		final Double queryC6 = this.applicationService.queryC6();
		Assert.notNull(queryC6);

		final Double queryC7 = this.applicationService.queryC7();
		Assert.notNull(queryC7);

		final Double queryC8 = this.applicationService.queryC8();
		Assert.notNull(queryC8);

		final Double queryC9 = this.tripService.queryC9();
		Assert.notNull(queryC9);

		final List<Trip> queryC10 = this.tripService.queryC10();
		Assert.notEmpty(queryC10);

		final List<Object[]> queryC11 = this.legalText.queryC11();
		Assert.notNull(queryC11);

		final List<Double> queryB1 = this.noteService.queryB1();
		Assert.notEmpty(queryB1);

		final List<Double> queryB2 = this.auditService.queryB2();
		Assert.notNull(queryB2);

		final Double queryB3 = this.tripService.queryB3();
		Assert.notNull(queryB3);

		final Double queryB4 = this.rangerService.queryB4();
		Assert.notNull(queryB4);

		final Double queryB5 = this.rangerService.queryB5();
		Assert.notNull(queryB5);

		final Double queryB6 = this.managerService.queryB6();
		Assert.notNull(queryB6);

		final Double queryB7 = this.rangerService.queryB7();
		Assert.notNull(queryB7);

	}
}
