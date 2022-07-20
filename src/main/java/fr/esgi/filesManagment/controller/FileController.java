package fr.esgi.filesManagment.controller;

import com.amazonaws.services.ecs.model.Attachment;
import fr.esgi.filesManagment.dto.*;
import fr.esgi.filesManagment.model.FileType;
import fr.esgi.filesManagment.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Produces;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity uploadFile(@RequestPart("details") FileRequest fileRequest,
                                     @RequestPart("file") MultipartFile file){
        fileService.uploadFile(file,fileRequest);
        return  ResponseEntity.ok().build();
    }
    @PostMapping("/{id}")
    public ResponseEntity uploadDirectoryFiles(@RequestPart("details") DirectoryRequest directory,
                                     @ModelAttribute List<MultipartFile> files){
        fileService.uploadDirectoryFiles(files,directory);
        return  ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFile(@PathVariable("id") Long id){
        fileService.deleteFile(id);
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
