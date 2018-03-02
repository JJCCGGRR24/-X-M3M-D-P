
package controllers.explorer;

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

import security.Authority;
import security.LoginService;
import services.ContactService;
import services.ExplorerService;
import controllers.AbstractController;
import domain.Contact;
import domain.Explorer;

@Controller
@RequestMapping("/contact/explorer")
public class ExplorerContactsController extends AbstractController {

	//Services ---------------------------------------------------------------
	@Autowired
	private ContactService	contactService;
	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public ExplorerContactsController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int explorerId) {
		final List<Authority> la = (List<Authority>) this.loginService.getPrincipalActor().getUserAccount().getAuthorities();
		final String aut = la.get(0).getAuthority();
		Assert.isTrue(this.explorerService.findOne(explorerId).equals(this.loginService.getPrincipalActor()) || aut.equals("ADMIN"));
		final ModelAndView res = new ModelAndView("contact/list");
		final Explorer e = this.explorerService.findOne(explorerId);
		res.addObject("uri", "contact/explorer/list.do");
		res.addObject("contacts", e.getContacts());
		res.addObject("actor", e);
		return res;
	}

	// myList ---------------------------------------------------------------
	@RequestMapping("/myList")
	public ModelAndView mylist() {
		final ModelAndView res = new ModelAndView("contact/list");
		final Explorer e = (Explorer) this.loginService.getPrincipalActor();
		res.addObject("uri", "contact/explorer/myList.do");
		res.addObject("contacts", e.getContacts());
		res.addObject("actor", e);
		return res;
	}
	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final Contact contact = this.contactService.create();
		result = this.createEditModelAndView(contact);
		return result;
	}

	//Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int contactId) {
		final ModelAndView result;
		Assert.isTrue(this.loginService.getPrincipalActor().getId() == this.contactService.findOne(contactId).getExplorer().getId());
		final Contact contact = this.contactService.findOne(contactId);
		Assert.notNull(contact);
		result = this.createEditModelAndView(contact);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Contact contact, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(contact);
		else
			try {
				this.contactService.save(contact);
				result = new ModelAndView("redirect:myList.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(contact, "template.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Contact contact, final BindingResult binding) {

		ModelAndView result;

		try {
			this.contactService.delete(contact);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(contact, "contact.commit.error");

		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Contact contact) {
		ModelAndView result;

		result = this.createEditModelAndView(contact, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Contact contact, final String message) {
		ModelAndView result;

		result = new ModelAndView("contact/edit");
		result.addObject("contact", contact);
		result.addObject("message", message);
		result.addObject("requestURI", "contact/explorer/edit.do");

		return result;
	}
}
