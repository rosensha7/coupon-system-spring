package com.jb.coupons.controllers;

import com.jb.coupons.beans.ClientType;
import com.jb.coupons.beans.UserDetails;
import com.jb.coupons.beans.UserDetailsToken;
import com.jb.coupons.facades.AdminService;
import com.jb.coupons.facades.CompanyService;
import com.jb.coupons.facades.CustomerService;
import com.jb.coupons.security.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("jwt")
@RequiredArgsConstructor
public class LoginController {


    private final CompanyService companyService;
    private final CustomerService customerService;
    private final AdminService adminService;

    //SHOULD BE POST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //token should be sent in header! .header("Authorization", jwtUtil.generateToken(~))
    //delete this

    @PostMapping("login/submit")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails){
        System.out.println("Received userDetails: " + userDetails);

        //first - need to check with database if the user exists
        if(!checkUserExists(userDetails)) {
            return new ResponseEntity<>("Incorrect credentials. Please try again.", HttpStatus.UNAUTHORIZED);//unauthorized
        }

        String myToken = JWTutil.generateToken(userDetails);
        UserDetailsToken userDetailsToken = UserDetailsToken.builder()
                .token(myToken)
                .clientType(userDetails.clientType)
                .email(userDetails.userEmail)
                .build();
        return ResponseEntity.ok()
                .header("Authorization", myToken)
                .body(userDetailsToken);
    }

    @GetMapping("test")
    public ResponseEntity<?> noLogin(){
        return new ResponseEntity<>("hello guest", HttpStatus.OK);
    }

    @GetMapping("admin")
    public ResponseEntity<?> checkToken(@RequestHeader(name="Authorization") String token){
        //create user details from given params
        UserDetails userDetails = UserDetails.builder()
                .clientType(ClientType.Administrator)
                .userEmail("admin@admin.com")
                .password("admin")
                .build();

        //check token valid
        boolean tokenValid = JWTutil.validateToken(token/*, userDetails*/);

        if(tokenValid){
            //since the given token was valid, ill send back a new token
            return new ResponseEntity<>(JWTutil.generateToken(userDetails),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Incorrect admin credentials!", HttpStatus.BAD_REQUEST); //unauthorized
        }
    }

    private boolean checkUserExists(UserDetails userDetails) {
        ClientType clientType = userDetails.clientType;
        boolean isExists = false;
        switch (clientType){
            case Administrator:
                isExists = adminService.login(userDetails.userEmail, userDetails.password);
                break;
            case Company:
                isExists = companyService.login(userDetails.userEmail, userDetails.password);
                break;
            case Customer:
                isExists = customerService.login(userDetails.userEmail, userDetails.password);
                break;
        }
        return isExists;
    }


}
