package fr.esgi.filesManagment.controller;

import fr.esgi.filesManagment.dto.*;
import fr.esgi.filesManagment.model.FileType;
import fr.esgi.filesManagment.service.FileService;
import fr.esgi.filesManagment.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final ImageService imageService;


    @PostMapping
    public ResponseEntity uploadFile(@RequestPart("details") FileRequest fileRequest,
                                     @RequestPart("file") MultipartFile file){
        fileService.uploadFile(file,fileRequest);
        return  ResponseEntity.ok().build();
    }

    @PostMapping("/image")
    public ResponseEntity uploadImage(@RequestPart("details") ImageRequest imageRequest,
                                      @RequestPart("image") MultipartFile image) {
        var newImage = imageService.uploadImage(image, imageRequest);
        URI location = URI.create(
                ServletUriComponentsBuilder.fromCurrentRequest().build().toUri() + "/" + newImage.getId());
        return ResponseEntity.created(location).body(newImage);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Optional<ImageResponse>> downloadImage(@PathVariable("id") Long id) {
        var file = imageService.downloadImage(id);
        return ResponseEntity.ok(file);
    }

    @PostMapping("/{id}")
    public ResponseEntity uploadDirectoryFiles(@RequestPart("details") DirectoryRequest directory,
                                               @ModelAttribute List<MultipartFile> files) {
        fileService.uploadDirectoryFiles(files, directory);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFile(@PathVariable("id") Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/image/{id}")
    public ResponseEntity deleteimage(@PathVariable("id") Long id){
        imageService.deleteImage(id);
        return  ResponseEntity.noContent().build();
    }

    /**
     *
     * @param id
     * @param type
     * @return
     */
    @GetMapping("/{id}/{type}")
    public ResponseEntity<ByteArrayResource > downloadFile(@PathVariable("id") Long id,@PathVariable("type") String type){
        //ByteArrayResource
        System.out.println("/////////////////////////////" + type + id);
        var file = fileService.downloadFile(id,type);
        var resource = new ByteArrayResource(file.getFile());
        return ResponseEntity.ok()
                .contentLength(file.getFile().length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file.getTitle() + "\"")
                .header("Cache-Control", "no-cache")
                .body(resource);
    }

    /**
     * Downloawd directory files by id
     * @param id of the directory
     * @param type if type null, the methode return files withe type equal to file
     * @return DirectoryResponse
     */
    @GetMapping("/directory/{id}")
    public ResponseEntity<DirectoryResponse> downloadDirectoryFiles(@PathVariable("id") Long id,@RequestParam(value="type") Optional<String> type){
        //ByteArrayResource
        var directory = fileService.downloadDirectoryFiles(id,type.orElse(FileType.ACTUAL_FILE.getName()));
        return ResponseEntity.ok(directory);
    }
}
