package com.jb.coupons.beans;

import lombok.Getter;

/**
 * The enum Category enum.
 */
@Getter
public enum CategoryEnum {
    /**
     * None category enum.
     */
    NONE(0),
    /**
     * Food category.
     */
    FOOD(1),
    /**
     * Electricity category.
     */
    ELECTRICITY(2),
    /**
     * Dining category.
     */
    DINING(3),
    /**
     * Vacation category.
     */
    VACATION(4),
    /**
     * Gaming category.
     */
    GAMING(5),
    /**
     * Cosmetics category.
     */
    COSMETICS(6),
    /**
     * Kitchenware category.
     */
    KITCHENWARE(7),
    /**
     * Hardware category.
     */
    HARDWARE(8),
    /**
     * Training category.
     */
    TRAINING(9),
    /**
     * Office category.
     */
    OFFICE(10);

    /**
     * The Index.
     */
    public int index;

    /**
     * Instantiates a new Category.
     *
     * @param i the
     */
    CategoryEnum(int i){
        index=i;
    }

}

