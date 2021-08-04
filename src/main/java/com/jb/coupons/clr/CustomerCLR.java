package com.jb.coupons.clr;

import com.jb.coupons.beans.*;
import com.jb.coupons.facades.ClientFacade;
import com.jb.coupons.facades.CustomerService;
import com.jb.coupons.facades.LoginManagerClass;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * The type Customer clr.
 */
@Component
@RequiredArgsConstructor
@Order(3)
public class CustomerCLR implements CommandLineRunner {

    private final LoginManagerClass loginManager;

    @Override
    public void run(String... args) throws Exception {
        try {

            System.out.println("*********************************");
            System.out.println("****TEST START - CUSTOMER CLR****");
            System.out.println("*********************************");

            ClientFacade cf = loginManager.login("israel@israeli.com", "a12345678", ClientType.Customer);

            //get one customer (the one that successfully logged in)
            System.out.println();
            System.out.println("Get customer details: ");
            System.out.println(((CustomerService) cf).getCustomerDetails());

            //purchase coupon
            System.out.println();
            System.out.println("Purchase coupons with IDs 3 and 4: ");
            ((CustomerService) cf).purchaseCoupon(Coupon.builder().id(3).build());
            ((CustomerService) cf).purchaseCoupon(Coupon.builder().id(4).build());
            System.out.println("Purchase coupon finished.");

            System.out.println();
            System.out.println("Purchase coupon that doesn't exist (id 333):");
            try {
                ((CustomerService) cf).purchaseCoupon(Coupon.builder().id(333).build());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
            System.out.println("Purchase a coupon with amount of 0 (with ID 4):");
            try {
                ((CustomerService) cf).purchaseCoupon(Coupon.builder().id(4).build());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //get all customer coupons
            System.out.println();
            System.out.println("Get all customer coupons:");
            ((CustomerService) cf).getCustomerCoupons().forEach(System.out::println);

            //get all customer coupons by category "Cosmetics"
            System.out.println();
            System.out.println("Get all customer coupons by category cosmetics:");
            ((CustomerService) cf).getCustomerCouponsByCategory(CategoryEnum.COSMETICS).forEach(System.out::println);

            //get all customer coupons under 100 shekels
            System.out.println();
            System.out.println("Get all customer coupons under 100 shekels:");
            ((CustomerService) cf).getCustomerCouponsByMaxPrice(100.00).forEach(System.out::println);
        }catch (Exception e){
            System.out.println("Something went wrong (Customer): " + e.getMessage());
        }

    }
}
