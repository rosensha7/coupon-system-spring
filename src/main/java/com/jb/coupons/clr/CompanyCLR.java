package com.jb.coupons.clr;

import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.beans.ClientType;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.facades.ClientFacade;
import com.jb.coupons.facades.CompanyService;
import com.jb.coupons.facades.LoginManagerClass;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;

/**
 * The type Company clr.
 */
@Component
@RequiredArgsConstructor
@Order(2)
public class CompanyCLR implements CommandLineRunner {

    private final LoginManagerClass loginManager;

    @Override
    public void run(String... args) throws Exception {

        try {

            System.out.println("********************************");
            System.out.println("****TEST START - COMPANY CLR****");
            System.out.println("********************************");

            //create coupon
            Coupon testCoupon1 = Coupon.builder()
                    .title("Adidas Running Shoes")
                    .amount(4)
                    .description("Top of the line shoes for joggers and athletes.")
                    .companyId(1)
                    .image("")
                    .price(13.33)
                    .startDate(new Date(Calendar.getInstance().getTimeInMillis() - 86400000)) //86400000 = 24 hours
                    .endDate(new Date(Calendar.getInstance().getTimeInMillis() + 86400000))
                    .categoryId(CategoryEnum.TRAINING)
                    .build();

            Coupon testCoupon2 = Coupon.builder()
                    .title("Basketball Ball")
                    .amount(14)
                    .description("A professional NBA basketball.")
                    .companyId(1)
                    .image("")
                    .price(33.33)
                    .startDate(new Date(Calendar.getInstance().getTimeInMillis() - 86400000)) //86400000 = 24 hours
                    .endDate(new Date(Calendar.getInstance().getTimeInMillis() + 86400000))
                    .categoryId(CategoryEnum.VACATION)
                    .build();

            Coupon testCoupon3 = Coupon.builder()
                    .title("Sunglasses")
                    .amount(10)
                    .description("Quality sunglasses")
                    .companyId(1)
                    .image("")
                    .price(50.33)
                    .startDate(new Date(Calendar.getInstance().getTimeInMillis() - 86400000)) //86400000 = 24 hours
                    .endDate(new Date(Calendar.getInstance().getTimeInMillis() + 86400000))
                    .categoryId(CategoryEnum.OFFICE)
                    .build();

            Coupon testCoupon4 = Coupon.builder()
                    .title("Tennis Racket")
                    .amount(10)
                    .description("A professional tennis racket.")
                    .companyId(1)
                    .image("")
                    .price(150.33)
                    .startDate(new Date(Calendar.getInstance().getTimeInMillis() - 86400000)) //86400000 = 24 hours
                    .endDate(new Date(Calendar.getInstance().getTimeInMillis() + 86400000))
                    .categoryId(CategoryEnum.KITCHENWARE)
                    .build();

            Coupon expiredCoupon = Coupon.builder()
                    .title("PS4 Controller")
                    .amount(10)
                    .description("An original PS4 controller for gaming")
                    .companyId(2)
                    .image("")
                    .price(150.33)
                    .startDate(new Date(Calendar.getInstance().getTimeInMillis() - 86400000)) //86400000 = 24 hours
                    .endDate(new Date(Calendar.getInstance().getTimeInMillis() + 86400000))
                    .categoryId(CategoryEnum.GAMING)
                    .build();

            Coupon amountZeroCoupon = Coupon.builder()
                    .title("Playstation 4")
                    .amount(0)
                    .description("PlayStation 4 console.")
                    .companyId(2)
                    .image("")
                    .price(1130.33)
                    .startDate(new Date(Calendar.getInstance().getTimeInMillis() - 86400000)) //86400000 = 24 hours
                    .endDate(new Date(Calendar.getInstance().getTimeInMillis() + 86400000))
                    .categoryId(CategoryEnum.ELECTRICITY)
                    .build();

            ClientFacade cf = loginManager.login("adidas@adidas.com", "a123456789", ClientType.Company);

            //Add coupons to company
            System.out.println("Adding 6 coupons...");
            ((CompanyService) cf).addCoupon(testCoupon1);
            ((CompanyService) cf).addCoupon(testCoupon2);
            ((CompanyService) cf).addCoupon(testCoupon3);
            ((CompanyService) cf).addCoupon(testCoupon4);
            ((CompanyService) cf).addCoupon(amountZeroCoupon);
            ((CompanyService) cf).addCoupon(expiredCoupon);
            System.out.println("6 coupons added.");

            //Get one company/company details
            System.out.println();
            System.out.println("Get company details: ");
            System.out.println(((CompanyService) cf).getCompanyDetails());

            //Get company coupons
            System.out.println();
            System.out.println("Get company coupons: ");
            ((CompanyService) cf).getCompanyCoupons().forEach(System.out::println);

            //Update coupon number 1
            System.out.println();
            System.out.println("Updating coupon number 1's description to 'NEW DESCRIPTION'. ");
            ((CompanyService) cf).updateCoupon(Coupon.builder()
                    .id(testCoupon1.getId())
                    .companyId(testCoupon1.getCompanyId())
                    .startDate(testCoupon1.getStartDate())
                    .endDate(testCoupon1.getEndDate())
                    .image(testCoupon1.getImage())
                    .description("NEW DESCRIPTION") //the field we are editing!
                    .title(testCoupon1.getTitle())
                    .price(testCoupon1.getPrice())
                    .amount(testCoupon1.getAmount())
                    .build());
            System.out.println("Update done.");

            //Company coupons after update
            System.out.println();
            System.out.println("Company coupons after update: ");
            ((CompanyService) cf).getCompanyCoupons().forEach(System.out::println);

            //Delete company coupon number 1
            System.out.println();
            System.out.println("Delete the company's coupon number 1: ");
            ((CompanyService) cf).deleteCoupon(testCoupon1.getId());
            System.out.println("Delete done.");

            //Company coupons after delete
            System.out.println();
            System.out.println("Company coupons after delete: ");
            ((CompanyService) cf).getCompanyCoupons().forEach(System.out::println);

            //Get coupons under category
            System.out.println();
            System.out.println("Company coupons under the category OFFICE: ");
            ((CompanyService) cf).getCompanyCouponsByCategory(CategoryEnum.OFFICE).forEach(System.out::println);

            //Get coupons under 100 shekels
            System.out.println();
            System.out.println("Company coupons under 100 shekels: ");
            ((CompanyService) cf).getCompanyCouponsByMaxPrice(100.00).forEach(System.out::println);
        }catch (Exception e){
            System.out.println("Something went wrong (Company): " + e.getMessage());
        }
    }
}
