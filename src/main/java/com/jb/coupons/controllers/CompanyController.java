package com.jb.coupons.controllers;

import com.auth0.jwt.JWT;
import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.beans.ClientType;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.beans.UserDetails;
import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.exceptions.CouponException;
import com.jb.coupons.exceptions.CustomerException;
import com.jb.coupons.facades.CompanyService;
import com.jb.coupons.security.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Company controller.
 */
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Gets company coupons.
     *
     * @return the company coupons
     * @throws CompanyException the company exception
     */
    @CrossOrigin
    @GetMapping("companycoupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name="Authorization") String token) throws CompanyException {
        token = JWTutil.trimToken(token);
        ClientType ct = JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(ct)
                .build();
        if(JWTutil.validateToken(token/*, ud*/))
        {
            List<Coupon> couponList = companyService.getCompanyCoupons();
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body(couponList);
        }
        else
            throw new CompanyException("No token match.");
    }

    @CrossOrigin
    @GetMapping("details")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name="Authorization") String token) throws CompanyException {
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
                    .body(companyService.getCompanyDetails());
        }
        else
            throw new CompanyException("No token match.");
    }

    @CrossOrigin
    @GetMapping("coupon/{id}")
    public ResponseEntity<?> getOneCoupon(@RequestHeader(name="Authorization") String token, @PathVariable int id) throws CompanyException {
        token = JWTutil.trimToken(token);
        ClientType ct = JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(ct)
                .build();
        if(JWTutil.validateToken(token))
        {
            Coupon coupon = companyService.getOneCoupon(id);
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body(coupon);
        }
        else
            throw new CompanyException("No token match.");
    }

    /**
     * Add coupon response entity.
     *
     * @param coupon the coupon
     * @return the response entity
     * @throws CouponException the coupon exception
     */
    @CrossOrigin
    @PostMapping("coupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name="Authorization") String token,
                                       @RequestBody Coupon coupon) throws CouponException {
        token = JWTutil.trimToken(token);
        JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(JWTutil.extractClientType(token))
                .build();
        if(JWTutil.validateToken(token))
        {
            int newId = companyService.addCoupon(coupon);
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body(newId);
        }
        else
            throw new CouponException("No token match.");
    }

    /**
     * Update coupon response entity.
     *
     * @param coupon the coupon
     * @return the response entity
     * @throws CouponException the coupon exception
     */
    @CrossOrigin
    @PutMapping("editcoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name="Authorization") String token,
                                          @RequestBody Coupon coupon) throws CouponException {
        token = JWTutil.trimToken(token);
        JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(JWTutil.extractClientType(token))
                .build();
        if(JWTutil.validateToken(token))
        {
            companyService.updateCoupon(coupon);
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body("Company updated successfully.");
        }
        else
            throw new CouponException("No token match.");
    }

    /**
     * Delete coupon response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws CouponException the coupon exception
     */
    @CrossOrigin
    @DeleteMapping("coupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name="Authorization") String token,
                                          @PathVariable int id) throws CouponException {
        token = JWTutil.trimToken(token);
        JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(JWTutil.extractClientType(token))
                .build();
        if(JWTutil.validateToken(token))
        {
            companyService.deleteCoupon(id);
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body("Coupon deleted successfully.");
        }
        else
            throw new CouponException("No token match.");
    }

    @CrossOrigin
    @GetMapping("company/couponsUnder/{maxPrice}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name="Authorization") String token,
                                               @PathVariable double maxPrice) throws CompanyException {

        token = JWTutil.trimToken(token);
        JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(JWTutil.extractClientType(token))
                .build();
        if(JWTutil.validateToken(token))
        {
            List<Coupon> couponList = companyService.getCompanyCouponsByMaxPrice(maxPrice);
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body(couponList);
        }
        else
            throw new CompanyException("No token match.");
    }

    @CrossOrigin
    @GetMapping("company/couponsByCategory/{category}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name="Authorization") String token,
                                               @PathVariable CategoryEnum category) throws CompanyException {
        token = JWTutil.trimToken(token);
        JWTutil.extractClientType(token);
        UserDetails ud = UserDetails.builder()
                .userEmail(JWTutil.extractEmail(token))
                .clientType(JWTutil.extractClientType(token))
                .build();
        if(JWTutil.validateToken(token))
        {
            List<Coupon> couponList = companyService.getCompanyCouponsByCategory(category);
            return ResponseEntity.ok()
                    .header("Authorization", JWTutil.generateToken(ud))
                    .body(couponList);
        }
        else
            throw new CompanyException("No token match.");
    }

}
