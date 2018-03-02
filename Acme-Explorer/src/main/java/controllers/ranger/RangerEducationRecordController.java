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
import services.EducationRecordService;
import controllers.AbstractController;
import domain.EducationRecord;

@Controller
@RequestMapping("/educationRecord/ranger")
public class RangerEducationRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculaService		curriculumService;
	@Autowired
	private LoginService			loginService;
	@Autowired
	private EducationRecordService	educationRecordService;


	// Constructors -----------------------------------------------------------

	public RangerEducationRecordController() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// EducationRecord
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/list")
	public ModelAndView listEducation(@RequestParam(required = true) final int curriculumId) {

		ModelAndView result = null;
		Collection<EducationRecord> records = new ArrayList<EducationRecord>();
		Assert.isTrue(this.loginService.getPrincipalActor().equals(this.curriculumService.findOne(curriculumId).getRanger()));
		records = this.curriculumService.findOne(curriculumId).getEducationRecords();
		result = new ModelAndView("record/listEducation");
		result.addObject("records", records);
		result.addObject("curriculumId", curriculumId);
		result.addObject("requestURI", "educationRecord/ranger/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createEducation(@RequestParam(required = true) final int curriculumId) {
		ModelAndView result;
		EducationRecord record;

		record = this.educationRecordService.create(this.curriculumService.findOne(curriculumId));
		result = this.createEditModelAndViewEducation(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editEducation(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord record;

		record = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(record);
		result = this.createEditModelAndViewEducation(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEducation(@Valid final EducationRecord record, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewEducation(record);
		else
			try {
				this.educationRecordService.save(record);
				result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + record.getCurriculum().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewEducation(record, "general.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEducation(final EducationRecord record, final BindingResult binding) {
		ModelAndView result;

		try {
			this.educationRecordService.delete(record);
			result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + record.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewEducation(record, "general.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndViewEducation(final EducationRecord record) {
		ModelAndView result;

		result = this.createEditModelAndViewEducation(record, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewEducation(final EducationRecord record, final String message) {
		ModelAndView result;

		result = new ModelAndView("record/editEducation");
		result.addObject("educationRecord", record);
		result.addObject("message", message);
		result.addObject("curriculumId", record.getCurriculum().getId());

		return result;
	}

}
