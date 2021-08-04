package com.jb.coupons.facades;

import com.jb.coupons.beans.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * The type Login manager class.
 */
@Component
@Lazy
@Service
public class LoginManagerClass {

    @Autowired
    private ApplicationContext ctx;
    private final String adminEmail = "admin@admin.com";
    private final String adminPassword = "admin";

    /**
     * Login client facade.
     *
     * @param email      the email
     * @param password   the password
     * @param clientType the client type (admin/customer/company)
     * @return the appropriate client facade
     */
    public ClientFacade login(String email, String password, ClientType clientType){
        switch (clientType) {
            case Company -> {
                CompanyServiceImpl companyFacade = ctx.getBean(CompanyServiceImpl.class);
                if (companyFacade.login(email, password))
                    return companyFacade;
            }
            case Customer -> {
                CustomerServiceImpl customerFacade = ctx.getBean(CustomerServiceImpl.class);
                if (customerFacade.login(email, password))
                    return customerFacade;
            }
            case Administrator -> {
                AdminServiceImpl adminFacade = ctx.getBean(AdminServiceImpl.class);
                if (email.equals(adminEmail) && password.equals(adminPassword))
                    return adminFacade;
            }
        }
        System.out.println("Wrong credentials. Login failed.");
        return null;
    }

}
