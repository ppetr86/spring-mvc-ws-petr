package com.shopapp.data.entitydto.out;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDtoOut extends AbstractIdBasedDtoOut {

    private String email;
    private boolean isVerified;
    private String photos;
    private AddressDtoOut address;
    private List<RoleDtoOut> roles = new ArrayList<>();

    public UserDtoOut(String id, String email, boolean isVerified, String photos, AddressDtoOut address, List<RoleDtoOut> roles) {
        super(id);
        this.email = email;
        this.isVerified = isVerified;
        this.photos = photos;
        this.address = address;
        this.roles = roles;
    }

}
