package fr.esgi.filesmanagement.service;

import fr.esgi.filesmanagement.dto.DirectoryEvent;
import fr.esgi.filesmanagement.exception.ResourceNotFoundException;
import fr.esgi.filesmanagement.model.Directory;
import fr.esgi.filesmanagement.repository.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectoryService {

    private final DirectoryRepository directoryRepository;

    public Directory createDirectory(DirectoryEvent directoryEvent) {
        var directory = Directory.builder().id(directoryEvent.getId()).title(directoryEvent.getTitle()).build();
        return directoryRepository.saveAndFlush(directory);
    }

    public void deleteDirectory(Long id) {
        directoryRepository.deleteById(id);
    }

    public Directory updateDirectory(Directory directory) {
        return directoryRepository.saveAndFlush(directory);
    }

    public Directory getDirectoryById(Long id) {
        return directoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Directory","id",id.toString()));
    }


}
