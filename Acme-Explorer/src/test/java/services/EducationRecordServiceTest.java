
package services;

import java.util.Date;
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
import domain.EducationRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private LoginService			loginService;


	//Tests
	@Test
	public void testDeleteAndCreateAndSave() {
		super.authenticate("ranger");
		System.out.println("----Delete------");
		final EducationRecord mr = ((Ranger) (this.loginService.getPrincipalActor())).getCurricula().getEducationRecords().get(0);
		System.out.println(mr);
		this.educationRecordService.delete(mr);
		System.out.println("FindOne after delete: " + this.educationRecordService.findOne(mr.getId()));

		final EducationRecord a = this.educationRecordService.create(mr.getCurriculum());
		System.out.println("");
		System.out.println("----Create & Save------");
		a.setInstitution("aaaaaaaaaaa");
		a.setTitle("aaaaaaa");
		a.setEndDate(new Date());
		a.setStartDate(new Date());
		System.out.println(a + " --> " + a.getTitle());
		final EducationRecord save = this.educationRecordService.save(a);
		final EducationRecord find = this.educationRecordService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		System.out.println(save + " --> " + save.getTitle());
		System.out.println("");
		super.authenticate(null);
	}
	@Test
	public void testFindAllEducationRecord() {

		super.authenticate("ranger");
		final List<EducationRecord> all = (List<EducationRecord>) this.educationRecordService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final EducationRecord l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneEducationRecord() {

		super.authenticate("ranger");
		final List<EducationRecord> all = (List<EducationRecord>) this.educationRecordService.findAll();
		final EducationRecord al = this.educationRecordService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
