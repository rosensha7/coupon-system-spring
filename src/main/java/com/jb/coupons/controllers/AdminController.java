package com.jb.coupons.controllers;

import com.jb.coupons.beans.*;
import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.exceptions.CustomerException;
import com.jb.coupons.facades.AdminService;
import com.jb.coupons.security.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;

    @CrossOrigin
    @GetMapping("company/{id}")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name="Authorization") String token,@PathVariable int id) throws CompanyException{
        token = JWTutil.trimToken(token);

        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/)
        {
            Company company = adminService.getOneCompany(id);
            return ResponseEntity.ok().header("Authorization", getNewAdminToken()).body(company);
        }
        else
            throw new CompanyException("No token match.");
    }

    @CrossOrigin
    @GetMapping("companies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name="Authorization") String token) throws CompanyException {
        token = JWTutil.trimToken(token);
        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/) {
            List<Company> companyList = adminService.getAllCompanies();
            return ResponseEntity.ok()
                    .header("Authorization", getNewAdminToken())
                    .body(companyList);
        }else
            throw new CompanyException("No token match.");
    }

    @CrossOrigin
    @GetMapping("customer/{id}")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name="Authorization") String token, @PathVariable int id) throws CustomerException{
        token = JWTutil.trimToken(token);
        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/)
        {
            Customer customer = adminService.getOneCustomer(id);
            return ResponseEntity.ok()
                    .header("Authorization", getNewAdminToken())
                    .body(customer);
        }
        else
            throw new CustomerException("No token match.");
    }

    @CrossOrigin
    @GetMapping("customers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name="Authorization") String token) throws CustomerException {
        token = JWTutil.trimToken(token);

        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/)
        {
            List<Customer> customerList = adminService.getAllCustomers();
            return ResponseEntity.ok()
                    .header("Authorization", getNewAdminToken())
                    .body(customerList);
        }
        else
            throw new CustomerException("No token match.");
    }

    @CrossOrigin
    @PostMapping("addcompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name="Authorization") String token, @RequestBody Company company) throws CompanyException{
        token = JWTutil.trimToken(token);
        System.out.println("company to add: " + company);
        if(JWTutil.validateToken(token))
        {
            adminService.addCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Authorization", getNewAdminToken())
                    .body("Company created successfully.");
        }
        else
            throw new CompanyException("No token match.");
    }

    @CrossOrigin
    @PostMapping("addcustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name="Authorization") String token,
                                         @RequestBody Customer customer) throws CustomerException{
        token = JWTutil.trimToken(token);

        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/)
        {
            adminService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Authorization", getNewAdminToken())
                    .body("Customer created successfully.");
        }
        else
            throw new CustomerException("No token match.");
    }

    @CrossOrigin
    @DeleteMapping("company/{id}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name="Authorization") String token,
                                           @PathVariable int id) throws CompanyException {
        token = JWTutil.trimToken(token);
        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/)
        {
            adminService.deleteCompany(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("Authorization", getNewAdminToken())
                    .body("Company created successfully.");
        }
        else
            throw new CompanyException("No token match.");
    }

    @CrossOrigin
    @DeleteMapping("customer/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name="Authorization") String token,
                                            @PathVariable int id) throws CustomerException {
        token = JWTutil.trimToken(token);
        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/)
        {
            adminService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("Authorization", getNewAdminToken())
                    .body("Customer created successfully.");
        }
        else
            throw new CustomerException("No token match.");
    }

    @CrossOrigin
    @PutMapping("editcompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name="Authorization") String token,
                                           @RequestBody Company company) throws CompanyException {
        token = JWTutil.trimToken(token);
        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/)
        {
            adminService.updateCompany(company);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("Authorization", getNewAdminToken())
                    .body("Company updated successfully.");
        }
        else
            throw new CompanyException("No token match.");
    }

    @CrossOrigin
    @PutMapping("editcustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name="Authorization") String token,
                                            @RequestBody Customer customer) throws CustomerException {
        token = JWTutil.trimToken(token);
        if(JWTutil.validateToken(token)/*JWTutil.validateToken(token,UserDetails.builder().userEmail("admin@admin.com").clientType(ClientType.Administrator).build())*/)
        {
            adminService.updateCustomer(customer);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("Authorization", getNewAdminToken())
                    .body("Customer updated successfully.");
        }
        else
            throw new CustomerException("No token match.");
    }

    private String getNewAdminToken(){
        String newAdminToken = JWTutil.generateToken(UserDetails.builder()
                .userEmail("admin@admin.com")
                .clientType(ClientType.Administrator)
                .build());
        return newAdminToken;
    }
}
