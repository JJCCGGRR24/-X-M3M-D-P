
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
import domain.MiscellaneousRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private LoginService				loginService;


	//Tests
	@Test
	public void testDeleteAndCreateAndSave() {
		super.authenticate("ranger");
		System.out.println("----Delete------");
		final MiscellaneousRecord mr = ((Ranger) (this.loginService.getPrincipalActor())).getCurricula().getMiscellaneousRecords().get(0);
		System.out.println(mr);
		this.miscellaneousRecordService.delete(mr);
		System.out.println("FindOne after delete: " + this.miscellaneousRecordService.findOne(mr.getId()));

		final MiscellaneousRecord a = this.miscellaneousRecordService.create(mr.getCurriculum());
		System.out.println("");
		System.out.println("----Create & Save------");
		System.out.println(a + " --> " + a.getTitle());
		a.setTitle("New Title");
		final MiscellaneousRecord save = this.miscellaneousRecordService.save(a);
		final MiscellaneousRecord find = this.miscellaneousRecordService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		System.out.println(save + " --> " + save.getTitle());
		System.out.println("");
		super.authenticate(null);
	}
	@Test
	public void testFindAllMiscellaneousRecord() {

		super.authenticate("ranger");
		final List<MiscellaneousRecord> all = (List<MiscellaneousRecord>) this.miscellaneousRecordService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final MiscellaneousRecord l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneMiscellaneousRecord() {

		super.authenticate("ranger");
		final List<MiscellaneousRecord> all = (List<MiscellaneousRecord>) this.miscellaneousRecordService.findAll();
		final MiscellaneousRecord al = this.miscellaneousRecordService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);

		System.out.println("");
	}

}
