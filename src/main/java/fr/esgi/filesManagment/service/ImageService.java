package fr.esgi.filesManagment.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import fr.esgi.filesManagment.dto.*;
import fr.esgi.filesManagment.exception.BadRequestException;
import fr.esgi.filesManagment.exception.ResourceNotFoundException;
import fr.esgi.filesManagment.mapper.ImageMapper;
import fr.esgi.filesManagment.model.File;
import fr.esgi.filesManagment.model.FileType;
import fr.esgi.filesManagment.model.Image;
import fr.esgi.filesManagment.repository.FileRepository;
import fr.esgi.filesManagment.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${amazon.s3.bucket.name}")
    private String PROFILE_FILE;
    private static final List IMAGES_EXE = Arrays.asList(IMAGE_GIF.getMimeType(),
            IMAGE_PNG.getMimeType(),
            IMAGE_JPEG.getMimeType());
    private final ImageRepository imageRepository;
    private final FileService fileService;
    private final AmazonS3 s3;

    @Transactional
    public ImageResponse uploadImage(MultipartFile image, ImageRequest imageRequest) {
        //1. Chek if image is not empty
        if(image.isEmpty()){
            throw new IllegalStateException(String.format("Cannot upload empty file [%d] ", image.getSize()));
        }
        //2. If file is an image
        if(!IMAGES_EXE.contains(image.getContentType())){
            throw new IllegalStateException(String.format("File must be an image"));
        }
        //3. Grap some metadata from file if any
        Map<String,String> metadata= new HashMap<>();
        metadata.put("Content-Type", image.getContentType());
        metadata.put("Content-Length",String.valueOf(image.getSize()));
        //5. Store the image in s3 and Update BDD with s3 image link
        String path = String.format("%s/%s", PROFILE_FILE,"Images");
        String fileName = String.format("%s/%s",imageRequest.getLink(),imageRequest.getTitle());
        try{
            fileService.upload(path,fileName,Optional.of(metadata), image.getInputStream());
            var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Image newImage;
            var imageOptional = imageRepository.findByReference(Long.parseLong(imageRequest.getLink(), 10));
            if( imageOptional.isEmpty() ){
                newImage = Image.builder().link( fileName).title(imageRequest.getTitle()).description(imageRequest.getDescription()).details(dateFormat.format(Calendar.getInstance().getTime())).reference(Long.parseLong(imageRequest.getLink(), 10)).build();
                imageRepository.saveAndFlush(newImage);
            } else {
                newImage = imageOptional.get();
            }
            return ImageMapper.convertToResponse(newImage);
        }catch (IOException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    @Transactional
    public ImageResponse downloadImage(Long ref) {
        var imageEntity = imageRepository.findByReference(ref).orElseThrow(()-> new ResourceNotFoundException("Image","id",ref.toString()));
        String path = String.format("%s/%s", PROFILE_FILE,"Images");
        var image = ImageMapper.convertToResponse(imageEntity);
        image.setFile(fileService.download(path,imageEntity.getLink()));
        return image;
    }
    @Transactional
    public void deleteImage(Long imageId) {
        var imageEntity = imageRepository.findById(imageId).orElseThrow(()-> new ResourceNotFoundException("Image","id",imageId.toString()));
        String path = String.format("%s/%s", PROFILE_FILE,"Images");
        fileService.delete(path,imageEntity.getLink());
        imageRepository.delete(imageEntity);
    }


}
