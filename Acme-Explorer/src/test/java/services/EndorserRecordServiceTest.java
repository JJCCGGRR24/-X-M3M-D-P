
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
import domain.Curricula;
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private EndorserRecordService	endorserRecordService;

	// Service aux
	@Autowired
	private CurriculaService		curriculaService;


	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		final List<Curricula> cs = (List<Curricula>) this.curriculaService.findAll();
		final Curricula curricula = cs.get(0);

		this.authenticate(curricula.getRanger().getUserAccount().getUsername());

		final EndorserRecord er = this.endorserRecordService.create(curricula);

		Assert.notNull(er.getCurriculum());

		Assert.isNull(er.getEmail());
		Assert.isNull(er.getComments());
		Assert.isNull(er.getLinkedProfile());
		Assert.isNull(er.getName());
		Assert.isNull(er.getPhone());

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");

		final List<Curricula> cs = (List<Curricula>) this.curriculaService.findAll();
		final Curricula curricula = cs.get(0);

		this.authenticate(curricula.getRanger().getUserAccount().getUsername());

		final EndorserRecord er = this.endorserRecordService.create(curricula);

		this.authenticate(curricula.getRanger().getUserAccount().getUsername());

		er.setEmail("aaaaa@gmail.com");
		er.setComments("aaaaaaaaaa");
		er.setLinkedProfile("http://www.google.es");
		er.setName("Paco paco paco");
		er.setPhone("+34 879098765");

		final EndorserRecord saved = this.endorserRecordService.save(er);
		Assert.isTrue(this.endorserRecordService.findAll().contains(saved));
		this.unauthenticate();

		Assert.notNull(this.endorserRecordService.findOne(saved.getId()));

		this.endorserRecordService.flush();

	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final List<EndorserRecord> lc = (List<EndorserRecord>) this.endorserRecordService.findAll();
		Assert.notNull(lc);

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		final List<Curricula> cs = (List<Curricula>) this.curriculaService.findAll();
		final Curricula curricula = cs.get(0);

		this.authenticate(curricula.getRanger().getUserAccount().getUsername());

		final EndorserRecord er = this.endorserRecordService.create(curricula);

		this.authenticate(curricula.getRanger().getUserAccount().getUsername());

		er.setEmail("aaaaa@gmail.com");
		er.setLinkedProfile("http://www.google.es");
		er.setName("Paco paco paco");
		er.setPhone("+34 879098765");

		final EndorserRecord saved = this.endorserRecordService.save(er);
		Assert.isTrue(this.endorserRecordService.findAll().contains(saved));
		this.unauthenticate();

		this.endorserRecordService.flush();

	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		final List<Curricula> cs = (List<Curricula>) this.curriculaService.findAll();
		final Curricula curricula = cs.get(0);

		this.authenticate(curricula.getRanger().getUserAccount().getUsername());

		final EndorserRecord er = this.endorserRecordService.create(curricula);

		er.setEmail("aaaaa@gmail.com");
		er.setComments("aaaaaaaaaa");
		er.setLinkedProfile("http://www.google.es");
		er.setName("Paco paco paco");
		er.setPhone("+34 879098765");

		final EndorserRecord saved = this.endorserRecordService.save(er);
		Assert.isTrue(this.endorserRecordService.findAll().contains(saved));

		this.endorserRecordService.delete(saved);
		Assert.isTrue(!this.endorserRecordService.findAll().contains(saved));

		this.unauthenticate();

		this.endorserRecordService.flush();

	}

}
