
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
import domain.Explorer;
import domain.Story;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StoryServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private StoryService	storyService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ExplorerService	explorerService;


	//Tests

	@Test
	public void testCreate() {

		final Trip trip = ((List<Trip>) this.tripService.findAll()).get(0);

		final Story s = this.storyService.create(trip);

		Assert.isTrue(s.getTitle() == null && s.getText() == null);

	}

	@Test
	public void testSave() {

		final Explorer e = ((List<Explorer>) this.explorerService.findAll()).get(0);
		this.authenticate(e.getUserAccount().getUsername());

		final Story result = e.getStories().get(0);

		result.setTitle("La historia de tu vida");

		result.setText("Vamos a ver muchos animales y muchos sitios bonitos");

		final Story saved = this.storyService.save(result);

		final Collection<Story> stories = this.storyService.findAll();
		Assert.isTrue(stories.contains(saved));
		Assert.isTrue(e.getStories().contains(saved));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		final Explorer e = ((List<Explorer>) this.explorerService.findAll()).get(0);
		this.authenticate(e.getUserAccount().getUsername());

		final Story result = e.getStories().get(0);

		this.storyService.delete(result);
		final Collection<Story> stories = this.storyService.findAll();
		Assert.isTrue(!stories.contains(result));

		this.unauthenticate();

	}

	@Test
	public void testFindAllStory() {

		super.authenticate("manager");
		final List<Story> all = (List<Story>) this.storyService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Story l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneStory() {

		super.authenticate("manager");
		final List<Story> all = (List<Story>) this.storyService.findAll();
		final Story al = this.storyService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
