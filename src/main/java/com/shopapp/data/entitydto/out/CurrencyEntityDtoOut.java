package com.shopapp.data.entitydto.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopapp.data.entity.CurrencyEntity;
import com.shopapp.data.entitydto.in.CurrencyEntityDtoIn;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "currencyentitydtoin")
public class CurrencyEntityDtoOut extends AbstractIdBasedDtoOut {

    @XmlElement(name = "name")//needed in case property in obj and xml would be different
    @JsonProperty("name") //needed in case property in obj and json would be different
    private String name;

    private String symbol;

    private String code;

    public CurrencyEntityDtoOut(CurrencyEntity value) {
        this.id=value.getId().toString();
        this.name = value.getName();
        this.symbol = value.getSymbol();
        this.code = value.getCode();
    }

    public CurrencyEntityDtoOut(CurrencyEntityDtoIn item) {
        this.id = UUID.randomUUID().toString();//TODO: just a dummy stuff for testing
        this.name = item.getName();
        this.symbol = item.getSymbol();
        this.code = item.getCode();
    }
}
