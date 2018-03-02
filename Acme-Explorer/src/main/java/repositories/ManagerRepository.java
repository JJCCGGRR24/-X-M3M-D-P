
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Application;
import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select (count(m) * 1.0)/(select count(mm) from Manager mm) from Manager m where m.suspicious is true")
	Double queryB6();

	@Query("select a from Application a where a.trip.manager = ?1")
	List<Application> getApplications(Manager manager);

	@Query("select a from SurvivalClass a where a.trip.manager = ?1")
	List<Application> getSurvivalClasses(Manager manager);

	@Query("select a from Manager a where a.suspicious = true")
	List<Actor> getSuspiciusManager();

	@Query("select a from Trip a where a.manager = ?1")
	List<Actor> getTripsByManager(Manager m);
}
