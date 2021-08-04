package com.jb.coupons.facades;

import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.repositories.CategoryRepository;
import com.jb.coupons.repositories.CompanyRepository;
import com.jb.coupons.repositories.CouponRepository;
import com.jb.coupons.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Client facade.
 */
@Component
public abstract class ClientFacade {

    /**
     * The Coupon repository.
     */
    @Autowired
    protected CouponRepository couponRepository;

    /**
     * The Customer repository.
     */
    @Autowired
    protected CustomerRepository customerRepository;

    /**
     * The Company repository.
     */
    @Autowired
    protected CompanyRepository companyRepository;

    /**
     * The Category repository.
     */
    @Autowired
    protected CategoryRepository categoryRepository;

    /**
     * Login boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     * @throws CompanyException the company exception
     */
    public abstract boolean login(String email, String password) throws CompanyException;

}
