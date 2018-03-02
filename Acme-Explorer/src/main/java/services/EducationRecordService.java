
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import security.LoginService;
import domain.Actor;
import domain.Curricula;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private ActorService				actorService;


	// Constructors -----------------------------------------------------------
	public EducationRecordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public EducationRecord create(final Curricula curricula) {
		final EducationRecord r = new EducationRecord();
		r.setCurriculum(curricula);
		final List<EducationRecord> educationRecords = curricula.getEducationRecords();
		educationRecords.add(r);
		curricula.setEducationRecords(educationRecords);
		return r;
	}

	public Collection<EducationRecord> findAll() {
		final Collection<EducationRecord> res = this.educationRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public EducationRecord findOne(final int educationRecordId) {
		return this.educationRecordRepository.findOne(educationRecordId);
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(this.checkPrincipal(educationRecord));
		this.detectSpam(educationRecord);
		return this.educationRecordRepository.save(educationRecord);
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.isTrue(this.checkPrincipal(educationRecord));
		this.educationRecordRepository.delete(educationRecord);
	}

	public void flush() {
		this.educationRecordRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final EducationRecord educationRecord) {
		Assert.isTrue(LoginService.getPrincipal().equals(educationRecord.getCurriculum().getRanger().getUserAccount()));
		return true;
	}

	private boolean detectSpam(final EducationRecord record) {
		boolean res = false;
		for (final String sw : this.configurationService.find().getSpamWords()) {
			if (record.getComments() != null)
				if (record.getComments().contains(sw)) {
					res = true;
					final Actor a = record.getCurriculum().getRanger();
					a.setSuspicious(res);
					this.actorService.save(a);
					break;
				}
			if (record.getInstitution().contains(sw) || record.getTitle().contains(sw)) {
				res = true;
				final Actor a = record.getCurriculum().getRanger();
				a.setSuspicious(res);
				this.actorService.save(a);
				break;
			}
		}
		return res;
	}

}
