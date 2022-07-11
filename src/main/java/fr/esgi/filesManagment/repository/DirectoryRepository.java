package fr.esgi.filesManagment.repository;

import fr.esgi.filesManagment.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory,Long> {

}