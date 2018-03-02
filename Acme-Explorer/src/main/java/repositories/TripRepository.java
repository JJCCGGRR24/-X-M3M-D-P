
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select avg(m.trips.size), min(m.trips.size)*1.0,  max(m.trips.size)*1.0, stddev(m.trips.size)*1.0 from Manager m")
	public Object[] queryC2();

	@Query("select avg(t.price), min(t.price)*1.0,  max(t.price)*1.0, stddev(t.price)*1.0 from Trip t")
	public Object[] queryC3();

	@Query("select avg(r.trips.size), min(r.trips.size)*1.0,  max(r.trips.size)*1.0, stddev(r.trips.size)*1.0 from Ranger r")
	public Object[] queryC4();

	@Query("select (count(t) *1.0)/(select count(tt) from Trip tt) from Trip t where t.cancelledReason is not null")
	public Double queryC9();

	@Query("select tr from Trip tr where tr.applications.size>= ((select avg(t.applications.size) from Trip t)*1.1) group by applications.size")
	public List<Trip> queryC10();

	@Query("select (count(t)*1.0)/(select count(tt) from Trip tt) from Trip t where t.audits.size = 1")
	public Double queryB3();

	@Query("select r from Trip r where r.title like %?1% or r.ticker like %?1% or r.description like %?1%")
	public Collection<Trip> search(String search);

	@Query("select t from Trip t where t.publicationDate < CURRENT_DATE and t.starts > CURRENT_DATE")
	public Collection<Trip> cancellableTrips();

	@Query("select sum(s.price) from Stage s where s.trip = ?1")
	public Double calculatePrice(Trip t);

	@Query("select a.trip from Audit a where a.auditor.id = ?1")
	public List<Trip> getTripsByAuditor(int id);

	@Query("select t from Trip t where t.manager.id = ?1")
	public Collection<Trip> getTripsByManager(int managerId);

	@Query("select t from Trip t where t.publicationDate != null and t.cancelledReason = null and t.starts > ?1")
	public Collection<Trip> getTripsVisible(Date dateNow);

}
