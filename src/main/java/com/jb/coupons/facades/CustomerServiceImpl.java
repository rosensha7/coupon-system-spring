package com.jb.coupons.facades;

import com.jb.coupons.beans.CategoryEnum;
import com.jb.coupons.beans.Coupon;
import com.jb.coupons.beans.Customer;
import com.jb.coupons.exceptions.CouponException;
import com.jb.coupons.exceptions.CustomerException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Customer service.
 */
@Service
@Component
public class CustomerServiceImpl extends ClientFacade implements CustomerService {

    //the id of the logged in customer
    private int customerId;

    public boolean login(String email, String password) {
        Customer customer = customerRepository.findByEmailAndPassword(email, password);
        if (customer == null) {//no matching customers
            return false;
        } else {//a customer matched
            this.customerId = customer.getId();
            return true;
        }
    }
    public int loginAndReturnCustomerId(String email, String password) {
        Customer customer = customerRepository.findByEmailAndPassword(email, password);
        if (customer == null) {//no matching customers
            return 0;
        } else {//a customer matched
            this.customerId = customer.getId();
            return customerId;
        }
    }

    public List<Coupon> getCustomerCoupons() throws CustomerException {
        if (customerRepository.existsById(customerId))
            return customerRepository.findById(customerId).getCouponList();
        else
            throw new CustomerException("This customer ID doesn't exist!");
    }

    public Coupon getOneCoupon(int couponId) throws CustomerException {
        if (couponRepository.existsById(couponId))
            return couponRepository.findById(couponId);
        else
            throw new CustomerException("This coupon ID doesn't exist!");
    }

    public void purchaseCoupon(Coupon coupon) throws CouponException, CustomerException {
        Coupon purchasedCoupon = couponRepository.findById(coupon.getId());
        Customer customer = customerRepository.findById(customerId);

        //check if the customer already bought this coupon before. If yes, he can't purchase.
        if (customer != null) {
            List<Coupon> matchingCustomerCoupons = customer.getCouponList().parallelStream().filter(c -> coupon.getId() == c.getId()).collect(Collectors.toList());
            if (!matchingCustomerCoupons.isEmpty()) {
                System.out.println("*ERROR* Purchase failed. This customer already has this coupon.");
                throw new CustomerException("This customer already purchased this coupon!");
            }
        }

        if (purchasedCoupon == null) {
            throw new CouponException("This coupon doesn't exist.");
        } else if (purchasedCoupon.getAmount() <= 0) {
            throw new CouponException("This coupon is out of stock.");
        } else if (purchasedCoupon.getEndDate().before(Calendar.getInstance().getTime())) {
            throw new CouponException("This coupon is expired.");
        } else {//All is good
            customer.getCouponList().add(purchasedCoupon);
            customerRepository.saveAndFlush(customer);
            purchasedCoupon.setAmount(purchasedCoupon.getAmount() - 1);
            couponRepository.saveAndFlush(purchasedCoupon);
        }
    }

    public List<Coupon> getCustomerCouponsByCategory(CategoryEnum categoryEnum) throws CustomerException {
        if (customerRepository.existsById(customerId) && categoryRepository.existsByCategoryId(categoryEnum)) {
            Customer customer = customerRepository.findById(customerId);
            return customer.getCouponList().parallelStream()
                    .filter(coupon -> coupon.getCategoryId() == categoryEnum)
                    .collect(Collectors.toList());
        } else
            throw new CustomerException("Input error. Invalid ID or category.");
    }

    public List<Coupon> getCustomerCouponsByMaxPrice(Double maxPrice) throws CustomerException {
        if (customerRepository.existsById(customerId)) {
            return customerRepository.findById(customerId).getCouponList().parallelStream()
                    .filter(coupon -> coupon.getPrice() < maxPrice)
                    .collect(Collectors.toList());
        } else
            throw new CustomerException("This customer ID doesn't exist!");
    }

    public Customer getCustomerDetails() throws CustomerException {
        if (customerRepository.existsById(customerId))
            return customerRepository.findById(customerId);
        else
            throw new CustomerException("This customer ID doesn't exist");
    }
}
