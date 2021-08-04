package com.jb.coupons.controllers;

import com.jb.coupons.beans.Company;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.exceptions.CustomerException;
import com.jb.coupons.facades.AdminService;
import com.jb.coupons.facades.CategoryService;
import com.jb.coupons.facades.CompanyService;
import com.jb.coupons.facades.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("guest")
public class GuestController {

    private final CategoryService categoryService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final CompanyService companyService;

    @CrossOrigin
    @GetMapping("coupons/{id}")
    public ResponseEntity<?> getOneCoupon(@PathVariable int id) throws CustomerException {
        System.out.println(customerService.getOneCoupon(id));
        return ResponseEntity.ok(customerService.getOneCoupon(id));
    }

    @CrossOrigin
    @GetMapping("categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @CrossOrigin
    @GetMapping("all_coupons")
    public ResponseEntity<?> getAllCoupons(){
        List<Coupon> couponList = adminService.getAllCoupons();
        return ResponseEntity.ok(couponList);
    }

    @CrossOrigin
    @GetMapping("all_companies")
    public ResponseEntity<?> getAllCompanies() throws Exception {
        List<Company> companyList = companyService.getAllCompanies();
        return ResponseEntity.ok(companyList);
    }

    @CrossOrigin
    @GetMapping("company/{id}")
    public ResponseEntity<?> getCompanyDetails(@PathVariable int id) throws CompanyException {
        return ResponseEntity.ok(adminService.getOneCompany(id));
    }


}
