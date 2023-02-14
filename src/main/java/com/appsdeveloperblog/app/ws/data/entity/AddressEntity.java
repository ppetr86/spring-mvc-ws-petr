package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressEntity extends IdBasedEntity implements Serializable {


    @Serial
    private static final long serialVersionUID = 8145705110681795088L;

    @Column(length = 30, nullable = false, unique = false, name = "address_id")
    private String addressId;

    @Column(length = 15, nullable = false, unique = false)
    private String city;

    @Column(length = 15, nullable = false, unique = false)
    private String country;

    @Column(length = 100, nullable = false, unique = false)
    private String streetName;

    @Column(length = 7, nullable = false, unique = false)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    private UserEntity user = null;

    public AddressEntity(AddressDtoIn addressDtoIn) {
        super();
        this.setAddressId(addressDtoIn.getAddressId());
        this.setCity(addressDtoIn.getCity());
        this.setCountry(addressDtoIn.getCountry());
        this.setStreetName(addressDtoIn.getStreetName());
        this.setPostalCode(addressDtoIn.getPostalCode());
    }

    public AddressEntity(String addressId,
                         String city,
                         String country,
                         String streetName,
                         String postalCode) {
        super();
        this.addressId = addressId;
        this.city = city;
        this.country = country;
        this.streetName = streetName;
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equalsId(obj);
    }

    @Override
    public int hashCode() {
        return hashCodeId();
    }
}
