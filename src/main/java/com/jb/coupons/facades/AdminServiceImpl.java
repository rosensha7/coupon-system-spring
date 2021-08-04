package com.jb.coupons.facades;

import com.jb.coupons.beans.Company;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.beans.Customer;
import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.exceptions.CustomerException;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Admin service.
 */
@Service
@Component
public class AdminServiceImpl extends ClientFacade implements AdminService{

    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public Company addCompany(Company company) throws CompanyException {
        if(!companyRepository.existsById(company.getId())
                && !companyRepository.existsByEmail(company.getEmail())
                && !companyRepository.existsByName(company.getName())){
            return companyRepository.saveAndFlush(company);
        }
        else
            throw new CompanyException("This company already exists.");
    }

    public void updateCompany(Company company) throws CompanyException {
        if(companyRepository.existsById(company.getId())){
            Company editedCompany = companyRepository.findById(company.getId());
            editedCompany.setPassword(company.getPassword());
            editedCompany.setEmail(company.getEmail());
            editedCompany.setName(company.getName());
            companyRepository.save(editedCompany);
        }
        else
            throw new CompanyException("A company with this ID doesn't exist.");
    }

    public void deleteCompany(int companyID) throws CompanyException {
        if(companyRepository.existsById(companyID)){
            Company dbCompany = companyRepository.findById(companyID);
            for(Coupon c : dbCompany.getCouponList()){
                List<Customer> customersWithCoupon = customerRepository.findByCouponListContaining(c);
                for(Customer customer: customersWithCoupon){
                    customer.removeCoupon(c);
                }
                couponRepository.deleteById(c.getId());
            }
            companyRepository.deleteById(companyID);
        }
        else
            throw new CompanyException("This company doesn't exist.");
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }

    public Company getOneCompany(int companyID) throws CompanyException {
        if(companyRepository.existsById(companyID))
            return companyRepository.findById(companyID);
        else
            throw new CompanyException("A company with this ID doesn't exists.");
    }

    public Customer addCustomer(Customer customer) throws CustomerException {
        if(!customerRepository.existsById(customer.getId()) && !customerRepository.existsByEmail(customer.getEmail()))
            return customerRepository.saveAndFlush(customer);
        else
            throw new CustomerException("This customer already exists.");
    }

    public void deleteCustomer(int customerId) throws CustomerException {
        if(customerRepository.existsById(customerId))
            customerRepository.deleteById(customerId);
        else
            throw new CustomerException("You can't delete a customer that doesn't exist.");
    }

    public void updateCustomer(Customer customer) throws CustomerException {
        try {
            if(customerRepository.existsById(customer.getId())) {
                Customer editedCustomer = customerRepository.findById(customer.getId());
                editedCustomer.setEmail(customer.getEmail());
                editedCustomer.setFirstname(customer.getFirstname());
                editedCustomer.setLastname(customer.getLastname());
                editedCustomer.setPassword(customer.getPassword());
//                System.out.println(customer);
                customerRepository.save(editedCustomer);
            }
            else
                throw new CustomerException("This customer doesn't exists.");
        }catch (JDBCException jdbcException){//in case the user attempts to edit an email address and its already taken by another customer
            System.out.println("You can't use this email address. It already exists for another customer.");
            throw new CustomerException("You can't update to this email. It already exists.");
        }
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(int id) throws CustomerException {
        if(!customerRepository.existsById(id))
            throw new CustomerException("This customer doesn't exists.");
        return customerRepository.findById(id);
    }
}
