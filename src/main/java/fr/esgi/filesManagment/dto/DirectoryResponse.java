package fr.esgi.filesmanagement.dto;

import fr.esgi.filesmanagement.model.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectoryResponse {
    private Long id;
    private String title;
    private Set<FileResponse> files;
}
