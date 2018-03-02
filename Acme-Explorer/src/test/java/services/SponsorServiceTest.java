
package services;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private SponsorService	sponsorService;


	//Tests

	//	@Test
	//	public void testCreate() {
	//
	//		final Sponsor r = this.sponsorService.create();
	//
	//		Assert.isTrue(r.getName() == null);
	//		Assert.isTrue(r.getSurname() == null);
	//		Assert.isTrue(r.getEmail() == null);
	//		Assert.isTrue(r.getPhone() == null);
	//		Assert.isTrue(r.getAddress() == null);
	//		Assert.isTrue(r.getSuspicious() == false);
	//
	//	}

	@Test
	public void testSave() {

		final Sponsor result = ((List<Sponsor>) this.sponsorService.findAll()).get(0);
		this.authenticate(result.getUserAccount().getUsername());

		result.setName("Alejandra");
		result.setSurname("Ramirez");
		result.setEmail("alejandra@gmail.com");
		result.setPhone("614325458");
		result.setAddress("C/Raimundo Navarro");

		final Sponsor saved = this.sponsorService.save(result);

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();
		Assert.isTrue(sponsors.contains(saved));

		this.unauthenticate();

	}

	@Test
	public void testFindAllSponsor() {

		super.authenticate("manager");
		final List<Sponsor> all = (List<Sponsor>) this.sponsorService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Sponsor l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneSponsor() {

		super.authenticate("manager");
		final List<Sponsor> all = (List<Sponsor>) this.sponsorService.findAll();
		final Sponsor al = this.sponsorService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}
}
