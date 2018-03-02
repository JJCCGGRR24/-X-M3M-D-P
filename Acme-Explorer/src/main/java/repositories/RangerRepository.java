
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Ranger;

@Repository
public interface RangerRepository extends JpaRepository<Ranger, Integer> {

	//	@Query("select (count(r)*1.0)/(select count(rr)*1.0 from Ranger rr) from Ranger r where r.curricula.size > 0")
	//	Double queryB4();

	@Query("select (count(r)*1.0)/(select count(rr) from Ranger rr) from Ranger r where r.curricula is not null")
	Double queryB4();

	@Query("select (count(r)*1.0)/(select count(rr) from Ranger rr) from Ranger r where r.curricula.endorserRecords.size>0")
	Double queryB5();

	@Query("select (count(m) * 1.0)/(select count(mm) from Ranger mm) from Ranger m where m.suspicious is true")
	Double queryB7();

	@Query("select a from Ranger a where a.suspicious = true")
	List<Actor> getSuspiciusRanger();

}
