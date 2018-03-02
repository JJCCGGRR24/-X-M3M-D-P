
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select min(t.audits.size)*1.0, max(t.audits.size)*1.0, avg(t.audits.size), stddev(t.audits.size)*1.0 from Trip t")
	Object[] queryB2();

}
