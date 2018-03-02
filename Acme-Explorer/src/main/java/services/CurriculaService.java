
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.LoginService;
import domain.Actor;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class CurriculaService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CurriculaRepository			curriculaRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TripService					tripService;

	@Autowired
	private LoginService				loginService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private ActorService				actorService;


	// Constructors -----------------------------------------------------------
	public CurriculaService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Curricula create() {
		final Curricula r = new Curricula();
		final Ranger rg = (Ranger) this.loginService.getPrincipalActor();
		r.setRanger(rg);
		final List<EducationRecord> educationRecords = new ArrayList<>();
		r.setEducationRecords(educationRecords);
		final List<EndorserRecord> endorserRecords = new ArrayList<>();
		r.setEndorserRecords(endorserRecords);
		final List<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();
		r.setMiscellaneousRecords(miscellaneousRecords);
		final List<ProfessionalRecord> professionalRecords = new ArrayList<>();
		r.setProfessionalRecords(professionalRecords);
		r.setTicker(this.tripService.generateTicker());
		return r;
	}

	public Collection<Curricula> findAll() {
		final Collection<Curricula> res = this.curriculaRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Curricula findOne(final int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	public Curricula save(final Curricula curricula) {
		Assert.notNull(curricula);
		if (curricula.getId() > 0)
			Assert.isTrue(this.checkPrincipal(curricula));
		this.detectSpam(curricula);
		return this.curriculaRepository.save(curricula);
	}

	public void delete(final Curricula curricula) {
		Assert.isTrue(this.checkPrincipal(curricula));
		for (final EducationRecord e : curricula.getEducationRecords())
			this.educationRecordService.delete(e);
		for (final EndorserRecord e : curricula.getEndorserRecords())
			this.endorserRecordService.delete(e);
		for (final ProfessionalRecord e : curricula.getProfessionalRecords())
			this.professionalRecordService.delete(e);
		for (final MiscellaneousRecord e : curricula.getMiscellaneousRecords())
			this.miscellaneousRecordService.delete(e);
		this.curriculaRepository.delete(curricula);
	}

	public void flush() {
		this.curriculaRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final Curricula curricula) {
		Assert.isTrue(curricula.getRanger().getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}

	private boolean detectSpam(final Curricula t) {
		boolean res = false;
		for (final String sw : this.configurationService.find().getSpamWords())
			if (t.getPersonalRecord().getFullName().contains(sw) || t.getPersonalRecord().getEmail().contains(sw) || t.getPersonalRecord().getLinkedProfile().contains(sw)) {
				res = true;
				final Actor a = t.getRanger();
				a.setSuspicious(res);
				this.actorService.save(a);
				break;
			}
		return res;
	}

}
