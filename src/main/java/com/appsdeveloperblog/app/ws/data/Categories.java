package com.appsdeveloperblog.app.ws.data;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Categories {

    APPAREL_AND_ACCESSORIES_CLOTHING("clothing"),
    APPAREL_AND_ACCESSORIES_SHOES("shoes"),
    APPAREL_AND_ACCESSORIES_OTHER("other"),
    APPAREL_AND_ACCESSORIES("Apparel and accessories", Set.of(APPAREL_AND_ACCESSORIES_CLOTHING, APPAREL_AND_ACCESSORIES_SHOES, APPAREL_AND_ACCESSORIES_OTHER)),

    CONSUMER_ELECTRONICS_MOBILE_PHONES("Mobile phones"),
    CONSUMER_ELECTRONICS_NOTEBOOKS("Notebooks"),
    CONSUMER_ELECTRONICS("Consumer Electronic", Set.of(CONSUMER_ELECTRONICS_MOBILE_PHONES, CONSUMER_ELECTRONICS_NOTEBOOKS)),


    BOOKS("Books"),
    MOVIES("Movies"),
    MUSIC("Music"),
    GAMES("Games"),
    HEALTH("Health"),
    PERSONAL_CARE("Personal care"),
    BEAUTY("Beauty"),
    FOOD_AND_BEVERAGES("Food and Beverages"),
    AUTO_AND_PARTS("Auto and parts"),
    FURNITURE_AND_DECOR("Furniture and decor");

    private final String description;
    private Set<Categories> children = new HashSet<>();

    Categories(String description) {
        this.description = description;
    }

    Categories(String description, Set<Categories> children) {
        this.description = description;
        this.children = children;
    }

}

