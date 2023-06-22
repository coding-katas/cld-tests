package com.orange.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.orange.dto.CliperDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendKafkaMessage {

    private final KafkaTemplate<String, CliperDTO> kafkaTemplate;

    private static final String CLIPER_TOPIC_NAME = "CLIPER_TOPIC";

    public void sendMessage(CliperDTO msg) {
        kafkaTemplate.send(CLIPER_TOPIC_NAME, msg.getEntityId(), msg);
    }

}
