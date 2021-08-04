package com.jb.coupons.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The type Category.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @Enumerated(EnumType.ORDINAL)
    private CategoryEnum categoryId;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
}
