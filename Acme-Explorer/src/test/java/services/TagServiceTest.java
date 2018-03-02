
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
import domain.Tag;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private TagService	tagService;


	//Tests

	@Test
	public void testCreate() {

		final Tag t = this.tagService.create();

		Assert.isTrue(t.getName() == null);

	}

	@Test
	public void testSave() {

		final Tag t = this.tagService.create();
		t.setName("Churrasquito");
		final Tag saved = this.tagService.save(t);

		final Collection<Tag> sc = this.tagService.findAll();
		Assert.isTrue(sc.contains(saved));

	}
	@Test
	public void testDelete() {

		final Tag result = ((List<Tag>) this.tagService.findAll()).get(0);

		this.tagService.delete(result);
		final Collection<Tag> t = this.tagService.findAll();
		Assert.isTrue(!t.contains(result));

	}

	@Test
	public void testFindAllTag() {

		super.authenticate("manager");
		final List<Tag> all = (List<Tag>) this.tagService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Tag l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneTag() {

		super.authenticate("manager");
		final List<Tag> all = (List<Tag>) this.tagService.findAll();
		final Tag al = this.tagService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
