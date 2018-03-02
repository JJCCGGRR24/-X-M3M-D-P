
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	//	public Configuration create() {
	//		final Configuration r = new Configuration();
	//		return r;
	//	}

	public Collection<Configuration> findAll() {
		final Collection<Configuration> res = this.configurationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	//	public Configuration findOne(final int configurationId) {
	//		return this.configurationRepository.findOne(configurationId);
	//	}

	public Configuration find() {
		final List<Configuration> res = this.configurationRepository.findAll();
		return res.get(0);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);

		final List<String> l = new ArrayList<>();
		for (final String a : configuration.getSpamWords())
			if (a.trim().length() == 0)
				l.add(a);
		configuration.getSpamWords().removeAll(l);
		return this.configurationRepository.save(configuration);
	}

	//	public void delete(final Configuration configuration) {
	//		this.configurationRepository.delete(configuration);
	//	}

	public void flush() {
		this.configurationRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
