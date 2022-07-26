package fr.esgi.filesManagment.bus;


import fr.esgi.filesManagment.dto.ProjectEvent;
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
@Component("createdProjectConsumer")
public class CreatedProjectConsumer implements Consumer<Message<ProjectEvent>> {

    private final DirectoryService directoryService;

    @Override
    public void accept(Message<ProjectEvent> message) {
        ProjectEvent projectEvent = message.getPayload();
        MessageHeaders messageHeaders = message.getHeaders();
        log.info("Directory event with id '{}' and title '{}' received from bus. topic: {}, partition: {}, offset: {}, deliveryAttempt: {}",
                projectEvent.getId(),
                projectEvent.getTitle(),
                messageHeaders.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                messageHeaders.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class),
                messageHeaders.get(KafkaHeaders.OFFSET, Long.class),
                messageHeaders.get(IntegrationMessageHeaderAccessor.DELIVERY_ATTEMPT, AtomicInteger.class));
        //var directory = directoryService.createDirectory(projectEvent);
        //log.info("Directory with id {} saved.", directory.getId());
    }
}