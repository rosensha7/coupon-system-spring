package com.jb.coupons.repositories;

import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * The interface Coupon repository.
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    /**
     * Find by company id list.
     *
     * @param companyId the company id
     * @return the list
     */
    List<Coupon> findByCompanyId(int companyId);

    /**
     * Find by company id and category id list.
     *
     * @param companyId  the company id
     * @param categoryId the category id
     * @return the list
     */
    List<Coupon> findByCompanyIdAndCategoryId(int companyId, CategoryEnum categoryId);

    /**
     * Find by title and company id list.
     *
     * @param title     the title
     * @param companyId the company id
     * @return the list
     */
    List<Coupon> findByTitleAndCompanyId(String title, int companyId);

    /**
     * Find by id coupon.
     *
     * @param id the id
     * @return the coupon
     */
    Coupon findById(int id);

    /**
     * Delete by end date before.
     *
     * @param time the time
     */
    @Transactional
    void deleteByEndDateBefore(Date time);

    @Transactional
    void deleteByCompanyId(int companyID);
}
