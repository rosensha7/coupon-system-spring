package com.jb.coupons.controllers;

import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.beans.Customer;
import com.jb.coupons.beans.UserDetails;
import com.jb.coupons.exceptions.CouponException;
import com.jb.coupons.exceptions.CustomerException;
import com.jb.coupons.facades.CategoryService;
import com.jb.coupons.facades.CustomerService;
import com.jb.coupons.security.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Customer controller.
 */
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Purchase coupon response entity.
     *
     * @param coupon the coupon
     * @return the response entity
     * @throws CustomerException the customer exception
     * @throws CouponException   the coupon exception
     */
    @CrossOrigin
    @PostMapping("purchasecoupon")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name="Authorization") String token,
                                            @RequestBody Coupon coupon) throws CustomerException, CouponException {
        token = JWTutil.trimToken(token);
        JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(JWTutil.extractClientType(token))
                .build();
        if(JWTutil.validateToken(token))
        {
            customerService.purchaseCoupon(coupon);
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body("Purchase successful.");
        }
        else
            throw new CustomerException("No token match.");
    }

    /**
     * Gets customer coupons.
     *
     * @return the customer coupons
     * @throws CustomerException the customer exception
     */
    @CrossOrigin
    @GetMapping("customercoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name="Authorization") String token) throws CustomerException {
        token = JWTutil.trimToken(token);
        JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(JWTutil.extractClientType(token))
                .build();
        if(JWTutil.validateToken(token))
        {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body(customerService.getCustomerCoupons());
        }
        else
            throw new CustomerException("No token match.");
    }

    /**
     * Gets customer details.
     *
     * @return the customer details
     * @throws CustomerException the customer exception
     */
    @CrossOrigin
    @GetMapping("details")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name="Authorization") String token) throws CustomerException {
        token = JWTutil.trimToken(token);
        JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(JWTutil.extractClientType(token))
                .build();
        if(JWTutil.validateToken(token))
        {
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body(customerService.getCustomerDetails());
        }
        else
            throw new CustomerException("No token match.");
    }

    /**
     * Gets customer coupons under a given price point.
     *
     * @param maxPrice the max price
     * @return the customer coupons
     * @throws CustomerException the customer exception
     */
    @CrossOrigin
    @GetMapping("customer/couponsunder/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name="Authorization") String token,
                                                @PathVariable double maxPrice) throws CustomerException {
        List<Coupon> couponList = customerService.getCustomerCouponsByMaxPrice(maxPrice);
        return ResponseEntity.ok(couponList);
    }

    /**
     * Gets customer coupons in a certain category.
     *
     * @param category the category
     * @return the customer coupons
     * @throws CustomerException the customer exception
     */
    @CrossOrigin
    @GetMapping("customer/couponsbycategory/{category}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name="Authorization") String token,
                                                @PathVariable CategoryEnum category) throws CustomerException {
        List<Coupon> couponList = customerService.getCustomerCouponsByCategory(category);
        return ResponseEntity.ok(couponList);
    }

}
