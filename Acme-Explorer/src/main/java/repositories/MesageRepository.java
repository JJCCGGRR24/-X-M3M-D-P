
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Mesage;

@Repository
public interface MesageRepository extends JpaRepository<Mesage, Integer> {

}
