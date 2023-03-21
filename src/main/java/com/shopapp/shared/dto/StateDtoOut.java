package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StateDtoOut extends IdBasedResource {

    private String name;

    public StateDtoOut(UUID id, String name) {
        this.setId(String.valueOf(id));
        this.name = name;
    }
}
