package fr.esgi.filesManagment.mapper;


import fr.esgi.filesManagment.dto.FileResponse;
import fr.esgi.filesManagment.dto.ImageResponse;
import fr.esgi.filesManagment.model.File;
import fr.esgi.filesManagment.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ImageMapper {

    public static ImageResponse convertToResponse(Image image) {
        return ImageResponse.builder()
                .id(image.getId())
                .description(image.getDescription())
                .details(image.getDetails())
                .link(image.getLink())
                .title(image.getTitle())
                .build();
    }


}
