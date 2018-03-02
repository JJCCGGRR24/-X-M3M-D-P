
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;
import domain.Nulp;

@Repository
public interface NulpRepository extends JpaRepository<Nulp, Integer> {

	@Query("select (count(t)*1.0)/(select count(tt) from Trip tt) from Trip t where t.nulps.size >=1")
	Double querieA();

	@Query("select t.manager from Trip t where t.nulps.size = (select max(tt.nulps.size) from Trip tt)")
	List<Manager> querieB();

	@Query("select n from Nulp n where n.trip.id = ?1 and n.moment < CURRENT_DATE")
	Collection<Nulp> getNulpsVisibles(int tripId);

}
