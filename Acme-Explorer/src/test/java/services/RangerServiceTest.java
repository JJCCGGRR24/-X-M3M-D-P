
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
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RangerServiceTest extends AbstractTest {

	@Autowired
	private RangerService	rangerService;


	//Tests
	@Test
	public void testCreateAndSave() {
		super.authenticate("ranger");
		//		final Ranger a = (Ranger) this.loginService.getPrincipalActor();
		final Ranger a = this.rangerService.create();
		System.out.println("----Create & Save------");
		System.out.println(a + " --> " + a.getName());
		a.setName("Paquito");
		final Ranger save = this.rangerService.save(a);
		final Ranger find = this.rangerService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		System.out.println(save + " --> " + save.getName());
		System.out.println("");
		super.authenticate(null);
	}

	@Test
	public void testFindAllRanger() {

		super.authenticate("ranger");
		final List<Ranger> all = (List<Ranger>) this.rangerService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Ranger l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneRanger() {

		super.authenticate("ranger");
		final List<Ranger> all = (List<Ranger>) this.rangerService.findAll();
		final Ranger al = this.rangerService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
