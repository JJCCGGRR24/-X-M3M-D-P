
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/admin")
public class AdminCategoryController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------------------------------------------------------
	public AdminCategoryController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create() {
		final ModelAndView res = this.createEditModelAndViewCategory(this.categoryService.create());
		return res;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editCategory(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;
		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);
		result = this.createEditModelAndViewCategory(category);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCategory(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(category);
		final String s = this.categoryService.validate(category);
		if (binding.hasErrors() || s != null)
			result = this.createEditModelAndViewCategory(category, s);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:/category/all/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCategory(category, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteCategory(final Category category, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(!this.categoryService.findOne(category.getId()).getName().equals("CATEGORY"));
		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/category/all/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewCategory(category, "commit.error");
		}
		return result;
	}

	// Ancillary methods ---------------------------------------------------------------
	protected ModelAndView createEditModelAndViewCategory(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndViewCategory(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewCategory(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		if (category.getId() == 0)
			result.addObject("categories", this.categoryService.findAll());
		else
			result.addObject("categories", this.categoryService.validFathers(category));
		result.addObject("message", message);

		return result;
	}
}
