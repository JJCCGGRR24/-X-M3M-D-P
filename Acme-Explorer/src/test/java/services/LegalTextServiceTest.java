
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
import domain.LegalText;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class LegalTextServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private LegalTextService	legalTextService;


	//Tests
	@Test
	public void testCreateAndSave() {

		super.authenticate("manager");
		final LegalText a = this.legalTextService.create();
		a.setBody("body");
		a.setFinalMode(true);
		a.setTitle("title");
		final LegalText save = this.legalTextService.save(a);
		final LegalText find = this.legalTextService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		super.authenticate(null);
		System.out.println("----Create & Save------");
		System.out.println(save);
		System.out.println("");
	}

	@Test
	public void testDelete() {

		super.authenticate("manager");
		final LegalText a = this.legalTextService.create();
		a.setBody("body");
		a.setFinalMode(false);
		a.setTitle("title");
		final LegalText save = this.legalTextService.save(a);
		final LegalText find = this.legalTextService.findOne(save.getId());
		Assert.isTrue(save.equals(find));

		this.legalTextService.delete(save);

		final List<LegalText> applis = (List<LegalText>) this.legalTextService.findAll();
		Assert.isTrue(!applis.contains(save));
		super.authenticate(null);

		System.out.println("---delete----");
		for (final LegalText a1 : applis)
			System.out.println(a1);

		System.out.println("");
		super.authenticate(null);

	}

	@Test
	public void testFindAllLegalText() {

		super.authenticate("manager");
		final List<LegalText> all = (List<LegalText>) this.legalTextService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final LegalText l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneLegalText() {

		super.authenticate("manager");
		final List<LegalText> all = (List<LegalText>) this.legalTextService.findAll();
		final LegalText al = this.legalTextService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

	@Test
	public void testPublish() {
		super.authenticate("manager");
		final LegalText a = this.legalTextService.create();
		a.setBody("body");
		a.setFinalMode(false);
		a.setTitle("title");
		final LegalText save = this.legalTextService.save(a);
		final LegalText find = this.legalTextService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		this.legalTextService.publish(save);
		super.authenticate(null);
		System.out.println("-----Publish--------");
		System.out.println(save);

		System.out.println("");
	}

}
