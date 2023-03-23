package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoOut extends IdBasedResource {

    private String id;
    private String email;
    private boolean isVerified;
    private String photos;
    private AddressDtoOut address;
    private List<RoleDtoOut> roles = new ArrayList<>();
}
