package com.orange.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.orange.model.Cliper;

@Getter
@Setter
public class CliperDTO {
    private String identifier;
    private long messageCreationTime;
    private String status;
    private String entityId;
    private List<ControlParameterDTO> params = new  ArrayList<>();

    public static CliperDTO convert(Cliper cliper) {
        CliperDTO cliperDTO = new CliperDTO();
        cliperDTO.setIdentifier(cliper.getIdentifier());
        cliperDTO.setMessageCreationTime(cliper.getMessageCreationTime());
        cliperDTO.setStatus(cliper.getStatus());
        cliperDTO.setEntityId(cliper.getEntityId());
        cliperDTO.setParams(cliper.getParams().stream().map(i -> ControlParameterDTO.convert(i)).collect(Collectors.toList()));
        return cliperDTO;
    }

}
