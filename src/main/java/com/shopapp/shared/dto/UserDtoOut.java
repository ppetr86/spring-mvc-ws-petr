package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
import com.shopapp.data.entitydto.ModelReference;
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
    private Set<ModelReference> roles = new HashSet<>();
}
