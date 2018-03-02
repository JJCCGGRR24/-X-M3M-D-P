
package controllers.actor;

import java.util.Collection;
import java.util.List;

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
import services.ActorService;
import services.FolderService;
import services.MesageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Mesage;

@Controller
@RequestMapping("/message/actor")
public class ActorMesageController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MesageService	mesageService;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ActorMesageController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {
		ModelAndView result;
		Collection<Mesage> ms;
		final Folder folder = this.folderService.findOne(folderId);
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == folder.getActor().getId());
		ms = folder.getMesages();
		result = new ModelAndView("actor/message/list");
		result.addObject("mensajes", ms);
		result.addObject("requestURI", "message/actor/list.do");
		final Actor a = this.loginService.getPrincipalActor();
		result.addObject("actor", a);
		result.addObject("box", folder.getName());
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Mesage message;
		final Actor logueado = this.loginService.getPrincipalActor();

		message = this.mesageService.create();
		message.setSender(logueado);
		for (final Folder f : logueado.getFolders())
			if (f.getName().equals("Outbox"))
				message.setFolder(f);
		result = new ModelAndView("actor/message/edit");
		result.addObject("mesage", message);
		result.addObject("priorities", this.mesageService.getPriorities());
		result.addObject("actors", this.actorService.findAll());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "send")
	public ModelAndView send(@Valid final Mesage mesage, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == mesage.getFolder().getActor().getId());
		if (binding.hasErrors()) {
			result = new ModelAndView("actor/message/edit");
			result.addObject("mesage", mesage);
			result.addObject("actors", this.actorService.findAll());
			result.addObject("priorities", this.mesageService.getPriorities());
		} else
			try {
				this.mesageService.sendMesage(mesage);
				result = new ModelAndView("redirect:../../folder/actor/list.do");
				result.addObject("message", "message.commit.ok");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/message/edit");
				result.addObject("mesage", mesage);
				result.addObject("actors", this.actorService.findAll());
				result.addObject("priorities", this.mesageService.getPriorities());
				result.addObject("message", "message.commit.error");
			}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor log = this.loginService.getPrincipalActor();
		final Mesage message = this.mesageService.findOne(messageId);
		Assert.isTrue(log.getId() == message.getFolder().getActor().getId());
		result = new ModelAndView("actor/message/details");
		result.addObject("mesage", message);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor log = this.loginService.getPrincipalActor();
		final Mesage message = this.mesageService.findOne(messageId);
		Assert.isTrue(log.getId() == message.getFolder().getActor().getId());
		result = this.createMoveModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView move(@Valid final Mesage message, final BindingResult binding) {
		ModelAndView result;
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == message.getFolder().getActor().getId());
		if (binding.hasErrors())
			result = this.createMoveModelAndView(message);
		else
			try {
				this.mesageService.save(message);
				result = new ModelAndView("redirect:../../message/actor/list.do?folderId=" + message.getFolder().getId());
				// result.addObject("message", "message.commit.ok");
				result.addObject("box", message.getFolder().getName());
			} catch (final Throwable oops) {
				result = this.createMoveModelAndView(message, "message.commit.error");
				// result.addObject("mensaje", message);
				// result.addObject("actors", messageService.findAllActors());
				// result.addObject("priorities", messageService.getPriorities());
				// result.addObject("message", "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int messageId) {
		ModelAndView result = null;
		final Mesage message = this.mesageService.findOne(messageId);
		final Folder folder = message.getFolder();
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == message.getFolder().getActor().getId());
		try {
			if (message.getFolder().getName().toLowerCase().equals("trashbox"))
				this.mesageService.delete(message);
			else {
				final Folder f = this.folderService.getTrashbox(this.loginService.getPrincipalActor());
				message.setFolder(f);
				final List<Mesage> lm = f.getMesages();
				lm.add(message);
				f.setMesages(lm);
				this.folderService.save(f);
				this.mesageService.save(message);
			}
			result = new ModelAndView("redirect:../../message/actor/list.do?folderId=" + folder.getId());
			// result.addObject("message", "message.commit.ok");
			result.addObject("box", folder.getName());
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			result = this.createEditModelAndView(message, "message.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Mesage message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Mesage mensaje, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/message/edit");
		result.addObject("mesage", mensaje);
		result.addObject("message", message);
		final List<Folder> folders = mensaje.getFolder().getActor().getFolders();
		folders.remove(mensaje.getFolder());
		result.addObject("folders", folders);

		return result;
	}

	protected ModelAndView createMoveModelAndView(final Mesage message) {
		ModelAndView result;

		result = this.createMoveModelAndView(message, null);

		return result;
	}

	protected ModelAndView createMoveModelAndView(final Mesage mensaje, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/message/move");
		result.addObject("mensaje", mensaje);
		result.addObject("message", message);
		final List<Folder> folders = mensaje.getFolder().getActor().getFolders();
		folders.remove(mensaje.getFolder());
		result.addObject("folders", folders);

		return result;
	}

}
