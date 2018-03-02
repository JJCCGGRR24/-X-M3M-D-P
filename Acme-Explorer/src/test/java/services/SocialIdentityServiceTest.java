
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
import domain.Actor;
import domain.Ranger;
import domain.SocialIdentity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialIdentityServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private ActorService			actorService;


	//Tests

	@Test
	public void testCreate() {

		final Actor a = ((List<Actor>) this.actorService.findAll()).get(0);
		this.authenticate(a.getUserAccount().getUsername());

		final SocialIdentity sI = this.socialIdentityService.create();
		Assert.isTrue(sI.getLink() == null && sI.getNetwork() == null && sI.getPhoto() == null && sI.getNick() == null && sI.getActor() != null);

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		final Actor a = ((List<Actor>) this.actorService.findAll()).get(0);
		this.authenticate(a.getUserAccount().getUsername());
		Collection<SocialIdentity> socialidentityCollection;

		final SocialIdentity result = this.socialIdentityService.create();

		result.setLink("https://dev.twitter.com/apps/newNO");

		result.setNetwork("Adsense");
		result.setNick("CanoCraft");
		result.setPhoto("https://dev.twitter.com/apps/newNO.jpg");

		final SocialIdentity saved = this.socialIdentityService.save(result);
		socialidentityCollection = this.socialIdentityService.findAll();
		Assert.isTrue(socialidentityCollection.contains(saved));
		Assert.isTrue(a.getSocialIdentities().contains(saved));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		final Ranger r = ((List<Ranger>) this.rangerService.findAll()).get(0);
		this.authenticate(r.getUserAccount().getUsername());

		final SocialIdentity s = r.getSocialIdentities().get(0);

		this.socialIdentityService.delete(s);
		final Collection<SocialIdentity> SocialIdentitys = this.socialIdentityService.findAll();
		Assert.isTrue(!SocialIdentitys.contains(s));

		this.unauthenticate();

	}

	@Test
	public void testFindAllSocialIdentity() {

		super.authenticate("manager");
		final List<SocialIdentity> all = (List<SocialIdentity>) this.socialIdentityService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final SocialIdentity l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneSocialIdentity() {

		super.authenticate("manager");
		final List<SocialIdentity> all = (List<SocialIdentity>) this.socialIdentityService.findAll();
		final SocialIdentity al = this.socialIdentityService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

	@Test
	public void testFindSocialIdentities() {

		super.authenticate("auditor2");
		final List<SocialIdentity> socials = this.socialIdentityService.socialId();
		Assert.notEmpty(socials);
		super.unauthenticate();

		System.out.println("---------Social Identity------------");
		for (final SocialIdentity socialId : socials)
			System.out.println(socialId);

	}
}
