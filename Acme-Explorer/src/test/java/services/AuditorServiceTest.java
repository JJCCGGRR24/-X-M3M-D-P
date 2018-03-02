
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

import utilities.AbstractTest;
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//@Transactional
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AuditorServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private AuditorService	auditorService;


	//Tests
	@Test
	public void testSaveAuditor() {

		//Edit only his name
		super.authenticate("auditor");

		final int id = super.getEntityId("auditor1");
		final Auditor a = this.auditorService.findOne(id);

		a.setName("Ramon");
		final Auditor auditorSave = this.auditorService.save(a);

		Assert.isTrue(auditorSave.getName().equals("Ramon"));
		super.authenticate(null);
	}

	@Test
	public void testFindOneAuditor() {

		final int id = super.getEntityId("auditor2");
		final Auditor a = this.auditorService.findOne(id);
		Assert.isTrue(a.getUserAccount().getUsername().equals("auditor2"));

	}

	@Test
	public void testFindAll() {

		final List<Auditor> admins = (List<Auditor>) this.auditorService.findAll();
		Assert.isTrue(admins.size() == 2);

	}

}
