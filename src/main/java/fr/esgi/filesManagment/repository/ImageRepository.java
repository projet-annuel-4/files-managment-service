package fr.esgi.filesManagment.repository;

import fr.esgi.filesManagment.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByReference(Long ref);

}
