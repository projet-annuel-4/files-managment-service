package fr.esgi.filesManagment.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
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
@Table(name = "files", indexes = {@Index(name = "i_link", columnList = "fileLink")})
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "files_id_seq")
    @SequenceGenerator(name = "files_id_seq", sequenceName = "files_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String link;
    private String title;
    @Enumerated(EnumType.STRING)
    private FileType type;
    private String description;
    private String details;
    @ManyToOne
    private Directory directory;

}
