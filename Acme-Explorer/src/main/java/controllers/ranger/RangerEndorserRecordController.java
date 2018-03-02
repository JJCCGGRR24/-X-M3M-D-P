/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.ranger;

import java.util.ArrayList;
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
import services.CurriculaService;
import services.EndorserRecordService;
import controllers.AbstractController;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/ranger")
public class RangerEndorserRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculaService		curriculumService;
	@Autowired
	private LoginService			loginService;

	@Autowired
	private EndorserRecordService	endorserRecordService;


	// Constructors -----------------------------------------------------------

	public RangerEndorserRecordController() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// Endorser Record
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/list")
	public ModelAndView listEndorser(@RequestParam(required = true) final int curriculumId) {

		ModelAndView result = null;
		Collection<EndorserRecord> records = new ArrayList<EndorserRecord>();
		Assert.isTrue(this.loginService.getPrincipalActor().equals(this.curriculumService.findOne(curriculumId).getRanger()));
		records = this.curriculumService.findOne(curriculumId).getEndorserRecords();
		result = new ModelAndView("record/listEndorser");
		result.addObject("records", records);
		result.addObject("curriculumId", curriculumId);
		result.addObject("requestURI", "endorserRecord/ranger/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createEndorser(@RequestParam(required = true) final int curriculumId) {
		ModelAndView result;
		EndorserRecord record;

		record = this.endorserRecordService.create(this.curriculumService.findOne(curriculumId));
		result = this.createEditModelAndViewEndorser(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editEndorser(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord record;

		record = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(record);
		result = this.createEditModelAndViewEndorser(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEndorser(@Valid final EndorserRecord record, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewEndorser(record);
		else
			try {
				this.endorserRecordService.save(record);
				result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + record.getCurriculum().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewEndorser(record, "general.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEndorser(final EndorserRecord record, final BindingResult binding) {
		ModelAndView result;

		try {
			this.endorserRecordService.delete(record);
			result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + record.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewEndorser(record, "general.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndViewEndorser(final EndorserRecord record) {
		ModelAndView result;

		result = this.createEditModelAndViewEndorser(record, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewEndorser(final EndorserRecord record, final String message) {
		ModelAndView result;

		result = new ModelAndView("record/editEndorser");
		result.addObject("endorserRecord", record);
		result.addObject("message", message);
		result.addObject("curriculumId", record.getCurriculum().getId());

		return result;
	}

}
