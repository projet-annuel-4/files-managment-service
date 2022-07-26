package fr.esgi.filesManagment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "directories")
public class Directory {
    @Id
    private  Long id;
    private  String title;
    @OneToMany(mappedBy = "directory", cascade = CascadeType.ALL)
    private Set<File> files = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return Objects.equals(id, directory.id) && Objects.equals(title, directory.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
