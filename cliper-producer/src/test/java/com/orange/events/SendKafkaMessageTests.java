package com.orange.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.orange.dto.CliperDTO;

@ExtendWith(SpringExtension.class)
public class SendKafkaMessageTests {

    @InjectMocks
    private SendKafkaMessage sendKafkaMessage;

    @Mock
    private KafkaTemplate<String, CliperDTO> kafkaTemplate;

    private static final String CLIPER_TOPIC_NAME = "CLIPER_TOPIC";

    @Test
    public void testSendMessage() {
        CliperDTO cliperDTO = new CliperDTO();
        cliperDTO.setStatus("SUCCESS");
        cliperDTO.setEntityId("b-1");

        sendKafkaMessage.sendMessage(cliperDTO);

        Mockito
        .verify(kafkaTemplate, Mockito.times(1))
        .send(CLIPER_TOPIC_NAME, "b-1", cliperDTO);
    }
}
