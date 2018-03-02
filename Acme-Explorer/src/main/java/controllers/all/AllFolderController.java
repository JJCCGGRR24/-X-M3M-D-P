
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import domain.Folder;

@Controller
@RequestMapping("/folder/all")
public class AllFolderController {

	//Services ---------------------------------------------------------------

	@Autowired
	private FolderService	folderService;


	// Constructors -----------------------------------------------------------
	public AllFolderController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Folder> folders = this.folderService.getFoldersByUser();
		Assert.notEmpty(folders);
		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/all/list.do");
		return result;
	}

}
