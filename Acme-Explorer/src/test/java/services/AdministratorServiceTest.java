
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

import security.Authority;
import utilities.AbstractTest;
import domain.Actor;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AdministratorServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	//Tests
	@Test
	public void testFindOneAdmin() {

		super.authenticate("admin");
		final int id = super.getEntityId("administrator1");
		final Actor a = this.administratorService.findOne(id);
		Assert.isTrue(a.getUserAccount().getUsername().equals("admin"));
		super.authenticate(null);
	}

	@Test
	public void testFindAll() {

		super.authenticate("admin");
		final List<Administrator> admins = (List<Administrator>) this.administratorService.findAll();
		Assert.isTrue(admins.size() == 1);
		super.authenticate(null);
	}

	@Test
	public void testSaveAdmin() {

		super.authenticate("admin");
		final int id = super.getEntityId("administrator1");
		final Administrator a = this.administratorService.findOne(id);

		a.setAddress("Calle Olivo");
		final Administrator a2 = this.administratorService.save(a);

		Assert.isTrue(a2.getAddress().equals("Calle Olivo"));
		super.authenticate(null);

	}

	@Test
	public void testBan() {

		super.authenticate("admin");

		final int id = super.getEntityId("explorer1");
		final Actor a = this.actorService.findOne(id);

		final List<Authority> lista = (List<Authority>) a.getUserAccount().getAuthorities();
		Assert.notEmpty(lista, "Esta vacia");

		final Actor actorBan = this.administratorService.ban(a);
		//		final List<Authority> lista2 = (List<Authority>) actorBan.getUserAccount().getAuthorities();
		final List<Authority> list = (List<Authority>) actorBan.getUserAccount().getAuthorities();
		final String auth = list.get(0).getAuthority();

		Assert.isTrue(auth.equals("BANNED"), "No se baneo");
		super.authenticate(null);
	}
	@Test
	public void testUnban() {
		super.authenticate("admin");

		final int id = super.getEntityId("explorer1");
		final Actor a = this.actorService.findOne(id);

		final List<Authority> lista = (List<Authority>) a.getUserAccount().getAuthorities();
		Assert.notEmpty(lista, "Esta vacia");

		final Actor actorBan = this.administratorService.ban(a);
		//		final List<Authority> lista2 = (List<Authority>) actorBan.getUserAccount().getAuthorities();
		final List<Authority> list = (List<Authority>) actorBan.getUserAccount().getAuthorities();
		final String auth = list.get(0).getAuthority();

		Assert.isTrue(auth.equals("BANNED"), "No se baneo");

		final Actor actorUnBan = this.administratorService.unban(a);
		//		final List<Authority> lista2 = (List<Authority>) actorBan.getUserAccount().getAuthorities();
		final List<Authority> list2 = (List<Authority>) actorUnBan.getUserAccount().getAuthorities();
		final String auth2 = list2.get(0).getAuthority();

		Assert.isTrue(auth2.equals("EXPLORER"), "No se quito el  baneo");

		super.authenticate(null);

	}

	@Test
	public void testAdminSaveActor() {

		//Edit his email
		super.authenticate("admin");
		final int id = super.getEntityId("explorer1");
		final Actor a = this.actorService.findOne(id);

		a.setEmail("josecarlos.jcgr24@gmail.com");
		final Actor a2 = this.actorService.adminSave(a);

		final Actor aNewEmail = this.actorService.findOne(a2.getId());

		Assert.isTrue(aNewEmail.getEmail().equals("josecarlos.jcgr24@gmail.com"));
		super.authenticate(null);

	}
}
