
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import domain.Actor;
import domain.Curricula;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationService			configurationService;

	@Autowired
	private ActorService					actorService;


	// Constructors -----------------------------------------------------------
	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public MiscellaneousRecord create(final Curricula curricula) {
		final MiscellaneousRecord r = new MiscellaneousRecord();
		r.setCurriculum(curricula);

		final List<MiscellaneousRecord> miscellaneousRecordCurriculum = curricula.getMiscellaneousRecords();
		miscellaneousRecordCurriculum.add(r);
		curricula.setMiscellaneousRecords(miscellaneousRecordCurriculum);

		return r;
	}

	public Collection<MiscellaneousRecord> findAll() {
		final Collection<MiscellaneousRecord> res = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		return this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(this.checkPrincipal(miscellaneousRecord));
		this.detectSpam(miscellaneousRecord);
		return this.miscellaneousRecordRepository.save(miscellaneousRecord);
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.isTrue(this.checkPrincipal(miscellaneousRecord));
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	public void flush() {
		this.miscellaneousRecordRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final MiscellaneousRecord obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getCurriculum().getRanger().getUserAccount()))
			res = true;
		return res;
	}

	private boolean detectSpam(final MiscellaneousRecord record) {
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
			if (record.getTitle().contains(sw)) {
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
