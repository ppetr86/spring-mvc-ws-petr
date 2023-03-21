package com.shopapp.data;

import java.util.HashSet;
import java.util.Set;

public enum Categories {

    APPAREL_AND_ACCESSORIES_CLOTHING("clothing"),
    APPAREL_AND_ACCESSORIES_SHOES("shoes"),
    APPAREL_AND_ACCESSORIES_OTHER("other"),
    APPAREL_AND_ACCESSORIES_CLOTHING_TOPS("tops"),
    APPAREL_AND_ACCESSORIES_CLOTHING_DRESSES("womens-dresses"),
    APPAREL_AND_ACCESSORIES_WOMEN_SHOES("womens-shoes"),
    APPAREL_AND_ACCESSORIES_CLOTHING_MEN_THIRTS("mens-shirts"),
    APPAREL_AND_ACCESSORIES_CLOTHING_MEN_SHOES("mens-shoes"),
    APPAREL_AND_ACCESSORIES_OTHER_MEN_WATCHES("mens-watches"),
    APPAREL_AND_ACCESSORIES_OTHER_WOMEN_WATCHES("womens-watches"),
    APPAREL_AND_ACCESSORIES_OTHER_WOMEN_BAGS("womens-bags"),
    APPAREL_AND_ACCESSORIES_OTHER_WOMEN_JEWELLERY("womens-jewellery"),
    APPAREL_AND_ACCESSORIES_OTHER_SUNGLASSES("sunglasses"),
    APPAREL_AND_ACCESSORIES("apparel and accessories", Set.of(
            APPAREL_AND_ACCESSORIES_CLOTHING,
            APPAREL_AND_ACCESSORIES_SHOES,
            APPAREL_AND_ACCESSORIES_OTHER,
            APPAREL_AND_ACCESSORIES_CLOTHING_TOPS,
            APPAREL_AND_ACCESSORIES_CLOTHING_DRESSES,
            APPAREL_AND_ACCESSORIES_WOMEN_SHOES,
            APPAREL_AND_ACCESSORIES_CLOTHING_MEN_THIRTS,
            APPAREL_AND_ACCESSORIES_CLOTHING_MEN_SHOES,
            APPAREL_AND_ACCESSORIES_OTHER_MEN_WATCHES,
            APPAREL_AND_ACCESSORIES_OTHER_WOMEN_WATCHES,
            APPAREL_AND_ACCESSORIES_OTHER_WOMEN_BAGS,
            APPAREL_AND_ACCESSORIES_OTHER_WOMEN_JEWELLERY,
            APPAREL_AND_ACCESSORIES_OTHER_SUNGLASSES)),

    MEN("men", Set.of(
            APPAREL_AND_ACCESSORIES_CLOTHING_MEN_THIRTS,
            APPAREL_AND_ACCESSORIES_CLOTHING_MEN_SHOES,
            APPAREL_AND_ACCESSORIES_OTHER_MEN_WATCHES)),
    WOMEN("womens", Set.of(
            APPAREL_AND_ACCESSORIES_WOMEN_SHOES,
            APPAREL_AND_ACCESSORIES_OTHER_WOMEN_WATCHES,
            APPAREL_AND_ACCESSORIES_OTHER_WOMEN_BAGS,
            APPAREL_AND_ACCESSORIES_OTHER_WOMEN_JEWELLERY)),

    CONSUMER_ELECTRONICS_MOBILE_PHONES("mobile phones"),
    CONSUMER_ELECTRONICS_MOBILE_SMARTPHONES("smartphones"),
    CONSUMER_ELECTRONICS_NOTEBOOKS("notebooks"),
    CONSUMER_ELECTRONICS_LAPTOPS("laptops"),
    CONSUMER_ELECTRONICS("consumer electronic", Set.of(
            CONSUMER_ELECTRONICS_MOBILE_PHONES,
            CONSUMER_ELECTRONICS_NOTEBOOKS,
            CONSUMER_ELECTRONICS_MOBILE_SMARTPHONES,
            CONSUMER_ELECTRONICS_LAPTOPS)),

    BEAUTY_FRAGRANCES("fragrances"),
    BEAUTY_SKIN_CARE("skincare"),
    BEAUTY("beauty", Set.of(BEAUTY_FRAGRANCES, BEAUTY_SKIN_CARE)),

    FOOD_AND_BEVERAGES_GROCERIES("groceries"),
    FOOD_AND_BEVERAGES("food and beverages", Set.of(FOOD_AND_BEVERAGES_GROCERIES)),

    FURNITURE_AND_DECOR_HOME_DECORATION("home-decoration"),
    FURNITURE_AND_DECOR_FURNITURE("furniture"),
    FURNITURE_AND_DECOR_LIGHTING("lighting"),
    FURNITURE_AND_DECOR("furniture and decor", Set.of(FURNITURE_AND_DECOR_FURNITURE, FURNITURE_AND_DECOR_HOME_DECORATION, FURNITURE_AND_DECOR_LIGHTING)),

    AUTOMOTIVE_AUTO_AND_PARTS("auto and parts"),
    AUTOMOTIVE("automotive", Set.of(AUTOMOTIVE_AUTO_AND_PARTS)),
    AUTOMOTIVE_MOTO_AND_PARTS("moto and parts"),

    BOOKS("books"),
    MOVIES("movies"),
    MUSIC("music"),
    GAMES("games"),
    HEALTH("health"),
    PERSONAL_CARE("personal care");

    private final String description;
    private Set<Categories> children = new HashSet<>();

    Categories(String description) {
        this.description = description;
    }

    Categories(String description, Set<Categories> children) {
        this.description = description;
        this.children = children;
    }

    public Set<Categories> getChildren() {
        return children;
    }

    public String getDescription() {
        return description;
    }
}

