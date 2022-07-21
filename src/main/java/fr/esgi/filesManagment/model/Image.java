package fr.esgi.filesManagment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images", indexes = {@Index(name = "i_link", columnList = "link")})
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_id_seq")
    @SequenceGenerator(name = "images_id_seq", sequenceName = "images_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String link;
    private String title;
    private String description;
    private String details;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(link, image.link) && Objects.equals(title, image.title)  && Objects.equals(description, image.description) && Objects.equals(details, image.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, title, description, details);
    }
}
