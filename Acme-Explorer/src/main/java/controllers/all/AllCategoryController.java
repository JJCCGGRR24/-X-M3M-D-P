
package controllers.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;

@Controller
@RequestMapping("/category/all")
public class AllCategoryController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------------------------------------------------------
	public AllCategoryController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("category/list");
		res.addObject("categories", this.categoryService.findAll());
		return res;
	}

	@RequestMapping("/flat")
	public ModelAndView flat() {
		final ModelAndView res = new ModelAndView("category/flat");
		res.addObject("category", this.categoryService.get("CATEGORY"));
		return res;
	}
}
