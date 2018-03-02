
package services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ConfigurationServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private ConfigurationService	configurationService;


	//Tests

	@Test
	public void saveConfiguration() {

		super.authenticate("admin");
		final Configuration c = this.configurationService.find();
		c.setDefaultCountry("+32");
		final Configuration cSave = this.configurationService.save(c);
		Assert.isTrue(cSave.getDefaultCountry().equals("+32"));
		super.authenticate(null);

	}
	@Test
	public void testFindConfiguration() {

		final Configuration c = this.configurationService.find();
		Assert.isTrue(c.getDefaultCountry().equals("+34") && c.getTaxVAT() == 0.21);

	}

	public void testFindAllConfiguration() {

		final List<Configuration> configs = (List<Configuration>) this.configurationService.findAll();
		Assert.isTrue(configs.size() == 1);
	}
}
