
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public CreditCardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public CreditCard create() {
		final CreditCard cc = new CreditCard();
		return cc;
	}

	public Collection<CreditCard> findAll() {
		final Collection<CreditCard> res = this.creditCardRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public CreditCard findOne(final int ccId) {
		return this.creditCardRepository.findOne(ccId);
	}

	public void flush() {
		this.creditCardRepository.flush();
	}

	public CreditCard save(final CreditCard a) {
		Assert.notNull(a);
		return this.creditCardRepository.save(a);
	}

}
