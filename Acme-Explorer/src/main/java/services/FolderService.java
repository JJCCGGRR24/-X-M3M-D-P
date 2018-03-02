
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import security.LoginService;
import domain.Actor;
import domain.Folder;
import domain.Mesage;

@Service
@Transactional
public class FolderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FolderRepository	folderRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService		loginService;


	// Constructors -----------------------------------------------------------
	public FolderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Folder create() {
		final Folder r = new Folder();
		r.setModifiable(true);
		final List<Mesage> messages = new ArrayList<>();
		r.setMesages(messages);
		return r;
	}

	public Collection<Folder> findAll() {
		final Collection<Folder> res = this.folderRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Folder findOne(final int folderId) {
		return this.folderRepository.findOne(folderId);
	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(this.checkPrincipal(folder));
		if (folder.getId() > 0) {
			final Folder original = this.findOne(folder.getId());
			if (!original.getModifiable())
				Assert.isTrue(original.getName().equals(folder.getName()));
		}
		//final Actor actor = this.loginService.getPrincipalActor();
		//folder.setActor(actor);
		//final List<Folder> folders = actor.getFolders();
		//folders.add(folder);
		//actor.setFolders(folders);
		return this.folderRepository.save(folder);
	}

	public Folder save2(final Folder folder) {
		Assert.notNull(folder);
		if (folder.getId() > 0) {
			final Folder original = this.findOne(folder.getId());
			if (!original.getModifiable())
				Assert.isTrue(original.getName().equals(folder.getName()));
		}
		//final Actor actor = this.loginService.getPrincipalActor();
		//folder.setActor(actor);
		//final List<Folder> folders = actor.getFolders();
		//folders.add(folder);
		//actor.setFolders(folders);
		return this.folderRepository.save(folder);
	}

	public void delete(final Folder folder) {
		Assert.isTrue(this.checkPrincipal(folder));
		Assert.isTrue(folder.getModifiable());
		this.folderRepository.delete(folder);
	}

	public void flush() {
		this.folderRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Boolean checkPrincipal(final Folder folder) {
		Assert.isTrue(LoginService.getPrincipal().equals(folder.getActor().getUserAccount()));
		return true;
	}

	public Collection<Folder> getFoldersByUser() {
		final Actor a = this.loginService.getPrincipalActor();
		return this.folderRepository.getFoldersByUser(a.getId());
	}

	public Actor addFolders(final Actor actor) {
		final Folder inbox = this.create();
		inbox.setName("Inbox");
		inbox.setActor(actor);
		inbox.setModifiable(false);
		final Folder outbox = this.create();
		outbox.setName("Outbox");
		outbox.setActor(actor);
		outbox.setModifiable(false);
		final Folder trashbox = this.create();
		trashbox.setName("Trashbox");
		trashbox.setActor(actor);
		trashbox.setModifiable(false);
		final Folder spambox = this.create();
		spambox.setName("Spambox");
		spambox.setActor(actor);
		spambox.setModifiable(false);
		final Folder notification = this.create();
		notification.setName("Notificationbox");
		notification.setActor(actor);
		notification.setModifiable(false);
		final List<Folder> folders = new ArrayList<Folder>();
		folders.add(inbox);
		folders.add(outbox);
		folders.add(trashbox);
		folders.add(spambox);
		folders.add(notification);
		actor.setFolders(folders);
		return actor;
	}

	public Folder changeFolderName(final Folder folder, final String s) {
		Assert.isTrue(folder.getModifiable() == (true));
		folder.setName(s);
		return this.save(folder);
	}

	public Folder getFolderByName(final Actor a, final String s) {
		return this.folderRepository.getFolderbyName(a, s);
	}

	public Folder getOutbox(final Actor a) {
		return this.getFolderByName(a, "Outbox");
	}

	public Folder getInbox(final Actor a) {
		return this.getFolderByName(a, "Inbox");
	}

	public Folder getTrashbox(final Actor a) {
		return this.getFolderByName(a, "Trashbox");
	}

	public Folder getSpambox(final Actor a) {
		return this.getFolderByName(a, "Spambox");
	}

	public Folder getNotificationmbox(final Actor a) {
		return this.getFolderByName(a, "Notificationbox");
	}

	public boolean isReservedName(final Folder f) {
		boolean res = false;
		final String s = f.getName().toLowerCase();
		if (s.equals("inbox") || s.equals("spambox") || s.equals("trashbox") || s.equals("outbox") || s.equals("notificationbox"))
			res = true;
		return res;
	}
}
