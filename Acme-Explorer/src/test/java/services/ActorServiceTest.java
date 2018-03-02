
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
import domain.Actor;
import domain.Explorer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
//@TransactionConfiguration(defaultRollback = true)
public class ActorServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private ActorService	actorService;


	//Tests

	@Test
	public void testSaveActor() {

		//Edit his email
		super.authenticate("explorer");
		final int id = super.getEntityId("explorer1");
		final Actor a = this.actorService.findOne(id);

		a.setEmail("josecarlos.jcgr24@gmail.com");
		final Actor a2 = this.actorService.save(a);

		final Actor aNewEmail = this.actorService.findOne(a2.getId());

		Assert.isTrue(aNewEmail.getEmail().equals("josecarlos.jcgr24@gmail.com"));
		super.authenticate(null);

	}
	@Test
	public void testFindOneActor() {

		final int id = super.getEntityId("explorer1");
		final Actor a = this.actorService.findOne(id);
		Assert.isTrue(a.getUserAccount().getUsername().equals("explorer"));

	}

	@Test
	public void testFindAll() {

		final List<Actor> actors = (List<Actor>) this.actorService.findAll();
		Assert.notNull(actors);
	}

	@Test
	public void testFindByUserAccount() {

		super.authenticate("explorer");
		final Explorer e = (Explorer) this.actorService.findByUserAccount(LoginService.getPrincipal());

		final int id = super.getEntityId("explorer1");
		Assert.isTrue(e.getId() == id);
		super.authenticate(null);

	}

	@Test
	public void testAdminSaveActor() {

		//Edit his email
		super.authenticate("explorer");
		final int id = super.getEntityId("explorer1");
		final Actor a = this.actorService.findOne(id);

		a.setEmail("josecarlos.jcgr24@gmail.com");
		final Actor a2 = this.actorService.adminSave(a);

		final Actor aNewEmail = this.actorService.findOne(a2.getId());

		Assert.isTrue(aNewEmail.getEmail().equals("josecarlos.jcgr24@gmail.com"));
		super.authenticate(null);

	}

	@Test
	public void testGetSuspiciusRangerAndManager() {
		super.authenticate("admin");
		final List<Actor> actorsSusp = this.actorService.getSuspiciusRangerAndManager();
		Assert.notNull(actorsSusp);
		System.out.println("-------getSuspiciusRangerAndManager-----");
		System.out.println(actorsSusp.get(0));
		System.out.println(actorsSusp.get(1));
		super.authenticate(null);
	}

	@Test
	public void exist() {
		super.authenticate("admin");
		final String username = "ranger";
		Assert.isTrue(this.actorService.exist(username));
		System.out.println("----------exists---------");
		System.out.println("Se ha encontrado el usuario con nombre de usuario" + username);
		super.authenticate(null);
	}
}
