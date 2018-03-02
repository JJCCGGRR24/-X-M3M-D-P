
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Explorer;
import domain.Trip;

@Repository
public interface ExplorerRepository extends JpaRepository<Explorer, Integer> {

	@Query("select sc from Application a join a.trip.survivalClasses sc where a.explorer = ?1 and a.status = 'ACCEPTED' and sc.date > ?2")
	List<Application> getSurvivalClassesApplyAccepted(Explorer explorer, Date now);

	@Query("select a.trip from Application a where a.explorer = ?1 and a.status='ACCEPTED'")
	List<Trip> getTripsWithApplicationAccepted(Explorer explorer);

}
