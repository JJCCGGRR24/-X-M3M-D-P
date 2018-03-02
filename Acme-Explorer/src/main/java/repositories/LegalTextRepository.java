
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalText;
import domain.Trip;

@Repository
public interface LegalTextRepository extends JpaRepository<LegalText, Integer> {

	@Query("select lt.title, count(lt) from Trip t join t.legalText lt group by lt.id")
	List<Object[]> queryC11();

	@Query("select l from LegalText l where l.finalMode = true")
	Collection<LegalText> getLegalTextsFinalMode();

	@Query("select t from Trip t where t.legalText.title = ?1")
	Trip tripByLegalText(String legalTextName);

}
