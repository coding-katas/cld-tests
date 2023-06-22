package com.orange.events;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.orange.dto.CliperDTO;
import com.orange.dto.ControlParameterDTO;
import com.orange.model.Cliper;
import com.orange.repository.CliperRepository;

@ExtendWith(SpringExtension.class)
public class ReceiveKafkaMessageTests {

    @InjectMocks
    private ReceiveKafkaMessage receiveKafkaMessage;

    @Mock
    private CliperRepository cliperRepository;

    @Test
    public void testSuccessfulMessageReceived() {

        CliperDTO cliperDTO = new CliperDTO();
        cliperDTO.setStatus("SUCCESS");

        ControlParameterDTO controlParameterDTO = new ControlParameterDTO();
        controlParameterDTO.setKey("application_name");
        controlParameterDTO.setValue("tcrm");

        cliperDTO.getParams().add(controlParameterDTO);

        Cliper cliper = Cliper.convert(cliperDTO);

        Mockito
        .when(cliperRepository.findByIdentifier(cliperDTO.getIdentifier()))
        .thenReturn(cliper);

        receiveKafkaMessage.listenCliperEvents(cliperDTO);
        Mockito
        .verify(cliperRepository, Mockito.times(1))
        .findByIdentifier(cliperDTO.getIdentifier());

        Mockito
        .verify(cliperRepository, Mockito.times(1))
        .save(cliper);
    }

}
