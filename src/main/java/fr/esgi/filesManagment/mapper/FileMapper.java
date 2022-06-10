package fr.esgi.filesManagment.mapper;


import fr.esgi.filesManagment.dto.FileResponse;
import fr.esgi.filesManagment.model.File;
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
