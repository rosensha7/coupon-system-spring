package com.jb.coupons.clr;

import com.jb.coupons.beans.*;
import com.jb.coupons.facades.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * The type Admin clr.
 */
@Component
@RequiredArgsConstructor
@Order(1)
public class AdminCLR implements CommandLineRunner {

    private final LoginManagerClass loginManager;

    private final CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {

        try {
            //initialize the category table
            try {
                categoryService.initCategoryTable();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            System.out.println("******************************");
            System.out.println("****TEST START - ADMIN CLR****");
            System.out.println("******************************");

            Customer testCustomer1 = Customer.builder()
                    .firstname("John")
                    .lastname("Smith")
                    .email("john@smith.com")
                    .password("a12345678")
                    .build();

            Customer testCustomer2 = Customer.builder()
                    .firstname("Israel")
                    .lastname("Israeli")
                    .email("israel@israeli.com")
                    .password("a12345678")
                    .build();

            Company testCompany1 = Company.builder()
                    .name("Adidas")
                    .email("adidas@adidas.com")
                    .password("a12345678")
                    .build();

            Company testCompany11 = Company.builder()
                    .name("Nike")
                    .email("nike@nike.com")
                    .password("a12345678")
                    .build();

            //Test: Login with correct credentials
            //Expected: a ClientFacade (success)
            System.out.println("Login test. Expecting success.");
            ClientFacade cf = loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
            System.out.println("Login done.");

            //Test: Login with incorrect credentials
            //Expected: an exception thrown
            System.out.println("Login test. Expecting error thrown saying wrong credentials.");
            try {
                loginManager.login("bla", "bla", ClientType.Company);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //Test: Add customers
            System.out.println();
            System.out.println("Add customers test.");
            Customer addedCustomer1 = ((AdminService) cf).addCustomer(testCustomer1);
            Customer addedCustomer2 = ((AdminService) cf).addCustomer(testCustomer2);
            System.out.println("Adding customer is finished.");

            //Test: Add the same customer twice
            //Expected: An error thrown
            System.out.println("Add customer again test. Expecting error thrown, saying 'customer already exists'.");
            try {
                ((AdminService) cf).addCustomer(testCustomer1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //Add company
            System.out.println();
            System.out.println("Add company test. Expecting success.");
            Company addedCompany1 = ((AdminService) cf).addCompany(testCompany1);
            Company addedCompany11 = ((AdminService) cf).addCompany(testCompany11);
            System.out.println("Adding companies finished.");

            //Test: Add the same company twice
            //Expected: An error thrown
            System.out.println();
            System.out.println("Add same company test. Expecting error thrown.");
            try {
                ((AdminService) cf).addCompany(testCompany1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //Test: Edit company
            System.out.println();
            System.out.println("Editing the password of company with ID " + testCompany1.getId() + " to 'a123456789'.");
            testCompany1.setPassword("a123456789");
            ((AdminService) cf).updateCompany(testCompany1);
            System.out.println("Company with ID " + testCompany1.getId() + " after the edit:");
            System.out.println(((AdminService) cf).getOneCompany(testCompany1.getId()));

            //get all companies
            System.out.println();
            System.out.println("All companies: ");
            ((AdminService) cf).getAllCompanies().forEach(System.out::println);

            //get all customers
            System.out.println();
            System.out.println("All customers: ");
            ((AdminService) cf).getAllCustomers().forEach(System.out::println);

            //get one company
            System.out.println();
            System.out.println("Get one company with ID " + addedCompany1.getId() + ":");
            System.out.println(((AdminService) cf).getOneCompany(addedCompany1.getId()));

            //Test: get one company with an id that doesn't exist
            //Expected: An error thrown
            try {
                System.out.println();
                System.out.println("Get one company that doesn't exist (id 12345):");
                System.out.println(((AdminService) cf).getOneCompany(12345));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //get one customer
            System.out.println();
            System.out.println("Get one customer with ID " + addedCustomer1.getId() + " :");
            System.out.println(((AdminService) cf).getOneCustomer(addedCustomer1.getId()));

            //Test: get one customer with an id that doesn't exist
            //Expected: An error thrown
            try {
                System.out.println();
                System.out.println("Get one customer that doesn't exist (id 12345):");
                System.out.println(((AdminService) cf).getOneCustomer(12345));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //Test: Delete customer
            //Expected: Success (customer deleted)
            System.out.println();
            System.out.println("Delete customer with ID " + addedCustomer1.getId() + ":");
            ((AdminService) cf).deleteCustomer(addedCustomer1.getId());
            System.out.println("Delete finished.");

            //get all customers (after delete)
            System.out.println();
            ((AdminService) cf).getAllCustomers().forEach(System.out::println);

            System.out.println();
            System.out.println("End of admin testing. \n");

        }catch (Exception e){
            System.out.println("Something went wrong (Admin): " + e.getMessage());
        }
    }
}
