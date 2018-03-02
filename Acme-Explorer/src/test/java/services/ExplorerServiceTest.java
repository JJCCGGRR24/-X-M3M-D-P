
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
import domain.Explorer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ExplorerServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private ExplorerService	explorerService;


	// Service under test

	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		final Explorer e = this.explorerService.create();

		Assert.notNull(e.getApplications());
		Assert.notNull(e.getContacts());
		Assert.notNull(e.getFinder());
		Assert.notNull(e.getReceivedMesages());
		Assert.notNull(e.getSendMesages());
		Assert.notNull(e.getSocialIdentities());
		Assert.notNull(e.getStories());
		Assert.notNull(e.getSuspicious());
		Assert.notNull(e.getUserAccount());

		Assert.isNull(e.getFolders());
		Assert.isNull(e.getAddress());
		Assert.isNull(e.getEmail());
		Assert.isNull(e.getName());
		Assert.isNull(e.getPhone());
		Assert.isNull(e.getSurname());

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final List<Explorer> exs = (List<Explorer>) this.explorerService.findAll();
		final Explorer ex = exs.get(0);
		final Explorer find = this.explorerService.findOne(ex.getId());
		Assert.notNull(find);

	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		final List<Explorer> lc = (List<Explorer>) this.explorerService.findAll();
		Assert.notNull(lc);

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		final List<Explorer> exs = (List<Explorer>) this.explorerService.findAll();
		final Explorer ex = exs.get(0);
		this.authenticate(ex.getUserAccount().getUsername());
		final String emailViejo = ex.getEmail();
		ex.setEmail("jjjjjjjjjjjj@gmail.com");
		final Explorer saved = this.explorerService.save(ex);
		final String emailNuevo = saved.getEmail();
		Assert.isTrue(emailViejo != emailNuevo);
		this.unauthenticate();

	}

}
