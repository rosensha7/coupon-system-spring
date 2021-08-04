package com.jb.coupons.facades;

import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.beans.Customer;
import com.jb.coupons.exceptions.CouponException;
import com.jb.coupons.exceptions.CustomerException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Customer service.
 */
@Component
public interface CustomerService {
    /**
     * Login boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     */
    boolean login(String email, String password);

    /**
     * Gets customer coupons.
     *
     * @return the customer coupons
     * @throws CustomerException the customer exception
     */
    List<Coupon> getCustomerCoupons() throws CustomerException;

    Coupon getOneCoupon(int couponId) throws CustomerException;

    /**
     * Gets customer coupons by category.
     *
     * @param categoryEnum the category enum
     * @return the customer coupons by category
     * @throws CustomerException the customer exception
     */
    List<Coupon> getCustomerCouponsByCategory(CategoryEnum categoryEnum) throws CustomerException;

    /**
     * Purchase coupon.
     *
     * @param coupon the coupon
     * @throws CouponException   the coupon exception
     * @throws CustomerException the customer exception
     */
    void purchaseCoupon(Coupon coupon) throws CouponException, CustomerException;

    /**
     * Gets customer coupons by max price.
     *
     * @param maxPrice the max price
     * @return the customer coupons by max price
     * @throws CustomerException the customer exception
     */
    List<Coupon> getCustomerCouponsByMaxPrice(Double maxPrice) throws CustomerException;

    /**
     * Gets customer details.
     *
     * @return the customer details
     * @throws CustomerException the customer exception
     */
    Customer getCustomerDetails() throws CustomerException;

    int loginAndReturnCustomerId(String email, String password);
}
