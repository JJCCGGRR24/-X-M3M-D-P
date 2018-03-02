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
import services.ProfessionalRecordService;
import controllers.AbstractController;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/ranger")
public class RangerProfessionalRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculaService			curriculumService;
	@Autowired
	private LoginService				loginService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	// Constructors -----------------------------------------------------------

	public RangerProfessionalRecordController() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// Professional Record
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/list")
	public ModelAndView listProfessional(@RequestParam(required = true) final int curriculumId) {

		ModelAndView result = null;
		Collection<ProfessionalRecord> records = new ArrayList<ProfessionalRecord>();
		Assert.isTrue(this.loginService.getPrincipalActor().equals(this.curriculumService.findOne(curriculumId).getRanger()));
		records = this.curriculumService.findOne(curriculumId).getProfessionalRecords();
		result = new ModelAndView("record/listProfessional");
		result.addObject("records", records);
		result.addObject("curriculumId", curriculumId);
		result.addObject("requestURI", "professionalRecord/ranger/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createProfessional(@RequestParam(required = true) final int curriculumId) {
		ModelAndView result;
		ProfessionalRecord record;

		record = this.professionalRecordService.create(this.curriculumService.findOne(curriculumId));
		result = this.createEditModelAndViewProfessional(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editProfessional(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord record;

		record = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(record);
		result = this.createEditModelAndViewProfessional(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveProfessional(@Valid final ProfessionalRecord record, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewProfessional(record);
		else
			try {
				this.professionalRecordService.save(record);
				result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + record.getCurriculum().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewProfessional(record, "general.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteProfessional(final ProfessionalRecord record, final BindingResult binding) {
		ModelAndView result;

		try {
			this.professionalRecordService.delete(record);
			result = new ModelAndView("redirect:/curricula/details.do?curriculumId=" + record.getCurriculum().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewProfessional(record, "general.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndViewProfessional(final ProfessionalRecord record) {
		ModelAndView result;

		result = this.createEditModelAndViewProfessional(record, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewProfessional(final ProfessionalRecord record, final String message) {
		ModelAndView result;

		result = new ModelAndView("record/editProfessional");
		result.addObject("professionalRecord", record);
		result.addObject("message", message);
		result.addObject("curriculumId", record.getCurriculum().getId());

		return result;
	}

}
