package fr.esgi.filesManagment.repository;

import fr.esgi.filesManagment.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByReference(Long reference);
}
