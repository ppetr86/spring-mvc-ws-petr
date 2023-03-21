package com.shopapp.data.entitydto;

import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.CreditCardEntity;
import com.shopapp.data.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAddressCreditCardEntityDto {

    private List<UserEntity> users;
    private List<AddressEntity> addresses;
    private List<CreditCardEntity> creditCards;
}
