package com.jb.coupons.facades;

import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.beans.Company;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.beans.Customer;
import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.exceptions.CouponException;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Company service.
 */
@Service
@Component
public class CompanyServiceImpl extends ClientFacade implements CompanyService {

    //the id of the logged in company
    private int companyId;

    public boolean login(String email, String password) {
        Company returnedCompany = companyRepository.findByEmailAndPassword(email, password);
        if (returnedCompany == null) {//no matching companies
            return false;
        } else {//a company matched
            this.companyId = returnedCompany.getId();
            return true;
        }
    }

    public int loginAndReturnCompanyId(String email, String password) {
        Company company = companyRepository.findByEmailAndPassword(email, password);
        if (company == null) {//no matching companies
            return 0;
        } else {//a customer matched
            this.companyId = company.getId();
            return companyId;
        }
    }

    @Override
    public Coupon getOneCoupon(int id) throws CompanyException{
        if(couponRepository.existsById(id))
            return couponRepository.findById(id);
        else
            throw new CompanyException("No coupon found.");
    }

    public int addCoupon(Coupon coupon) throws CouponException {
        if (coupon.getEndDate().before(Calendar.getInstance().getTime())) {//check if this coupon is expired
            throw new CouponException("This coupon is expired! Can't add an expired coupon.");
        } else if (!couponRepository.findByTitleAndCompanyId(coupon.getTitle(), companyId).isEmpty()) { //check if this title already exists for another coupon
            throw new CouponException("A coupon with this title already exists for this company!");
        } else{
            coupon.setCompanyId(this.companyId);
            return couponRepository.saveAndFlush(coupon).getId();
        }
    }

    public void deleteCoupon(int couponId) throws CouponException {
        if (couponRepository.existsById(couponId)){
            Coupon couponToDelete = couponRepository.findById(couponId);
            System.out.println("Coupon ID: " + couponId);
            List<Customer> customersWithCoupon = customerRepository.findByCouponListContaining(couponToDelete);
            for(Customer customer:customersWithCoupon){
                customer.removeCoupon(couponToDelete);
                customerRepository.save(customer);
            }
            Company companyWithCoupon = companyRepository.findById(companyId);
            companyWithCoupon.removeCoupon(couponToDelete);
            companyRepository.save(companyWithCoupon);
            couponRepository.delete(couponToDelete);
        }
        else
            throw new CouponException("You can't delete this coupon because it doesn't exist.");
    }

    public void updateCoupon(Coupon coupon) throws CouponException {
        try {
            if (couponRepository.existsById(coupon.getId())) {
                couponRepository.saveAndFlush(coupon);
            }
        } catch (JDBCException jdbcException) {
            System.out.println("This company already has a coupon with this title!");
            throw new CouponException("This company already has a coupon with this title!");
        }
    }

    public List<Coupon> getCompanyCoupons() throws CompanyException {
        if (companyRepository.existsById(companyId))
            return couponRepository.findByCompanyId(companyId);
        else
            throw new CompanyException("The company with ID " + companyId + " doesn't exist.");
    }

    public List<Coupon> getCompanyCouponsByCategory(CategoryEnum categoryEnum) throws CompanyException {
        if (companyRepository.existsById(companyId) && categoryRepository.existsByCategoryId(categoryEnum))
            return couponRepository.findByCompanyIdAndCategoryId(companyId, categoryEnum);
        else
            throw new CompanyException("Input error. Invalid ID or category.");
    }

    public List<Coupon> getCompanyCouponsByMaxPrice(Double maxPrice) throws CompanyException {
        if (!companyRepository.existsById(companyId)) {
            throw new CompanyException("The company with ID " + companyId + " doesn't exist.");
        }
        List<Coupon> companyCoupons = couponRepository.findByCompanyId(companyId);
        if (!(companyCoupons == null) && !companyCoupons.isEmpty()) {
            return companyCoupons.parallelStream()
                    .filter(coupon -> coupon.getCompanyId() == companyId && coupon.getPrice() < maxPrice)
                    .collect(Collectors.toList());
        }
        return companyCoupons;
    }

    public Company getCompanyDetails() throws CompanyException {
        if(companyRepository.existsById(companyId))
            return companyRepository.findById(companyId);
        else
            throw new CompanyException("The company with ID " + companyId + " doesn't exist.");
    }


    public List<Company> getAllCompanies() throws Exception {
        return companyRepository.findAll();
    }
}
