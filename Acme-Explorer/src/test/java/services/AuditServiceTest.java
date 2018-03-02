
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
import domain.Audit;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private AuditService	auditService;

	@Autowired
	private TripService		tripService;


	//Tests

	@Test
	public void testCreateAudit() {

		super.authenticate("auditor");

		final int id = super.getEntityId("trip2");
		final Trip trip = this.tripService.findOne(id);
		final Audit a = this.auditService.create(trip);

		Assert.notNull(a.getMoment());
		Assert.notNull(a.getFinalMode());
		Assert.notNull(a.getAttachments());
		Assert.notNull(a.getTrip());
		Assert.notNull(a.getAuditor());
		Assert.isNull(a.getTitle());
		Assert.isNull(a.getDescription());

		super.authenticate(null);
	}

	@Test
	public void testSaveAudit() {

		super.authenticate("auditor");

		final int id = super.getEntityId("trip2");
		final Trip trip = this.tripService.findOne(id);

		final Audit a = this.auditService.create(trip);

		a.setDescription("This audit is good");
		a.setTitle("Audit saved");

		final Audit save = this.auditService.save(a);

		final Audit find = this.auditService.findOne(save.getId());
		Assert.isTrue(find.equals(save));
		super.authenticate(null);

	}

	@Test
	public void testFindOne() {

		super.authenticate("auditor");

		final int id = super.getEntityId("audit1");
		final Audit a = this.auditService.findOne(id);
		final int idAuditor = super.getEntityId("auditor1");
		final int idTrip = super.getEntityId("trip1");
		Assert.isTrue(a.getAuditor().getId() == idAuditor && a.getTrip().getId() == idTrip);
		super.authenticate(null);
	}

	@Test
	public void testFindAllAudits() {

		super.authenticate("auditor");
		final List<Audit> all = (List<Audit>) this.auditService.findAll();
		Assert.isTrue(all.size() == 2);
		super.authenticate(null);

	}

	@Test
	public void testDeleteAudit() {

		super.authenticate("auditor2");

		final int id = super.getEntityId("audit2");
		final Audit save = this.auditService.findOne(id);

		this.auditService.delete(save);

		final List<Audit> applis = (List<Audit>) this.auditService.findAll();
		Assert.isTrue(!applis.contains(save));
		super.authenticate(null);

	}
}
