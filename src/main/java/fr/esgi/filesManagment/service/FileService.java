package fr.esgi.filesManagment.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.opsworks.model.UserProfile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import fr.esgi.filesManagment.dto.DirectoryResponse;
import fr.esgi.filesManagment.dto.FileRequest;
import fr.esgi.filesManagment.dto.FileResponse;
import fr.esgi.filesManagment.exception.BadRequestException;
import fr.esgi.filesManagment.exception.ResourceNotFoundException;
import fr.esgi.filesManagment.model.File;
import fr.esgi.filesManagment.repository.DirectoryRepository;
import fr.esgi.filesManagment.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final static String PROFILE_FILE="annual-project-platform-files";
    private final DirectoryService directoryService;
    private final FileRepository fileRepository;
    private final AmazonS3 s3;

    @Transactional
    public void uploadFile( FileRequest fileRequest) {
        //1. Chek if image is not empty
        if(fileRequest.getFile().isEmpty()){
            throw new BadRequestException(String.format("Cannot upload empty file [%d] ", fileRequest.getFile().getSize()));
        }
        //2. The user exists in our BDD
        var directory = directoryService.getDirectoryById(fileRequest.getDirecoryId());
        //3. Grap some metadata from file if any
        Map<String,String> metadata= new HashMap<>();
        metadata.put("Content-Type", fileRequest.getFile().getContentType());
        metadata.put("Content-Length",String.valueOf(fileRequest.getFile().getSize()));
        //4. Store the image in s3 and Update BDD with s3 image link
        String path = String.format("%s/%s", PROFILE_FILE,fileRequest.getDirecoryId());
        String fileName = fileRequest.getLink();
        try{
            upload(path,fileName,Optional.of(metadata), fileRequest.getFile().getInputStream());
            var files = directory.getFiles();
            var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            var newFile = File.builder().link( fileName).title(fileRequest.getTitle()).description(fileRequest.getDescription()).details(dateFormat.format(Calendar.getInstance().getTime())).build();
            fileRepository.saveAndFlush(newFile);
            files.add(newFile);
            directory.setFiles(files);
            directoryService.updateDirectory(directory);
        }catch (IOException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Transactional
    public DirectoryResponse downloadDirectoryFiles(Long id) {
        var directory = directoryService.getDirectoryById(id);
        String path = String.format("%s/%s", PROFILE_FILE,directory.getId());
        var files = directory.getFiles().stream()
                .map(file -> FileResponse.builder()
                                        .id(file.getId())
                                        .details(file.getDetails())
                                        .description(file.getDescription())
                                        .title(file.getTitle())
                                        .file(download(path,file.getLink()))
                                        .build()
                ).collect(Collectors.toSet());
        return DirectoryResponse.builder()
                .files(files)
                .id(directory.getId())
                .title(directory.getTitle())
                .build();
    }

    public FileResponse downloadFile(Long fileId) {
        var file = fileRepository.findById(fileId).orElseThrow(()-> new ResourceNotFoundException("File","id",fileId.toString()));
        String path = String.format("%s/%s", PROFILE_FILE,file.getDirectory().getId());
        return FileResponse.builder()
                        .id(file.getId())
                        .details(file.getDetails())
                        .description(file.getDescription())
                        .title(file.getTitle())
                        .file(download(path,file.getLink()))
                        .build();
    }

    private void upload(String path, String fileName, Optional<Map<String,String>> optionalMetada, InputStream inputStream){
        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetada.ifPresent((map->{
            if(!map.isEmpty()) map.forEach(metadata::addUserMetadata);
        }));
        try{
            s3.putObject(path,fileName,inputStream,metadata);
        }catch(AmazonServiceException e){
            throw new BadRequestException("Failed to store file to s3",e);
        }
    }

    private byte[] download(String path, String key) {
        try{
            S3Object object = s3.getObject(path, key);
            S3ObjectInputStream inputStream =object.getObjectContent();
            return IOUtils.toByteArray(inputStream);
        }catch(AmazonServiceException | IOException e){
            throw new BadRequestException("Failed to download file from s3",e);
        }
    }
}
