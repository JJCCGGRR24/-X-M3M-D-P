
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NulpRepository;
import security.LoginService;
import domain.Manager;
import domain.Nulp;

@Service
@Transactional
public class NulpService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private NulpRepository	nulpRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService	loginService;

	@Autowired
	private TripService		tripService;


	// Constructors -----------------------------------------------------------
	public NulpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Nulp create() {
		final Nulp nulp = new Nulp();
		nulp.setCode(this.generateCode());

		return nulp;
	}

	public Nulp save(final Nulp nulp) {
		Assert.notNull(nulp);
		Assert.isTrue(this.checkPrincipal(nulp));
		return this.nulpRepository.save(nulp);
	}

	public void delete(final Nulp nulp) {
		//		Assert.notNull(nulp);
		//		Assert.isTrue(this.checkPrincipal(nulp));
		//		final List<Nulp> nulps = nulp.getTrip().getNulps();
		//		nulps.remove(nulp);
		//		nulp.getTrip().setNulps(nulps);
		this.nulpRepository.delete(nulp);
	}
	public Nulp findOne(final int nulpId) {
		return this.nulpRepository.findOne(nulpId);
	}

	public Collection<Nulp> findAll() {
		return this.nulpRepository.findAll();
	}

	public void flush() {
		this.nulpRepository.flush();
	}

	// Other business methods -------------------------------------------------

	//	public List<Nulp> getNulpsByManager(final Manager man) {
	//		return this.nulpRepository.getNulpsByManager(man);
	//	}
	//
	//	public List<Nulp> getNulpsByAuditor(final Auditor man) {
	//		return this.nulpRepository.getNulpsByAuditor(man);
	//	}
	//
	//	public List<Nulp> getNulpsByExplorer(final Explorer explorer) {
	//		return this.nulpRepository.getNulpsByExplorer(explorer);
	//	}
	//
	//	public List<Nulp> getNulpsByRanger(final Ranger ranger) {
	//		return this.nulpRepository.getNulpsByRanger(ranger);
	//	}

	public boolean checkPrincipal(final Nulp nulp) {
		boolean res = false;
		final Manager man = (Manager) this.loginService.getPrincipalActor();
		Assert.isTrue(nulp.getTrip().getManager().equals(man));
		res = true;
		return res;
	}

	@SuppressWarnings("deprecation")
	public String generateCode() {
		final Date current = new Date();
		final Integer dayInt = current.getDate();
		String day = dayInt.toString();
		if (day.length() == 1)
			day = "0" + day;
		final Integer monthInt = current.getMonth() + 1;
		String month = monthInt.toString();
		if (month.length() == 1)
			month = "0" + month;
		final Integer yearInt = current.getYear();
		final String year = yearInt.toString().substring(1, 3);
		return year + month + day + ":" + NulpService.generateStringAux();

	}

	private static String generateStringAux() {
		final int length = 6;
		final String characters = "_0123456789abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < 6; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public Collection<Nulp> getNulpsVisibles(final int tripId) {
		return this.nulpRepository.getNulpsVisibles(tripId);
	}

	public Double querieA() {
		return this.nulpRepository.querieA();
	}

	public List<Manager> querieB() {
		return this.nulpRepository.querieB();
	}

}
