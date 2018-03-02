
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MesageRepository;
import security.LoginService;
import domain.Actor;
import domain.Folder;
import domain.Mesage;

@Service
@Transactional
public class MesageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MesageRepository		messageRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService			loginService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;


	// Constructors -----------------------------------------------------------
	public MesageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Mesage create() {
		final Mesage r = new Mesage();
		r.setDate(new Date(System.currentTimeMillis() - 1000));
		r.setSender(this.loginService.getPrincipalActor());
		r.setPriority("NEUTRAL");
		return r;
	}

	public Collection<Mesage> findAll() {
		final Collection<Mesage> res = this.messageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Mesage findOne(final int messageId) {
		return this.messageRepository.findOne(messageId);
	}

	public Mesage save(final Mesage message) {
		message.setSender(this.loginService.getPrincipalActor());
		this.checkPrincipalIsSender(message);
		Assert.notNull(message);
		message.setDate(new Date(System.currentTimeMillis() - 1000));
		return this.messageRepository.save(message);
	}

	public Mesage saveBySystem(final Mesage message) {
		Assert.notNull(message);
		message.setDate(new Date(System.currentTimeMillis() - 1000));
		return this.messageRepository.save(message);
	}

	public void delete(final Mesage message) {
		this.checkPrincipal(message);
		if (message.getFolder().getName().toLowerCase().equals("trashbox"))
			this.messageRepository.delete(message);
		else {
			final Folder trashbox = this.folderService.getTrashbox(this.loginService.getPrincipalActor());
			message.setFolder(trashbox);
			final Folder actual = message.getFolder();
			actual.getMesages().remove(message);
			trashbox.getMesages().add(message);
			this.folderService.save(actual);
			this.folderService.save(trashbox);
			this.messageRepository.save(message);
		}
	}

	public void flush() {
		this.messageRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Mesage moveTo(final Mesage message, final Folder folder) {

		final List<Mesage> mns = folder.getMesages();
		mns.add(message);
		folder.setMesages(mns);
		this.folderService.save(folder);

		final Folder actual = message.getFolder();
		actual.getMesages().remove(message);
		this.folderService.save(actual);

		message.setFolder(folder);
		return this.save(message);

	}

	public Mesage sendMesage(final Mesage message) {
		final Actor sender = this.loginService.getPrincipalActor();
		final Actor recipient = message.getRecipient();

		//Mensaje enviado
		message.setDate(new Date(System.currentTimeMillis() - 1000));
		message.setSender(sender);
		message.setFolder(this.folderService.getOutbox(sender));

		//Mensaje recibido
		final Mesage message2 = this.create();
		message2.setDate(new Date(System.currentTimeMillis() - 1000));
		message2.setRecipient(recipient);
		message2.setSender(message.getSender());
		message2.setPriority(message.getPriority());
		message2.setBody(message.getBody());
		message2.setSubject(message.getSubject());
		message2.setFolder(this.folderService.getInbox(recipient));

		final Mesage res = this.messageRepository.save(message);
		this.messageRepository.save(message2);
		if (this.detectSpam(message))
			message2.setFolder(this.folderService.getSpambox(message2.getRecipient()));
		return res;
	}

	public Boolean detectSpam(final Mesage m) {
		Boolean res = false;
		for (final String s : this.configurationService.find().getSpamWords())
			if (m.getSubject().contains(s) || m.getBody().contains(s)) {
				res = true;
				final Actor sender = m.getSender();
				sender.setSuspicious(res);
				this.actorService.save(sender);
				break;
			}
		return res;
	}

	public void checkPrincipal(final Mesage obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getFolder().getActor().getUserAccount()))
			res = true;
		Assert.isTrue(res);
	}

	public void checkPrincipalIsSender(final Mesage obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getSender().getUserAccount()))
			res = true;
		Assert.isTrue(res);
	}

	public List<String> getPriorities() {
		final List<String> l = new ArrayList<String>();
		l.add("LOW");
		l.add("MEDIUM");
		l.add("HIGH");
		return l;
	}

	public void send(final Mesage m1) {
		Assert.notNull(m1);
		final Mesage m2 = new Mesage();
		m2.setBody(m1.getBody());
		if (this.isSpam(m1))
			m2.setFolder(this.folderService.getSpambox(m1.getRecipient()));
		else
			m2.setFolder(this.folderService.getInbox(m1.getRecipient()));
		m2.setId(0);
		m2.setVersion(0);
		m2.setRecipient(m1.getRecipient());
		m2.setSender(m1.getSender());
		m2.setSubject(m1.getSubject());
		m2.setPriority(m1.getPriority());
		m2.setDate(m1.getDate());

		final Folder f1 = m1.getFolder();
		final Folder f2 = m2.getFolder();

		final List<Mesage> ms1 = f1.getMesages();
		final List<Mesage> ms2 = f2.getMesages();

		ms1.add(m1);
		ms2.add(m2);

		f1.setMesages(ms1);
		f2.setMesages(ms2);

		this.save(m2);
		this.save(m1);
		//		folderService.save(f1);
		//		folderService.save(f2);
	}

	public void sendNotificationBox(final Actor a) {
		final Folder notification = this.folderService.getNotificationmbox(a);
		final Mesage msn = this.create();

		msn.setRecipient(a);
		msn.setSender(this.adminService.findAll().iterator().next());
		msn.setSubject("Querido usuario...." + "\n" + "Dear user....");
		msn.setBody("Cambio de estado en alguna de tus aplicaciones." + "\n" + "Change in one of your applications.");
		msn.setDate(new Date(System.currentTimeMillis() - 1000));
		msn.setFolder(notification);
		final Mesage msn2 = this.saveBySystem(msn);

		final List<Mesage> msns = notification.getMesages();
		msns.add(msn2);
		notification.setMesages(msns);

		this.folderService.save2(notification);

	}

	private boolean isSpam(final Mesage m1) {
		boolean res = false;
		for (final String sw : this.configurationService.find().getSpamWords())
			if (m1.getBody().toLowerCase().contains(sw.toLowerCase()) || m1.getSubject().toLowerCase().contains(sw.toLowerCase())) {
				res = true;
				break;
			}
		return res;
	}

	public boolean sendBroadcast(final Mesage mesage) {
		boolean res = false;
		for (final Actor a : this.actorService.findAll()) {
			//Mensaje recibido
			final Mesage message2 = this.create();
			message2.setDate(new Date(System.currentTimeMillis() - 1000));
			message2.setRecipient(a);
			message2.setSender(mesage.getSender());
			message2.setPriority(mesage.getPriority());
			message2.setBody(mesage.getBody());
			message2.setSubject(mesage.getSubject());
			message2.setFolder(this.folderService.getNotificationmbox(a));
			this.messageRepository.save(message2);
		}
		res = true;
		return res;
	}
}
