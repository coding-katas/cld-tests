package com.orange.dto;

import com.orange.model.ControlParameter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ControlParameterDTO {

    private String key;
    private String value;

    public static ControlParameterDTO convert(ControlParameter controlParameter) {
        ControlParameterDTO controlParameterDTO = new ControlParameterDTO();
        controlParameterDTO.setKey(controlParameter.getKey());
        controlParameterDTO.setValue(controlParameter.getValue());
        return controlParameterDTO;
    }

}
