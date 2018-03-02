
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
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private ManagerService	managerService;


	//Tests
	@Test
	public void testCreateAndSave() {
		super.authenticate("manager");
		//		final Manager a = (Manager) this.loginService.getPrincipalActor();
		final Manager a = this.managerService.create();
		System.out.println("----Create & Save------");
		System.out.println(a + " --> " + a.getName());
		a.setName("Paquito");
		final Manager save = this.managerService.save(a);
		final Manager find = this.managerService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		System.out.println(save + " --> " + save.getName());
		System.out.println("");
		super.authenticate(null);
	}

	@Test
	public void testFindAllManager() {

		super.authenticate("manager");
		final List<Manager> all = (List<Manager>) this.managerService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Manager l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneManager() {

		super.authenticate("manager");
		final List<Manager> all = (List<Manager>) this.managerService.findAll();
		final Manager al = this.managerService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
