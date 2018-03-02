
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

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.SocialIdentityService;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity/actor")
public class ActorSocialIdentityController {

	//Services ---------------------------------------------------------------

	@Autowired
	private SocialIdentityService	socialEntityService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private LoginService			loginService;


	// Constructors -----------------------------------------------------------
	public ActorSocialIdentityController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int actorId) {

		final List<Authority> la = (List<Authority>) this.loginService.getPrincipalActor().getUserAccount().getAuthorities();
		final String aut = la.get(0).getAuthority();
		Assert.isTrue(this.actorService.findOne(actorId).equals(this.loginService.getPrincipalActor()) || aut.equals("ADMIN"));
		final ModelAndView result;
		final Collection<SocialIdentity> socialIdentities;

		final Actor a = this.actorService.findOne(actorId);
		Assert.notNull(a);
		socialIdentities = a.getSocialIdentities();
		Assert.notNull(socialIdentities);

		result = new ModelAndView("socialIdentity/listSocialId");
		result.addObject("socialIdentities", socialIdentities);
		//		try {
		//			result.addObject("owner", false);
		result.addObject("actor", a);
		//		} catch (final Exception e) {
		//			result.addObject("owner", false);
		//		}

		result.addObject("requestURI", "socialIdentity/actor/list.do");

		return result;
	}

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		final Actor a = this.loginService.getPrincipalActor();
		final ModelAndView result;
		final Collection<SocialIdentity> socialIdentities;
		Assert.notNull(a);
		socialIdentities = a.getSocialIdentities();
		Assert.notNull(socialIdentities);

		result = new ModelAndView("socialIdentity/listSocialId");
		result.addObject("socialIdentities", socialIdentities);
		//		try {
		//			result.addObject("owner", false);
		result.addObject("actor", a);
		//		} catch (final Exception e) {
		//			result.addObject("owner", false);
		//		}

		result.addObject("requestURI", "socialIdentity/actor/myList.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final SocialIdentity socialId = this.socialEntityService.create();
		result = this.createEditModelAndView(socialId);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialIdentityId) {

		final ModelAndView result;

		final SocialIdentity socialId = this.socialEntityService.findOne(socialIdentityId);
		Assert.notNull(socialId);
		result = this.createEditModelAndView(socialId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialIdentity socialIdentity, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialIdentity);
		else
			try {
				this.socialEntityService.save(socialIdentity);
				result = new ModelAndView("redirect:list.do?actorId=" + socialIdentity.getActor().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialIdentity, "socialId.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialIdentity socialIdentity, final BindingResult binding) {

		ModelAndView result;

		try {

			this.socialEntityService.delete(socialIdentity);
			result = new ModelAndView("redirect:list.do?actorId=" + socialIdentity.getActor().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");

		}
		return result;
	}

	//Auxiliars-------------------------
	protected ModelAndView createEditModelAndView(final SocialIdentity socialId) {
		ModelAndView result;

		result = this.createEditModelAndView(socialId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final SocialIdentity socialId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("socialIdentity/editSocialId");
		result.addObject("socialIdentity", socialId);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "socialIdentity/actor/edit.do");

		return result;
	}

}
