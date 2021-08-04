package com.jb.coupons.repositories;

import com.jb.coupons.beans.Category;
import com.jb.coupons.beans.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Category repository.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Exists by category id boolean.
     *
     * @param ce the ce
     * @return the boolean
     */
    boolean existsByCategoryId(CategoryEnum ce);
}

