package fr.esgi.filesManagment.bus;


import fr.esgi.filesManagment.dto.DirectoryEvent;
import fr.esgi.filesManagment.service.DirectoryService;
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
@Component("createdDirectoryConsumer")
public class CreatedDirectoryConsumer implements Consumer<Message<DirectoryEvent>> {

    private final DirectoryService directoryService;

    @Override
    public void accept(Message<DirectoryEvent> message) {
        DirectoryEvent directoryEvent = message.getPayload();
        MessageHeaders messageHeaders = message.getHeaders();
        log.info("User event with id '{}' and title '{}' received from bus. topic: {}, partition: {}, offset: {}, deliveryAttempt: {}",
                directoryEvent.getId(),
                directoryEvent.getTitle(),
                messageHeaders.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                messageHeaders.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class),
                messageHeaders.get(KafkaHeaders.OFFSET, Long.class),
                messageHeaders.get(IntegrationMessageHeaderAccessor.DELIVERY_ATTEMPT, AtomicInteger.class));
        var directory = directoryService.createDirectory(directoryEvent);
        log.info("Directory with id {} saved.", directory.getId());
    }
}