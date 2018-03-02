
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
import domain.Mesage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MesageServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private MesageService	messageService;
	@Autowired
	private FolderService	folderService;
	@Autowired
	private LoginService	loginService;

	@Autowired
	private ActorService	actorService;


	//Tests
	@Test
	public void testCreateAndSaveAndSend() {

		super.authenticate("manager");
		final Mesage a = this.messageService.create();
		a.setBody("body");
		a.setPriority("LOW");
		a.setSubject("Hola");
		final Mesage save = this.messageService.sendMesage(a);
		final Mesage find = this.messageService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		super.authenticate(null);
		System.out.println("----Create & Save & Send------");
		System.out.println(save);
		System.out.println("");
	}
	@Test
	public void testDelete() {

		super.authenticate("manager");
		final Mesage a = this.messageService.create();
		a.setBody("body");
		a.setPriority("LOW");
		a.setSubject("Hola");
		final Mesage save = this.messageService.sendMesage(a);
		final Mesage find = this.messageService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		System.out.println("---Delete----");
		System.out.println(this.messageService.findOne(save.getId()));
		this.messageService.delete(save);
		System.out.println(this.messageService.findOne(save.getId()));
		System.out.println("");
		super.authenticate(null);

	}

	@Test
	public void testFindAllMesage() {

		super.authenticate("manager");
		final List<Mesage> all = (List<Mesage>) this.messageService.findAll();
		super.authenticate(null);
		System.out.println("-----Find All--------");
		for (final Mesage l : all)
			System.out.println(l);
		System.out.println("");

	}

	@Test
	public void testFindOneMesage() {

		super.authenticate("manager");
		final List<Mesage> all = (List<Mesage>) this.messageService.findAll();
		final Mesage al = this.messageService.findOne(all.get(0).getId());
		super.authenticate(null);
		System.out.println("-----Find One--------");
		System.out.println(al);
		System.out.println("");
	}

	@Test
	public void testDetectSpam() {

		super.authenticate("manager");
		final Mesage a = this.messageService.create();
		a.setBody("body cialis");
		a.setPriority("LOW");
		a.setSubject("Hola");
		final boolean spam = this.messageService.detectSpam(a);
		Assert.isTrue(spam);
		System.out.println("----Detect Spam------");
		System.out.println("Subject: " + a.getSubject());
		System.out.println("Body: " + a.getBody());
		System.out.println("¿Spam?: " + spam);
		System.out.println("");
		super.authenticate(null);
	}
	@Test
	public void testMoveTo() {

		super.authenticate("manager");
		final Mesage a = this.messageService.create();
		a.setBody("body");
		a.setPriority("LOW");
		a.setSubject("Hola");
		final Mesage save = this.messageService.sendMesage(a);
		final Mesage find = this.messageService.findOne(save.getId());
		Assert.isTrue(save.equals(find));
		System.out.println("----Move To------");
		System.out.println(save.getFolder().getName());
		this.messageService.moveTo(save, this.folderService.getTrashbox(this.loginService.getPrincipalActor()));
		System.out.println(save.getFolder().getName());
		System.out.println("");
		super.authenticate(null);
	}

	@Test
	public void testNotify() {
		super.authenticate("manager");
		final Actor a = this.actorService.findOne(171);

		this.messageService.sendNotificationBox(a);
		final Folder notification = this.folderService.getNotificationmbox(a);
		Assert.isTrue(notification.getMesages().size() == 1);
		System.out.println("-----------Notify--------");
		System.out.println("Funciona el notify de notificar al actor que le pase");

		super.authenticate(null);
	}
}
