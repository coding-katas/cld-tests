package com.orange.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.orange.dto.CliperDTO;
import com.orange.model.Cliper;
import com.orange.repository.CliperRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveKafkaMessage {

    private final CliperRepository cliperRepository;

    private static final String CLIPER_TOPIC_EVENT_NAME = "CLIPER_TOPIC_EVENT";
    private static final String CLIPER_TOPIC_NAME = "CLIPER_TOPIC";

    @KafkaListener(topics = CLIPER_TOPIC_NAME, groupId = "group")
    public void listenCliperTopic(CliperDTO cliperDTO) {
        try {
            log.info("Party received on topic: {}.", cliperDTO.getIdentifier());

            Cliper cliper = cliperRepository.findByIdentifier(cliperDTO.getIdentifier());
            cliper.setStatus(cliperDTO.getStatus());
            cliperRepository.save(cliper);
        } catch (Exception e) {
            log.error("Error processing message {}", cliperDTO.getIdentifier());
        }
    }

    @KafkaListener(topics = CLIPER_TOPIC_EVENT_NAME, groupId = "group")
    public void listenCliperEvents(CliperDTO cliperDTO) {
        try {
            log.info("Party received on topic: {}.", cliperDTO.getIdentifier());

            Cliper cliper = cliperRepository.findByIdentifier(cliperDTO.getIdentifier());
            cliper.setStatus(cliperDTO.getStatus());
            cliperRepository.save(cliper);
        } catch (Exception e) {
            log.error("Error processing message {}", cliperDTO.getIdentifier());
        }
    }


}
