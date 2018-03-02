
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Tag;
import domain.Trip;

@Service
@Transactional
public class TagService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TagRepository	tagRepository;

	@Autowired
	private TripService		tripService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public TagService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Tag create() {
		final Tag r = new Tag();

		final List<Trip> trips = new ArrayList<>();
		r.setTrips(trips);

		return r;
	}

	public Collection<Tag> findAll() {
		final Collection<Tag> res = this.tagRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Tag findOne(final int tagId) {
		return this.tagRepository.findOne(tagId);
	}

	public Tag save(final Tag tag) {
		Assert.notNull(tag);
		if (tag.getId() > 0)
			Assert.isTrue(tag.getTrips().isEmpty());
		return this.tagRepository.save(tag);
	}

	public void delete(final Tag tag) {
		//Assert.isTrue(tag.getTrips().isEmpty());
		this.tagRepository.delete(tag);
	}

	public void flush() {
		this.tagRepository.flush();
	}

	// Other business methods -------------------------------------------------

	public Collection<Tag> getTagsFree() {

		return this.tagRepository.getTagsFree();
	}

	public void add(final Tag tag, final Trip trip) {
		final List<Tag> tags = trip.getTags();
		tags.add(tag);
		trip.setTags(tags);
		this.tripService.save(trip);

		final List<Trip> trips = tag.getTrips();
		trips.add(trip);
		tag.setTrips(trips);
		this.tagRepository.save(tag);
	}

	public void takeOff(final Tag tag, final Trip trip) {
		final List<Tag> tags = trip.getTags();
		tags.remove(tag);
		trip.setTags(tags);
		this.tripService.save(trip);

		final List<Trip> trips = tag.getTrips();
		trips.remove(trip);
		tag.setTrips(trips);
		this.tagRepository.save(tag);
	}
}
