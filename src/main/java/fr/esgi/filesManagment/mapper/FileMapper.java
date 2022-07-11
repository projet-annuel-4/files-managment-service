package fr.esgi.filesmanagement.mapper;


import fr.esgi.filesmanagement.dto.FileResponse;
import fr.esgi.filesmanagement.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FileMapper {

    public FileResponse convertToResponse(File file) {
        return FileResponse.builder()
                .id(file.getId())
                .build();
    }


}
