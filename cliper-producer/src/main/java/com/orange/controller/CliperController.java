package com.orange.controller;

import com.orange.dto.CliperDTO;
import com.orange.events.SendKafkaMessage;
import com.orange.model.Cliper;
import com.orange.model.ControlParameter;
import com.orange.repository.CliperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cliper")
@RequiredArgsConstructor
public class CliperController {

    private final CliperRepository cliperRepository;
    private final SendKafkaMessage sendKafkaMessage;

    @GetMapping
    public List<CliperDTO> getCliper() {
        return cliperRepository.findAll()
               .stream()
               .map(cliper -> CliperDTO.convert(cliper))
               .collect(Collectors.toList());
    }

    @PostMapping
    public CliperDTO saveCliper(@RequestBody CliperDTO cliperDTO) {
        cliperDTO.setIdentifier(UUID.randomUUID().toString());
        cliperDTO.setMessageCreationTime(System.currentTimeMillis());
        cliperDTO.setStatus("PENDING");

        Cliper cliper = Cliper.convert(cliperDTO);
        for (ControlParameter controlParameter : cliper.getParams()) {
            controlParameter.setCliper(cliper);
        }

        cliperDTO = CliperDTO.convert(cliperRepository.save(cliper));
        sendKafkaMessage.sendMessage(cliperDTO);
        return cliperDTO;

    }

}
