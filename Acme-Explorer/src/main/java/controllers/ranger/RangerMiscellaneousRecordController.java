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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/ranger")
public class RangerMiscellaneousRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculaService			curriculumService;
	@Autowired
	private LoginService				loginService;
	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	// Constructors -----------------------------------------------------------

	public RangerMiscellaneousRecordController() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// Miscellaneous Record
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/list")
	public ModelAndView listMiscellaneous(@RequestParam(required = true) final int curriculumId) {

		ModelAndView result = null;
		Collection<MiscellaneousRecord> records = new ArrayList<MiscellaneousRecord>();
		Assert.isTrue(this.loginService.getPrincipalActor().equals(this.curriculumService.findOne(curriculumId).getRanger()));
		records = this.curriculumService.findOne(curriculumId).getMiscellaneousRecords();
		result = new ModelAndView("record/listMiscellaneous");
		result.addObject("records", records);
		result.addObject("curriculumId", curriculumId);
		result.addObject("requestURI", "miscellaneousRecord/ranger/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createMiscellaneous(@RequestParam(required = true) final int curriculumId) {
		ModelAndView result;
		MiscellaneousRecord record;

		record = this.miscellaneousRecordService.create(this.curriculumService.findOne(curriculumId));
		result = this.createEditModelAndViewMiscellaneous(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editMiscellaneous(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord record;

		record = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		Assert.notNull(record);
		result = this.createEditModelAndViewMiscellaneous(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMiscellaneous(@Valid final MiscellaneousRecord record, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewMiscellaneous(record);
		else
			try {
				this.miscellaneousRecordService.save(record);
				result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + record.getCurriculum().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewMiscellaneous(record, "general.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteMiscellaneous(final MiscellaneousRecord record, final BindingResult binding) {
		ModelAndView result;

		try {
			this.miscellaneousRecordService.delete(record);
			result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + record.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewMiscellaneous(record, "general.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndViewMiscellaneous(final MiscellaneousRecord record) {
		ModelAndView result;

		result = this.createEditModelAndViewMiscellaneous(record, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewMiscellaneous(final MiscellaneousRecord record, final String message) {
		ModelAndView result;

		result = new ModelAndView("record/editMiscellaneous");
		result.addObject("miscellaneousRecord", record);
		result.addObject("message", message);
		result.addObject("curriculumId", record.getCurriculum().getId());

		return result;
	}

}
