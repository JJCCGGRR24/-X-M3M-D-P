
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Trip;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select t from Trip t where t.price <= ?1 and t.price >= ?2 and (t.ticker like %?3% or t.title like %?3% or t.description like %?3% ) and t.starts >= ?4 and t.ends <= ?5")
	List<Trip> finderAllFields(Double prizeMax, Double priceMin, String keyword, Date fechaMin, Date fechaMax);

}
