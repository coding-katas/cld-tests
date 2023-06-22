package com.orange.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import com.orange.dto.ControlParameterDTO;

@Getter
@Setter
@Entity(name = "cliper_param")
public class ControlParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "param_key")
    private String key;
    @Column(name = "param_value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "cliper_id")
    private Cliper cliper;

    public static ControlParameter convert(ControlParameterDTO controlParameterDTO) {
        ControlParameter controlParameter = new ControlParameter();
        controlParameter.setKey(controlParameterDTO.getKey());
        controlParameter.setValue(controlParameterDTO.getValue());
        return controlParameter;
    }

}
