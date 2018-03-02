
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.Trip;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository	categoryRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Category create() {
		final Category c = new Category();
		c.setCategoriesChildren(new ArrayList<Category>());
		c.setTrips(new ArrayList<Trip>());
		return c;
	}
	public Collection<Category> findAll() {
		final Collection<Category> res = this.categoryRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Category findOne(final int categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		return this.categoryRepository.save(category);
	}

	public void delete(final Category c) {
		this.deleteAux(c);
	}

	private void deleteAux(final Category c) {
		//		if (c.getCategoriesChildren().isEmpty()) {
		//			for (final Trip t : c.getTrips())
		//				t.setCategory(null);
		//			System.out.println("Intento de borrar: " + c.getName());
		//			this.categoryRepository.delete(c);
		//			System.out.println(c.getName() + " eliminado.");
		//			System.out.println("");
		//		} else {
		//			for (final Category cat : c.getCategoriesChildren()) {
		//				this.deleteAux(cat);
		//				cat.setCategoryFather(null);
		//			}
		//			this.categoryRepository.delete(c);
		//			for (final Trip t : c.getTrips())
		//				t.setCategory(null);
		//			System.out.println("Intento de borrar: " + c.getName());
		//			c.setCategoriesChildren(new ArrayList<Category>());
		//			this.categoryRepository.delete(c);
		//			System.out.println(c.getName() + " eliminado.");
		//			System.out.println("");
		//		}
		for (final Category cat : c.getCategoriesChildren())
			this.deleteAux(cat);
		for (final Trip t : c.getTrips())
			t.setCategory(null);
		System.out.println("Intento de borrar: " + c.getName());
		c.setCategoriesChildren(new ArrayList<Category>());
		this.categoryRepository.delete(c);
		System.out.println(c.getName() + " eliminado.");
		System.out.println("");
	}

	public List<Category> getChildrenOf(final Category c) {
		final List<Category> l = this.getChildrenOfAux(c, new ArrayList<Category>());
		l.add(c);
		return l;
	}

	private List<Category> getChildrenOfAux(final Category c, final List<Category> lc) {
		if (c.getCategoriesChildren() != null)
			for (final Category cat : c.getCategoriesChildren()) {
				lc.add(cat);
				this.getChildrenOfAux(cat, lc);
			}
		return lc;
	}

	public List<Category> getFathersOf(final Category c) {
		final List<Category> l = this.getFathersOfAux(c, new ArrayList<Category>());
		l.add(c);
		return l;
	}

	private List<Category> getFathersOfAux(final Category c, final List<Category> lc) {
		if (c.getCategoryFather() != null) {
			lc.add(c.getCategoryFather());
			this.getFathersOfAux(c.getCategoryFather(), lc);
		}
		return lc;
	}

	public void flush() {
		this.categoryRepository.flush();
	}

	// Other business methods -------------------------------------------------

	@SuppressWarnings("unused")
	private boolean isDeleteable(final Category c) {
		return this.isDeleteableAux(c, true, 1);
	}

	private boolean isDeleteableAux(final Category c, boolean isDeleteable, int i) {
		final boolean isBasic = c.getCategoriesChildren().isEmpty();
		i++;
		if (!isBasic)
			for (final Category cat : c.getCategoriesChildren()) {
				i++;
				isDeleteable = isDeleteable & cat.getTrips().isEmpty() & this.isDeleteableAux(cat, isDeleteable, i);
			}
		else
			isDeleteable = isDeleteable & c.getTrips().isEmpty();
		return isDeleteable;
	}

	public List<Category> validFathers(final Category c) {
		final List<Category> lc = ((List<Category>) this.findAll());
		lc.removeAll(this.getChildrenOf(c));
		return lc;
	}

	public Category get(final String string) {
		return this.categoryRepository.get(string);
	}

	public String validate(final Category category) {
		String s = null;
		final Category cBBDD = this.findOne(category.getId());
		if (category.getId() != 0) //edit
			Assert.isTrue(this.validFathers(cBBDD).contains(category.getCategoryFather()));
		else
			Assert.isTrue(category.getCategoryFather() != null);
		if (category.getName().toUpperCase().equals("CATEGORY"))
			s = "category.reserved";
		for (final Category cat : this.findOne(category.getCategoryFather().getId()).getCategoriesChildren())
			if (cat.getName().toUpperCase().equals(category.getName().toUpperCase())) {
				s = "category.used";
				break;
			}
		return s;
	}

	public List<Trip> getTripVisibleByCategory(final List<Trip> trips) {
		final List<Trip> res = new ArrayList<Trip>();
		for (final Trip t : trips)
			if (t.getPublicationDate() != null && t.getCancelledReason() == null)
				res.add(t);
		return res;
	}
}
