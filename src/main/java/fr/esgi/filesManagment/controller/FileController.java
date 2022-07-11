package fr.esgi.filesmanagement.controller;

import fr.esgi.filesmanagement.dto.DirectoryRequest;
import fr.esgi.filesmanagement.dto.DirectoryResponse;
import fr.esgi.filesmanagement.dto.FileRequest;
import fr.esgi.filesmanagement.dto.FileResponse;
import fr.esgi.filesmanagement.model.FileType;
import fr.esgi.filesmanagement.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity uploadFile(@RequestPart("details") FileRequest fileRequest,
                                     @ModelAttribute MultipartFile file){
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
    @GetMapping("/{id}")
    public ResponseEntity<FileResponse> downloadFile(@PathVariable("id") Long id,@RequestParam(value="type") Optional<String> type){
        //ByteArrayResource
        var file = fileService.downloadFile(id,type.orElse(FileType.ACTUAL_FILE.getName()));
        return ResponseEntity.ok(file);
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