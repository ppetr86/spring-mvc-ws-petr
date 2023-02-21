package com.appsdeveloperblog.app.ws.shared.dto;

import com.appsdeveloperblog.app.ws.api.converter.IdBasedResource;
import com.appsdeveloperblog.app.ws.data.entitydto.ModelReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoOut extends IdBasedResource {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<ModelReference> addresses = new HashSet<>();
    private Set<ModelReference> roles = new HashSet<>();
}
