package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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


    @Column(length = 15, nullable = false, unique = false)
    private String city;

    @Column(length = 15, nullable = false, unique = false)
    private String country;

    @Column(length = 100, nullable = false, unique = false)
    private String street;

    @Column(length = 7, nullable = false, unique = false)
    private String postalCode;

    @ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();

    public AddressEntity(AddressDtoIn addressDtoIn) {
        super();
        this.setCity(addressDtoIn.getCity());
        this.setCountry(addressDtoIn.getCountry());
        this.setStreet(addressDtoIn.getStreet());
        this.setPostalCode(addressDtoIn.getPostalCode());
    }

    public AddressEntity(String city,
                         String country,
                         String street,
                         String postalCode) {
        super();
        this.city = city;
        this.country = country;
        this.street = street;
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressEntity that))
            return false;

        return super.equalsId(o) || Objects.equals(city, that.city) && Objects.equals(country, that.country) && Objects.equals(street, that.street) && Objects.equals(postalCode, that.postalCode) && Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(city, country, street, postalCode);
    }
}
