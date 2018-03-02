
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
import domain.Actor;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private FolderService	folderService;

	// Service under test
	@Autowired
	private ActorService	actorService;

	@Autowired
	private LoginService	loginService;


	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		final List<Actor> as = (List<Actor>) this.actorService.findAll();
		final Actor a = as.get(0);
		this.authenticate(a.getUserAccount().getUsername());
		final Folder f = this.folderService.create();
		Assert.isNull(f.getName());
		Assert.notNull(f.getMesages());
		Assert.notNull(f.getModifiable());
		this.unauthenticate();
		System.out.println("");
	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final List<Folder> lc = (List<Folder>) this.folderService.findAll();
		System.out.println(this.folderService.findOne(lc.get(0).getId()));
		System.out.println("");
	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final List<Folder> lc = (List<Folder>) this.folderService.findAll();
		Assert.notNull(lc);
		System.out.println(lc);
		System.out.println("");
	}

	@Test
	public void testSaveAndChangeFolderName() {
		System.out.println("========== testSave&ChangeFolderName() ==========");
		final List<Folder> lc = (List<Folder>) this.folderService.findAll();
		final Folder f = this.folderService.findOne(lc.get(0).getId());
		final String s = f.getActor().getUserAccount().getUsername();
		super.authenticate(s);
		System.out.println("Nombre antes: " + f.getName());
		f.setModifiable(true);
		this.folderService.changeFolderName(f, "NUEVA");
		final Folder saved = this.folderService.save(f);
		System.out.println("Nombre después: " + saved.getName());
		super.authenticate(null);
		System.out.println("");
	}
	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");
		final List<Folder> lc = (List<Folder>) this.folderService.findAll();
		final Folder f = this.folderService.findOne(lc.get(0).getId());
		final String s = f.getActor().getUserAccount().getUsername();
		super.authenticate(s);
		System.out.println("Nombre antes: " + f.getName());
		f.setName("NUEVA");
		f.setModifiable(true);
		this.folderService.delete(f);
		System.out.println("Nombre después: " + this.folderService.findOne(lc.get(0).getId()));
		super.authenticate(null);
		System.out.println("");
	}

	@Test
	public void testAddFolders() {
		System.out.println("========== testAddFolders() ==========");
		super.authenticate("auditor");
		final List<Folder> lc = this.loginService.getPrincipalActor().getFolders();
		for (final Folder f : lc) {
			f.setModifiable(true);
			this.folderService.delete(f);
		}
		System.out.println("Nº de carpetas de " + this.loginService.getPrincipalActor().getSurname() + ": " + lc.size());
		this.folderService.addFolders(this.loginService.getPrincipalActor());
		System.out.println("Añadimos carpetas");
		System.out.println("Nº de carpetas de " + this.loginService.getPrincipalActor().getSurname() + ": " + lc.size());
		super.authenticate(null);
		System.out.println("");
	}
}
