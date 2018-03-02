
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Stage;
import domain.Trip;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {

	@Query("select s from Stage s where s.trip = ?1")
	public Collection<Stage> stagesByTrip(Trip trip);
}
