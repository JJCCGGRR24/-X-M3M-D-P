
package services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.ProfessionalRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private LoginService				loginService;


	//Tests
	@Test
	public void testDeleteAndCreateAndSave() {
		super.authenticate("ranger");
		System.out.println("----Delete------");
		final ProfessionalRecord mr = ((Ranger) (this.loginService.getPrincipalActor())).getCurricula().getProfessionalRecords().get(0);
		System.out.println(mr);
		this.professionalRecordService.delete(mr);
		System.out.println("FindOne after delete: " + this.professionalRecordService.findOne(mr.getId()));

		final ProfessionalRecord a = this.professionalRecordService.create(mr.getCurriculum());
		System.out.println("");
		System.out.println("----Create & Save------");
		System.out.println(a + " --> " + a.getCompanyName());
		a.setCompanyName("New Company Name");
		final ProfessionalRecord save = this.professionalRecordService.save(a);
		final ProfessionalRecord find = this.professionalRecordService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		System.out.println(save + " --> " + save.getCompanyName());
		System.out.println("");
		super.authenticate(null);
	}
	@Test
	public void testFindAllProfessionalRecord() {

		super.authenticate("ranger");
		final List<ProfessionalRecord> all = (List<ProfessionalRecord>) this.professionalRecordService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final ProfessionalRecord l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneProfessionalRecord() {

		super.authenticate("ranger");
		final List<ProfessionalRecord> all = (List<ProfessionalRecord>) this.professionalRecordService.findAll();
		final ProfessionalRecord al = this.professionalRecordService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
