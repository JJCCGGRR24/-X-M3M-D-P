
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select min(t.notes.size)*1.0, max(t.notes.size)*1.0,avg(t.notes.size), stddev(t.notes.size)*1.0 from Trip t")
	Object[] queryB1();

}
