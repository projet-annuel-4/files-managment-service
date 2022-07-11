package fr.esgi.filesmanagement.model;

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
@Table(name = "files", indexes = {@Index(name = "i_link", columnList = "link")})
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "files_id_seq")
    @SequenceGenerator(name = "files_id_seq", sequenceName = "files_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private Long reference;
    private String link;
    private String title;
    @Enumerated(EnumType.STRING)
    private FileType type;
    private String description;
    private String details;
    @ManyToOne
    @JoinColumn(name="directory_id", nullable=false)
    private Directory directory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(link, file.link) && Objects.equals(title, file.title) && type == file.type && Objects.equals(description, file.description) && Objects.equals(details, file.details) && Objects.equals(directory, file.directory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, title, type, description, details, directory);
    }
}
