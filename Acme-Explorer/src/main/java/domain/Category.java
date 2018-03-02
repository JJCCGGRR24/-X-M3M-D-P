
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	// Relationships ----------------------------------------------------------

	private Category		categoryFather;
	private List<Category>	categoriesChildren;
	private List<Trip>		trips;


	@Valid
	@ManyToOne
	public Category getCategoryFather() {
		return this.categoryFather;
	}

	public void setCategoryFather(final Category categoryFather) {
		this.categoryFather = categoryFather;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "categoryFather")
	public List<Category> getCategoriesChildren() {
		return this.categoriesChildren;
	}

	public void setCategoriesChildren(final List<Category> categoriesChildren) {
		this.categoriesChildren = categoriesChildren;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "category")
	public List<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final List<Trip> trips) {
		this.trips = trips;
	}
}
