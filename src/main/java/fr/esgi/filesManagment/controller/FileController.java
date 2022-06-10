package fr.esgi.filesManagment.controller;

import fr.esgi.filesManagment.dto.DirectoryResponse;
import fr.esgi.filesManagment.dto.FileRequest;
import fr.esgi.filesManagment.dto.FileResponse;
import fr.esgi.filesManagment.model.FileType;
import fr.esgi.filesManagment.service.FileService;
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
        fileService.uploadFile(fileRequest);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileResponse> downloadFile(@PathVariable("id") Long id){
        //ByteArrayResource
        var file = fileService.downloadFile(id);
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
        var directory = fileService.downloadDirectoryFiles(id,type.orElse(FileType.FILE.getName()));
        return ResponseEntity.ok(directory);
    }

}
