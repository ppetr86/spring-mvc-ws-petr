package com.appsdeveloperblog.app.ws.data.entitydto;

import com.appsdeveloperblog.app.ws.data.entity.CreditCardEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
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
