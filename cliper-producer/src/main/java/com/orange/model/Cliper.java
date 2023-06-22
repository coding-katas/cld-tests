package com.orange.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import com.orange.dto.CliperDTO;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Cliper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private String status;

    @Column(name = "message_creation_time")
    private long messageCreationTime;

    @Column(name = "entity_id")
    private String entityId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cliper")
    private List<ControlParameter> params;

    public static Cliper convert(CliperDTO cliperDTO) {
        Cliper cliper = new Cliper();
        cliper.setIdentifier(cliperDTO.getIdentifier());
        cliper.setStatus(cliperDTO.getStatus());
        cliper.setMessageCreationTime(cliperDTO.getMessageCreationTime());
        cliper.setParams(cliperDTO
                         .getParams()
                         .stream()
                         .map(i -> ControlParameter.convert(i))
                         .collect(Collectors.toList()));
        cliper.setEntityId(cliperDTO.getEntityId());
        return cliper;
    }

}
