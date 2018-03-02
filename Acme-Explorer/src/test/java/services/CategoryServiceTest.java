
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
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CategoryServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private CategoryService	categoryService;


	//Tests

	@Test
	public void testCreate() {

		super.authenticate("admin");

		final Category c = this.categoryService.create();

		Assert.notNull(c.getCategoriesChildren());
		Assert.notNull(c.getTrips());
		Assert.isNull(c.getName());
		Assert.isNull(c.getCategoryFather());
		super.authenticate(null);
	}

	@Test
	public void testSaveCategory() {

		super.authenticate("admin");

		final Category a = this.categoryService.create();
		a.setName("Categoria de prueba para save");

		final int id = super.getEntityId("Mountain_Climbing");
		final Category categoryFather = this.categoryService.findOne(id);
		a.setCategoryFather(categoryFather);

		final Category save = this.categoryService.save(a);

		final Category find = this.categoryService.findOne(save.getId());

		Assert.isTrue(save.equals(find));
		super.authenticate(null);
		System.out.println("------Save------" + "\n" + find.getName() + "\n" + find.getCategoriesChildren() + "\n" + find.getCategoryFather().getName() + "\n");
	}
	@Test
	public void testFindAllCategory() {

		final List<Category> all = (List<Category>) this.categoryService.findAll();
		Assert.isTrue(all.size() == 11);

	}

	@Test
	public void testFindOneCategory() {

		final int id = super.getEntityId("Mountain_Climbing");
		final Category a = this.categoryService.findOne(id);

		final int idFather = super.getEntityId("Climbing");

		Assert.isTrue(a.getName().equals("Mountain") && a.getCategoryFather().getId() == idFather);

		System.out.println("----Find One--------" + "\n" + a.getName() + "\n" + a.getCategoryFather().getName() + "\n" + a.getCategoriesChildren() + "\n");
	}

	@Test
	public void testDeleteCategory() {
		super.authenticate("admin");
		final List<Category> categorys = (List<Category>) this.categoryService.findAll();
		System.out.println("-----Delete-----" + "\n");
		System.out.println("Nº de Categorías: " + categorys.size());
		final int id = super.getEntityId("Water");
		final Category find = this.categoryService.findOne(id);
		this.categoryService.delete(find);
		final List<Category> categorys2 = (List<Category>) this.categoryService.findAll();

		super.authenticate(null);
		System.out.println("");
		System.out.println("Categorías restantes: " + categorys2.size());
		for (final Category c : categorys2)
			System.out.println(c.getName());
	}
}
