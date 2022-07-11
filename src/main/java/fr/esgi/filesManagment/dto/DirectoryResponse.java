package fr.esgi.filesManagment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
