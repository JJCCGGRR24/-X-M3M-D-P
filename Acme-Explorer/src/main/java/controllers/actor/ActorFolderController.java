
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.FolderService;
import controllers.AbstractController;
import domain.Folder;

@Controller
@RequestMapping("/folder/actor")
public class ActorFolderController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FolderService	folderService;

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------

	public ActorFolderController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Folder> folders;

		folders = this.loginService.getPrincipalActor().getFolders();
		result = new ModelAndView("actor/folder/list");
		result.addObject("folders", folders);
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Folder f;
		f = this.folderService.create();
		f.setActor(this.loginService.getPrincipalActor());
		f.setModifiable(true);
		result = this.createEditModelAndView(f);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;
		folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		result = this.createEditModelAndView(folder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else if (this.folderService.isReservedName(folder))
			result = this.createEditModelAndView(folder, "folder.commit.error.name");
		else
			try {
				this.folderService.save(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Folder folder, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		try {
			this.folderService.delete(folder);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folder, "folder.commit.error");
			System.out.println(oops.getMessage());
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/folder/edit");
		result.addObject("folder", folder);
		result.addObject("message", message);

		return result;
	}

}
