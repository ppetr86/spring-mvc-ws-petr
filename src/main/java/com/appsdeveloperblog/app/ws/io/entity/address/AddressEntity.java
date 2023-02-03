package com.appsdeveloperblog.app.ws.io.entity.address;

import com.appsdeveloperblog.app.ws.io.entity.superclass.IdBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressEntity extends IdBasedEntity implements Serializable {


    @Serial
    private static final long serialVersionUID = 8145705110681795088L;

    @Column(length = 30, nullable = false, unique = false)
    private String addressId;

    @Column(length = 15, nullable = false, unique = false)
    private String city;

    @Column(length = 15, nullable = false, unique = false)
    private String country;

    @Column(length = 100, nullable = false, unique = false)
    private String streetName;

    @Column(length = 7, nullable = false, unique = false)
    private String postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return city.equals(that.city) && country.equals(that.country) && streetName.equals(that.streetName) && postalCode.equals(that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, streetName, postalCode);
    }
}
