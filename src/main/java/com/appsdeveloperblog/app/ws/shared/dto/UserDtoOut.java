package com.appsdeveloperblog.app.ws.shared.dto;

import com.appsdeveloperblog.app.ws.data.entitydto.ModelReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoOut extends RepresentationModel<UserDtoOut> {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<ModelReference> addresses;
}
