package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import com.shopapp.data.entitydto.in.CurrencyEntityDtoIn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "currencies")
@NoArgsConstructor
@Getter
@Setter
public class CurrencyEntity extends IdBasedEntity {

    @Column(nullable = false, length = 64, unique = true)
    private String name;

    @Column(nullable = false, length = 3, unique = true)
    private String symbol;

    @Column(nullable = false, length = 4, unique = true)
    private String code;

    public CurrencyEntity(String name, String symbol, String code) {
        super();
        this.name = name;
        this.symbol = symbol;
        this.code = code;
    }

    public CurrencyEntity(CurrencyEntityDtoIn value) {
        this.id = UUID.randomUUID();
        this.name = value.getName();
        this.symbol = value.getSymbol();
        this.code = value.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyEntity that)) return false;
        return super.equalsId(o) || Objects.equals(name, that.name)
                && Objects.equals(symbol, that.symbol)
                && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(name, symbol, code);
    }

    @Override
    public String toString() {
        return name + " - " + code + " - " + symbol;
    }
}
