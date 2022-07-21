package fr.esgi.filesManagment.service;

import fr.esgi.filesManagment.dto.ProjectEvent;
import fr.esgi.filesManagment.exception.ResourceNotFoundException;
import fr.esgi.filesManagment.model.Directory;
import fr.esgi.filesManagment.repository.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectoryService {

    private final DirectoryRepository directoryRepository;

    public Directory createDirectory(ProjectEvent projectEvent) {
        var directory = Directory.builder().id(projectEvent.getId()).title(projectEvent.getTitle()).build();
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
