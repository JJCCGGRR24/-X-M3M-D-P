
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
import domain.Contact;
import domain.Explorer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ContactServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private ContactService	contactService;

	// Service under test
	@Autowired
	private ExplorerService	explorerService;


	//Tests

	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		final List<Explorer> exs = (List<Explorer>) this.explorerService.findAll();
		final Explorer e = exs.get(0);

		this.authenticate(e.getUserAccount().getUsername());

		final Contact c = this.contactService.create();
		Assert.notNull(c.getExplorer());
		Assert.isNull(c.getName());
		Assert.isNull(c.getEmail());
		Assert.isNull(c.getPhone());

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");

		final List<Explorer> exs = (List<Explorer>) this.explorerService.findAll();
		final Explorer e = exs.get(0);

		this.authenticate(e.getUserAccount().getUsername());

		final Contact c = this.contactService.create();
		c.setEmail("paco@gmail.com");
		c.setName("Paquillo Fdez");
		c.setPhone("+34 678987898");

		final Contact saved = this.contactService.save(c);

		Assert.isTrue(this.contactService.findAll().contains(saved));

		this.unauthenticate();

		final Contact cfind = this.contactService.findOne(saved.getId());
		Assert.notNull(cfind);

	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final List<Contact> lc = (List<Contact>) this.contactService.findAll();
		Assert.notNull(lc);

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		final List<Explorer> exs = (List<Explorer>) this.explorerService.findAll();
		final Explorer e = exs.get(0);

		this.authenticate(e.getUserAccount().getUsername());

		final Contact c = this.contactService.create();
		c.setEmail("paco@gmail.com");
		c.setName("Paquillo Fdez");
		c.setPhone("+34 678987898");

		final Contact saved = this.contactService.save(c);

		Assert.isTrue(this.contactService.findAll().contains(saved));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		final List<Explorer> exs = (List<Explorer>) this.explorerService.findAll();
		final Explorer e = exs.get(0);

		this.authenticate(e.getUserAccount().getUsername());

		final Contact c = this.contactService.create();
		c.setEmail("paco@gmail.com");
		c.setName("Paquillo Fdez");
		c.setPhone("+34 678987898");

		final Contact saved = this.contactService.save(c);

		Assert.isTrue(this.contactService.findAll().contains(saved));

		this.contactService.delete(saved);
		Assert.isTrue(!this.contactService.findAll().contains(saved));

		this.unauthenticate();

	}
}
