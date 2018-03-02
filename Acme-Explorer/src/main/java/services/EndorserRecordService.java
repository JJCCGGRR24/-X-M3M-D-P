
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import domain.Actor;
import domain.Curricula;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private ActorService				actorService;


	// Constructors -----------------------------------------------------------
	public EndorserRecordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public EndorserRecord create(final Curricula curricula) {
		final EndorserRecord r = new EndorserRecord();
		r.setCurriculum(curricula);
		final List<EndorserRecord> endorserRecords = curricula.getEndorserRecords();
		endorserRecords.add(r);
		curricula.setEndorserRecords(endorserRecords);
		return r;
	}

	public Collection<EndorserRecord> findAll() {
		final Collection<EndorserRecord> res = this.endorserRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public EndorserRecord findOne(final int endorserRecordId) {
		return this.endorserRecordRepository.findOne(endorserRecordId);
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(this.checkPrincipal(endorserRecord));
		this.detectSpam(endorserRecord);
		return this.endorserRecordRepository.save(endorserRecord);
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.isTrue(this.checkPrincipal(endorserRecord));
		this.endorserRecordRepository.delete(endorserRecord);
	}

	public void flush() {
		this.endorserRecordRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final EndorserRecord endorserRecord) {
		Assert.isTrue(LoginService.getPrincipal().equals(endorserRecord.getCurriculum().getRanger().getUserAccount()));
		return true;
	}

	private boolean detectSpam(final EndorserRecord record) {
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
			if (record.getName().contains(sw) || record.getEmail().contains(sw)) {
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
