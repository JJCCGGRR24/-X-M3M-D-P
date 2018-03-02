
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Explorer;
import domain.Trip;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select avg(t.applications.size), min(t.applications.size)*1.0,  max(t.applications.size)*1.0, stddev(t.applications.size) from Trip t")
	Object[] queryC1();

	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='PENDING'")
	Double queryC5();

	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='DUE'")
	Double queryC6();

	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='ACCEPTED'")
	Double queryC7();

	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='CANCELLED'")
	Double queryC8();

	@Query("select a from Application a where a.trip = ?1 and a.explorer = ?2")
	Application getApplicationByExplorer(Trip trip, Explorer explorer);
}
