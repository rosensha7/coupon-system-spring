package com.jb.coupons.facades;

import com.jb.coupons.beans.Company;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.beans.Customer;
import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.exceptions.CustomerException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The interface Admin service.
 */
@Component
public interface AdminService {

    /**
     * Login boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     * @throws CompanyException the company exception
     */
    boolean login(String email, String password);

    public List<Coupon> getAllCoupons();

    /**
     * Add company company.
     *
     * @param company the company
     * @return the company
     * @throws CompanyException the company exception
     */
    Company addCompany(Company company) throws CompanyException;

    /**
     * Update company.
     *
     * @param company the company
     * @throws CompanyException the company exception
     */
    void updateCompany(Company company) throws CompanyException;

    /**
     * Delete company.
     *
     * @param companyID the company id
     * @throws CompanyException the company exception
     */


    void deleteCompany(int companyID) throws CompanyException;
//    @Query(value="DELETE FROM customer_coupon_list WHERE coupon.company_id=:companyId")
//    void deleteCompanyCoupons(int companyID) throws  CompanyException;

    /**
     * Gets all companies.
     *
     * @return the all companies
     */
    List<Company> getAllCompanies();

    /**
     * Gets one company.
     *
     * @param companyID the company id
     * @return the one company
     * @throws CompanyException the company exception
     */
    Company getOneCompany(int companyID) throws CompanyException;

    /**
     * Add customer customer.
     *
     * @param customer the customer
     * @return the customer
     * @throws CustomerException the customer exception
     */
    Customer addCustomer(Customer customer) throws CustomerException;

    /**
     * Delete customer.
     *
     * @param customerId the customer id
     * @throws CustomerException the customer exception
     */
    void deleteCustomer(int customerId) throws CustomerException;

    /**
     * Update customer.
     *
     * @param customer the customer
     * @throws CustomerException the customer exception
     */
    void updateCustomer(Customer customer) throws CustomerException;

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    List<Customer> getAllCustomers();

    /**
     * Gets one customer.
     *
     * @param id the id
     * @return the one customer
     * @throws CustomerException the customer exception
     */
    Customer getOneCustomer(int id) throws CustomerException;
}
