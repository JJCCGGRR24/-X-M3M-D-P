
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import domain.Actor;
import domain.Curricula;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationService			configurationService;

	@Autowired
	private ActorService					actorService;


	// Constructors -----------------------------------------------------------
	public ProfessionalRecordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public ProfessionalRecord create(final Curricula curriculum) {
		final ProfessionalRecord r = new ProfessionalRecord();
		r.setCurriculum(curriculum);

		final List<ProfessionalRecord> professionalRecordsCurriculum = curriculum.getProfessionalRecords();
		professionalRecordsCurriculum.add(r);
		curriculum.setProfessionalRecords(professionalRecordsCurriculum);

		return r;
	}

	public Collection<ProfessionalRecord> findAll() {
		final Collection<ProfessionalRecord> res = this.professionalRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public ProfessionalRecord findOne(final int professionalRecordId) {
		return this.professionalRecordRepository.findOne(professionalRecordId);
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(this.checkPrincipal(professionalRecord));
		this.detectSpam(professionalRecord);
		return this.professionalRecordRepository.save(professionalRecord);
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.isTrue(this.checkPrincipal(professionalRecord));
		this.professionalRecordRepository.delete(professionalRecord);
	}

	public void flush() {
		this.professionalRecordRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final ProfessionalRecord obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getCurriculum().getRanger().getUserAccount()))
			res = true;
		return res;
	}

	private boolean detectSpam(final ProfessionalRecord record) {
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
			if (record.getCompanyName().contains(sw) || record.getRole().contains(sw)) {
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
