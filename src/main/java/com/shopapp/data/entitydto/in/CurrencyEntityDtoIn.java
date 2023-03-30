package com.shopapp.data.entitydto.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "currencyentitydtoin")
public class CurrencyEntityDtoIn  extends AbstractIdBasedDtoIn {

    @XmlElement(name = "name")//needed in case property in obj and xml would be different
    @JsonProperty("name") //needed in case property in obj and json would be different
    private String name;

    private String symbol;

    private String code;

}
