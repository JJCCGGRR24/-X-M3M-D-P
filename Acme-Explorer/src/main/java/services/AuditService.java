
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import security.LoginService;
import domain.Audit;
import domain.Auditor;
import domain.Trip;

@Service
@Transactional
public class AuditService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AuditRepository	auditRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public AuditService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Audit create(final Trip trip) {
		final Audit r = new Audit();
		r.setAttachments(new ArrayList<String>());
		r.setAuditor((Auditor) this.loginService.getPrincipalActor());
		r.setFinalMode(false);
		r.setMoment(new Date(System.currentTimeMillis() - 1000));
		r.setTrip(trip);
		final List<Audit> audits = trip.getAudits();
		audits.add(r);
		trip.setAudits(audits);
		return r;
	}

	public Collection<Audit> findAll() {
		final Collection<Audit> res = this.auditRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Audit findOne(final int auditId) {
		return this.auditRepository.findOne(auditId);
	}

	public Audit save(final Audit audit) {
		Assert.notNull(audit);
		//Id = 0, ó (id != 0 y modoborrador), ó (id!=0 y noborrador y borrador en BBDD)
		//Comprobamos que el audit que guardamos tenga:
		//El id igual a 0, y por tanto no esté aún en BBDD		
		//EL id distinto de 0, pero en BBDD esté en modo borrador y no importe como lo actualizas.
		//----------Lo unico que importa es que en la BBDD este en modo borrador------------
		Assert.isTrue(audit.getId() == 0 || ((audit.getId() != 0 && this.findOne(audit.getId()).getFinalMode() == false)));
		final List<String> l = new ArrayList<>();
		for (final String a : audit.getAttachments())
			if (a.trim().length() == 0)
				l.add(a);
		audit.getAttachments().removeAll(l);
		return this.auditRepository.save(audit);
	}

	public void delete(final Audit audit) {
		Assert.isTrue(this.checkPrincipal(audit));
		Assert.isTrue(audit.getFinalMode() == false);
		this.auditRepository.delete(audit);
	}

	public void flush() {
		this.auditRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final Audit audit) {
		Assert.isTrue(LoginService.getPrincipal().equals(audit.getAuditor().getUserAccount()));
		return true;

	}
	public List<Double> queryB2() {
		final List<Double> l = new ArrayList<Double>();
		final Object[] o = this.auditRepository.queryB2();
		l.add((Double) o[0]);
		l.add((Double) o[1]);
		l.add((Double) o[2]);
		l.add((Double) o[3]);
		return l;
	}

}
