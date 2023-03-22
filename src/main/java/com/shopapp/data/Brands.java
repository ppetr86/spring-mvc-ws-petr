package com.shopapp.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

import static com.shopapp.data.Categories.*;

@Getter
@AllArgsConstructor
public enum Brands {

    NIKE("nike", Set.of(APPAREL_AND_ACCESSORIES)),
    H_M("h&m", Set.of(APPAREL_AND_ACCESSORIES)),
    ZARA("zara", Set.of(APPAREL_AND_ACCESSORIES)),
    ADIDAS("adidas", Set.of(APPAREL_AND_ACCESSORIES)),
    UNIQLO("uniqlo", Set.of(APPAREL_AND_ACCESSORIES)),

    BATA("bata", Set.of(APPAREL_AND_ACCESSORIES)),
    GEOX("geox", Set.of(APPAREL_AND_ACCESSORIES)),
    PUMA("puma", Set.of(APPAREL_AND_ACCESSORIES)),
    OSIRIS_SHOES("osiris shoes", Set.of(APPAREL_AND_ACCESSORIES)),

    OAKLEY("oakley", Set.of(APPAREL_AND_ACCESSORIES)),

    SAMSUNG("samsung", Set.of(CONSUMER_ELECTRONICS)),
    IPHONE("iphone", Set.of(CONSUMER_ELECTRONICS)),
    BLACKBERRY("blackberry", Set.of(CONSUMER_ELECTRONICS)),

    HP("hp", Set.of(CONSUMER_ELECTRONICS)),
    LENOVO("lenovo", Set.of(CONSUMER_ELECTRONICS)),
    ASUS("asus", Set.of(CONSUMER_ELECTRONICS)),
    ACER("acer", Set.of(CONSUMER_ELECTRONICS)),

    DOVE("dove", Set.of(PERSONAL_CARE)),
    NIVEA("nivea", Set.of(PERSONAL_CARE)),
    GILLETTE("gillette", Set.of(PERSONAL_CARE)),

    COCA_COLA("coca cola", Set.of(FOOD_AND_BEVERAGES)),
    NESTLE("nestle", Set.of(FOOD_AND_BEVERAGES)),
    PEPSI("pepsi", Set.of(FOOD_AND_BEVERAGES)),
    JBS("jbs", Set.of(FOOD_AND_BEVERAGES)),
    ANHEUSER_BUSCH("anheuser busch", Set.of(FOOD_AND_BEVERAGES)),

    BMW("bmw", Set.of(AUTOMOTIVE)),
    MERCEDES_BENZ("mercedes benz", Set.of(AUTOMOTIVE)),
    KIA("kia", Set.of(AUTOMOTIVE)),
    SKODA("skoda", Set.of(AUTOMOTIVE)),
    SEAT("seat", Set.of(AUTOMOTIVE)),
    PORSCHE("porsche", Set.of(AUTOMOTIVE)),

    CASTLERY("castlery", Set.of(FURNITURE_AND_DECOR)),
    ARHAUS("arhaus", Set.of(FURNITURE_AND_DECOR)),
    DESIGN_WITHIN_REACH("design within reach", Set.of(FURNITURE_AND_DECOR)),
    IKEA("ikea", Set.of(FURNITURE_AND_DECOR)),
    ASKO("asko", Set.of(FURNITURE_AND_DECOR)),
    XXL_LUTZ("xxl lutz", Set.of(FURNITURE_AND_DECOR)),
    ;

    private final String description;
    private final Set<Categories> categories;
}

