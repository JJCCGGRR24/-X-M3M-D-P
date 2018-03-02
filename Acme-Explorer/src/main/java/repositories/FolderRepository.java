
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	@Query("select f from Folder f where f.modifiable = true and f.actor = ?1")
	public Collection<Folder> getFoldersModifiable(Actor a);

	@Query("select f from Folder f where f.name = ?2 and f.actor = ?1")
	public Folder getFolderbyName(Actor a, String s);

	@Query("select f from Folder f where f.actor.id = ?1")
	public Collection<Folder> getFoldersByUser(int idActor);
}
