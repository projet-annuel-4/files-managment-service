package fr.esgi.filesmanagement.repository;

import fr.esgi.filesmanagement.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory,Long> {

}