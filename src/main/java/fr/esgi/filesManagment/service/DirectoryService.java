package fr.esgi.filesManagment.service;

import fr.esgi.filesManagment.dto.ProjectEvent;
import fr.esgi.filesManagment.exception.ResourceNotFoundException;
import fr.esgi.filesManagment.model.Directory;
import fr.esgi.filesManagment.repository.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectoryService {

    private final DirectoryRepository directoryRepository;

    public Directory createDirectory(ProjectEvent projectEvent) {
        var directory = Directory.builder().id(projectEvent.getId()).title(projectEvent.getTitle()).build();
        return directoryRepository.saveAndFlush(directory);
    }

    public Directory createDirectoryV2(Long id, String name) {
        var directory = Directory.builder().id(id).title(name).build();
        return directoryRepository.saveAndFlush(directory);
    }

    public void deleteDirectory(Long id) {
        directoryRepository.deleteById(id);
    }

    public Directory updateDirectory(Directory directory) {
        return directoryRepository.saveAndFlush(directory);
    }

    public Directory getDirectoryById(Long id) {
        Optional<Directory> directoryOptional =  directoryRepository.findById(id);
        if( directoryOptional.isEmpty()){
            return null;
        }
        return  directoryOptional.get();
    }


}
