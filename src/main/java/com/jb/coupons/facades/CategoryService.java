package com.jb.coupons.facades;

import com.jb.coupons.beans.Category;
import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.exceptions.CategoryAlreadyExistsException;
import com.jb.coupons.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Category service.
 */
@Service
@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Init category table. This method populates the category table with the hard-coded enum values.
     * It is called as soon as the software starts running.
     *
     * @throws CategoryAlreadyExistsException the category already exists exception
     */
    public void initCategoryTable() throws CategoryAlreadyExistsException {
        for (CategoryEnum ce: CategoryEnum.values()) {
            if(!categoryRepository.existsByCategoryId(ce))
                categoryRepository.save(Category.builder().categoryId(ce).category(ce).build());
            else
                throw new CategoryAlreadyExistsException("This category already exists.");
        }
    }

    public String[] getAllCategories() {
        String[] strCategories = new String[CategoryEnum.values().length];
        for (CategoryEnum ce:CategoryEnum.values()) {
            strCategories[ce.index] = ce.name();
        }
        return strCategories;
    }
}