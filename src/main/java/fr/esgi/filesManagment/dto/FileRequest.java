package fr.esgi.filesManagment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileRequest {
    private Long id;
    private Long direcoryId;
    private String type;
    private String title;
    private String link;
    private String description;
}
