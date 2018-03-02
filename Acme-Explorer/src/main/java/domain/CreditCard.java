
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity {

	// Attributes —---------------------------------------------------------—
	private String	holderName;
	private String	brandName;
	private String	number;
	private int		expirationYear;
	private int		expirationMonth;
	private int		CVV;


	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}
	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}
	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}
	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}
	@NotBlank
	@CreditCardNumber
	@Pattern(regexp = "^\\d{16}$")
	public String getNumber() {
		return this.number;
	}
	public void setNumber(final String number) {
		this.number = number;
	}
	@NotNull
	@Range(min = 2000, max = 3000)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}
	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}
	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCVV() {
		return this.CVV;
	}
	public void setCVV(final Integer CVV) {
		this.CVV = CVV;
	}


	// Relationships —---------------------------------------------------------—

	private Application	application;


	//	private Sponsorship	sponsorship;
	//
	//
	@Valid
	@OneToOne(optional = true)
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	//	@Valid
	//	@OneToOne(optional = true, mappedBy = "creditCard")
	//	public Sponsorship getSponsorship() {
	//		return this.sponsorship;
	//	}
	//
	//	public void setSponsorship(final Sponsorship sponsorship) {
	//		this.sponsorship = sponsorship;
	//	}

}
