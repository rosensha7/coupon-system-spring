package com.jb.coupons.facades;

import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.beans.Company;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.exceptions.CouponException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Company service.
 */
@Component
public interface CompanyService {

    /**
     * Login boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     */
    boolean login(String email, String password);

    /**
     * Add coupon.
     *
     * @param coupon the coupon
     * @throws CouponException the coupon exception
     */
    int addCoupon(Coupon coupon) throws CouponException;

    /**
     * Delete coupon.
     *
     * @param couponId the coupon id
     * @throws CouponException the coupon exception
     */
    void deleteCoupon(int couponId) throws CouponException;

    /**
     * Update coupon.
     *
     * @param coupon the coupon
     * @throws CouponException the coupon exception
     */
    void updateCoupon(Coupon coupon) throws CouponException;

    /**
     * Gets company coupons.
     *
     * @return the company coupons
     * @throws CompanyException the company exception
     */
    List<Coupon> getCompanyCoupons() throws CompanyException;

    /**
     * Gets company coupons by category.
     *
     * @param categoryEnum the category enum
     * @return the company coupons by category
     * @throws CompanyException the company exception
     */
    List<Coupon> getCompanyCouponsByCategory(CategoryEnum categoryEnum) throws CompanyException;

    /**
     * Gets company coupons by max price.
     *
     * @param maxPrice the max price
     * @return the company coupons by max price
     * @throws CompanyException the company exception
     */
    List<Coupon> getCompanyCouponsByMaxPrice(Double maxPrice) throws CompanyException;

    /**
     * Gets company details.
     *
     * @return the company details
     * @throws Exception the exception
     */
    Company getCompanyDetails() throws CompanyException;

    List<Company> getAllCompanies() throws Exception;

    int loginAndReturnCompanyId(String email, String password);

    Coupon getOneCoupon(int id) throws CompanyException;
}
