
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
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;


	//Tests

	@Test
	public void testCreate() {

		final Sponsor s = ((List<Sponsor>) this.sponsorService.findAll()).get(0);
		this.authenticate(s.getUserAccount().getUsername());

		final Sponsorship ss = this.sponsorshipService.create();
		Assert.isTrue(ss.getBanner() == null && ss.getInfoPage() == null && ss.getCreditCard() == null && ss.getSponsor() != null);

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		final Sponsor s = ((List<Sponsor>) this.sponsorService.findAll()).get(0);
		this.authenticate(s.getUserAccount().getUsername());

		final Sponsorship result = this.sponsorshipService.create();

		final CreditCard cc = new CreditCard();
		cc.setBrandName("VISA");
		cc.setCVV(112);
		cc.setExpirationMonth(5);
		cc.setExpirationYear(2992);
		cc.setHolderName("Pepito Grillo");
		cc.setNumber("4485123087562803");

		result.setBanner("https://i.ytimg.com/vi/VcW3ma-hrX4/hqdefault.jpg");
		result.setInfoPage("https://es.wikipedia.org/wiki/Suricata_suricatta");
		result.setCreditCard(cc);

		final Sponsorship saved = this.sponsorshipService.save(result);
		final Collection<Sponsorship> sponsorshipCollection = this.sponsorshipService.findAll();
		Assert.isTrue(sponsorshipCollection.contains(saved));
		Assert.isTrue(s.getSponsorships().contains(saved));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		final Sponsor s = ((List<Sponsor>) this.sponsorService.findAll()).get(0);
		this.authenticate(s.getUserAccount().getUsername());

		final Sponsorship ss = s.getSponsorships().get(0);

		this.sponsorshipService.delete(ss);
		final Collection<Sponsorship> sss = this.sponsorshipService.findAll();
		Assert.isTrue(!sss.contains(ss));

		this.unauthenticate();

	}

	@Test
	public void testFindAllSponsorship() {

		super.authenticate("manager");
		final List<Sponsorship> all = (List<Sponsorship>) this.sponsorshipService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Sponsorship l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneSponsorship() {

		super.authenticate("manager");
		final List<Sponsorship> all = (List<Sponsorship>) this.sponsorshipService.findAll();
		final Sponsorship al = this.sponsorshipService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
