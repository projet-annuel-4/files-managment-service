package fr.esgi.filesManagment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileRequest {
    private MultipartFile file;
    private Long direcoryId;
    private String title;
    private String link;
    private String description;
    private String details;
}
