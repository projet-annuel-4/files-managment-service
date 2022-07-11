package fr.esgi.filesmanagement.bus;


import fr.esgi.filesmanagement.dto.DirectoryEvent;
import fr.esgi.filesmanagement.service.DirectoryService;
import fr.esgi.filesmanagement.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component("deleteDirectoryConsumer")
public class DeleteDirectoryConsumer implements Consumer<Message<DirectoryEvent>> {

    private final DirectoryService directoryService;
    private final FileService fileService;

    @Override
    public void accept(Message<DirectoryEvent> message) {
        DirectoryEvent directoryEvent = message.getPayload();
        MessageHeaders messageHeaders = message.getHeaders();
        log.info("Directory event with id '{}' received from bus. topic: {}, partition: {}, offset: {}, deliveryAttempt: {}",
                directoryEvent.getId(),
                messageHeaders.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                messageHeaders.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class),
                messageHeaders.get(KafkaHeaders.OFFSET, Long.class),
                messageHeaders.get(IntegrationMessageHeaderAccessor.DELIVERY_ATTEMPT, AtomicInteger.class));
        var directory = directoryService.getDirectoryById(directoryEvent.getId());
        directory.getFiles().stream().forEach(file -> fileService.deleteFile(file.getId()));
        log.info("Directory with id {} saved.", directory.getId());
    }
}