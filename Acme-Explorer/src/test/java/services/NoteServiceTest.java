
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
import domain.Auditor;
import domain.Note;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	@Autowired
	private NoteService		noteService;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private TripService		tripService;


	//Tests
	@Test
	public void testDeleteAndCreateAndSave() {
		super.authenticate("auditor");
		System.out.println("----Delete------");
		final Note mr = ((Auditor) (this.loginService.getPrincipalActor())).getNotes().get(0);
		System.out.println(mr);
		this.noteService.delete(mr);
		System.out.println("FindOne after delete: " + this.noteService.findOne(mr.getId()));

		final List<Trip> lts = (List<Trip>) this.tripService.findAll();
		final Note a = this.noteService.create(lts.get(0));
		System.out.println("");
		System.out.println("----Create & Save------");
		System.out.println(a + " --> " + a.getComment());
		a.setComment("New Comment");
		final Note save = this.noteService.save(a);
		final Note find = this.noteService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		System.out.println(save + " --> " + save.getComment());
		System.out.println("");
		super.authenticate(null);
	}
	@Test
	public void testFindAllNote() {

		super.authenticate("auditor");
		final List<Note> all = (List<Note>) this.noteService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Note l : all)
			System.out.println(l);

		System.out.println("");

	}

	@Test
	public void testFindOneNote() {

		super.authenticate("auditor");
		final List<Note> all = (List<Note>) this.noteService.findAll();
		final Note al = this.noteService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);
		System.out.println("");
	}

}
