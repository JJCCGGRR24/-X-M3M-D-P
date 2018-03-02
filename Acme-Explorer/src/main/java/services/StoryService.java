
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StoryRepository;
import security.LoginService;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Service
@Transactional
public class StoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private StoryRepository	storyRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService	loginService;


	// Constructors -----------------------------------------------------------
	public StoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Story create(final Trip trip) {
		final Story r = new Story();

		final Explorer explorer = (Explorer) this.loginService.getPrincipalActor();
		r.setExplorer(explorer);
		final List<Story> ls = explorer.getStories();
		ls.add(r);
		explorer.setStories(ls);

		final List<String> attachments = new ArrayList<>();
		r.setAttachments(attachments);

		r.setTrip(trip);
		final List<Story> storiesTrip = trip.getStories();
		storiesTrip.add(r);
		trip.setStories(storiesTrip);

		return r;
	}

	public Story create() {
		final Story r = new Story();

		final Explorer explorer = (Explorer) this.loginService.getPrincipalActor();
		r.setExplorer(explorer);
		final List<Story> ls = explorer.getStories();
		ls.add(r);
		explorer.setStories(ls);

		final List<String> attachments = new ArrayList<>();
		r.setAttachments(attachments);

		return r;
	}

	public Collection<Story> findAll() {
		final Collection<Story> res = this.storyRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Story findOne(final int storyId) {
		return this.storyRepository.findOne(storyId);
	}

	public Story save(final Story story) {
		Assert.notNull(story);
		Assert.isTrue(this.checkPrincipal(story));
		return this.storyRepository.save(story);
	}

	public void delete(final Story story) {
		Assert.isTrue(this.checkPrincipal(story));
		this.storyRepository.delete(story);
	}

	public void crudDelete(final Story story) {
		//		Assert.isTrue(this.checkPrincipal(story));
		this.storyRepository.delete(story);
	}

	public void flush() {
		this.storyRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public boolean checkPrincipal(final Story obj) {
		boolean res = false;
		if (LoginService.getPrincipal().equals(obj.getExplorer().getUserAccount()))
			res = true;
		return res;
	}

}
