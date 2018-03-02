
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String			defaultCountry;
	private List<String>	spamWords;
	private double			taxVAT;
	private double			timeCache;
	private int				numResults;
	private String			welcomeEn;
	private String			welcomeEs;
	private String			welcome;


	@NotBlank
	public String getDefaultCountry() {
		return this.defaultCountry;
	}

	public void setDefaultCountry(final String defaultCountry) {
		this.defaultCountry = defaultCountry;
	}

	@NotEmpty
	@NotNull
	@ElementCollection
	@Value("#{'${list.of.strings}'.split(',')}")
	public List<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final List<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotNull
	public double getTaxVAT() {
		return this.taxVAT;
	}

	public void setTaxVAT(final double taxVAT) {
		this.taxVAT = taxVAT;
	}

	@NotNull
	@Range(min = (long) 1.0, max = (long) 24.0)
	public double getTimeCache() {
		return this.timeCache;
	}

	public void setTimeCache(final double timeCache) {
		this.timeCache = timeCache;
	}

	@NotNull
	@Range(min = 10, max = 100)
	public int getNumResults() {
		return this.numResults;
	}

	public void setNumResults(final int numResults) {
		this.numResults = numResults;
	}

	@NotNull
	public String getWelcomeEs() {
		return this.welcomeEs;
	}

	public void setWelcomeEs(final String welcomeEs) {
		this.welcomeEs = welcomeEs;
	}

	@NotNull
	public String getWelcomeEn() {
		return this.welcomeEn;
	}

	public void setWelcomeEn(final String welcomeEn) {
		this.welcomeEn = welcomeEn;
	}

	@NotNull
	public String getWelcome() {
		return this.welcome;
	}

	public void setWelcome(final String welcome) {
		this.welcome = welcome;
	}
	// Relationships ----------------------------------------------------------

}
