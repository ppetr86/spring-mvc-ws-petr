package com.appsdeveloperblog.app.ws.factories;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.CreditCardEntity;
import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;

import java.util.Set;

public class UserFactory {

    public UserEntity createUser(String petr, String novotny, String s, String encryptedPassword,
                                 String s1, boolean b,
                                 Set<RoleEntity> emptySet,
                                 Set<AddressEntity> emptySet1,
                                 Set<CreditCardEntity> emptySet2) {


        var userFromRepo = new UserEntity();
        userFromRepo.setFirstName(petr);
        userFromRepo.setLastName(novotny);
        userFromRepo.setEmail(s);
        userFromRepo.setEncryptedPassword(encryptedPassword);
        userFromRepo.setEmailVerificationToken(s1);
        userFromRepo.setVerified(b);
        userFromRepo.setRoles(emptySet);
        userFromRepo.setAddresses(emptySet1);
        userFromRepo.setCreditCards(emptySet2);
        //userFromRepo.setShoppingLists(Collections.emptySet());

        return userFromRepo;
    }
}
