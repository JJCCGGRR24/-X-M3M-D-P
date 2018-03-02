
package services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Curricula;
import domain.PersonalRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private CurriculaService	curriculaService;

	// Service under test
	@Autowired
	private RangerService		rangerService;

	@Autowired
	private LoginService		loginService;


	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		final List<Ranger> exs = (List<Ranger>) this.rangerService.findAll();
		final Ranger e = exs.get(0);
		e.setCurricula(null);

		this.authenticate(e.getUserAccount().getUsername());

		final Curricula c = this.curriculaService.create();
		Assert.notNull(c.getRanger());
		Assert.notNull(c.getEducationRecords());
		Assert.notNull(c.getEndorserRecords());
		Assert.notNull(c.getMiscellaneousRecords());
		Assert.notNull(c.getProfessionalRecords());
		Assert.notNull(c.getTicker());
		Assert.isNull(c.getPersonalRecord());

		this.unauthenticate();
		this.curriculaService.flush();

	}
	//mucha mierda pa un findOne
	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		super.authenticate("ranger");
		final Ranger r = (Ranger) this.loginService.getPrincipalActor();
		final Ranger g = this.rangerService.findOne(r.getId());
		Assert.notNull(g);
		System.out.println(g + ": " + g.getName() + " " + g.getSurname());
		//		Ranger e = this.rangerService.create();
		//		e.setEmail("aaaaa@gmail.com");
		//		e.setName("aaaaa");
		//		e.setPhone("678909876");
		//		e.setSurname("aaaaaaaa");
		//		e.setAddress("aaaaaaaaaa");
		//		final UserAccount userAccount = e.getUserAccount();
		//		userAccount.setPassword("jajajaja");
		//		userAccount.setUsername("aaaaaaaaxd");
		//		e.setUserAccount(userAccount);
		//		e = this.rangerService.save(e);
		//		this.authenticate(e.getUserAccount().getUsername());
		//
		//		final Curricula c = this.curriculaService.create();
		//		final PersonalRecord pr = new PersonalRecord();
		//		pr.setEmail("paco2@gmail.com");
		//		pr.setFullName("Paco paco");
		//		pr.setLinkedProfile("https://es.linkedin.com/");
		//		pr.setPhone("+34 898776574");
		//		pr.setPhoto("https://static.licdn.com/sc/h/95o6rrc5ws6mlw6wqzy0xgj7y");
		//		c.setPersonalRecord(pr);
		//
		//		final Curricula saved = this.curriculaService.save(c);
		//
		//		Assert.isTrue(this.curriculaService.findAll().contains(saved));
		//
		//		this.unauthenticate();
		//
		//		final Curricula cfind = this.curriculaService.findOne(saved.getId());
		//		Assert.notNull(cfind);
		//
		//		this.curriculaService.flush();
		super.authenticate(null);
		System.out.println("");
	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final List<Curricula> lc = (List<Curricula>) this.curriculaService.findAll();
		Assert.notNull(lc);
		for (final Curricula cu : lc)
			System.out.println(cu.getTicker());
		System.out.println("");
		this.curriculaService.flush();

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		this.authenticate("ranger");

		final Ranger r = (Ranger) this.loginService.getPrincipalActor();
		final Curricula curricula = r.getCurricula();

		final PersonalRecord pr = new PersonalRecord();
		final String emailAntiguo = pr.getEmail();
		pr.setEmail("paco2@yopmail.com");
		pr.setFullName("Paco paco");
		pr.setLinkedProfile("https://es.linkedin.com/");
		pr.setPhone("+34 898776574");
		pr.setPhoto("https://static.licdn.com/sc/h/95o6rrc5ws6mlw6wqzy0xgj7y");
		curricula.setPersonalRecord(pr);

		final Curricula saved = this.curriculaService.save(curricula);

		Assert.isTrue(emailAntiguo != saved.getPersonalRecord().getEmail());

		this.unauthenticate();
		this.curriculaService.flush();

	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");
		this.authenticate("ranger");
		final Ranger g = ((Ranger) (this.loginService.getPrincipalActor()));
		final Curricula c = g.getCurricula();
		System.out.println("Buscamos el CV: " + c);
		System.out.println("- Borramos -");
		g.setCurricula(null);
		this.rangerService.save(g);
		//		c.setRanger(null);
		this.curriculaService.delete(c);
		Assert.isTrue(!this.curriculaService.findAll().contains(c));
		System.out.println("Volvemos a buscarlo: " + g.getCurricula());
		this.unauthenticate();
		this.curriculaService.flush();
	}
}
