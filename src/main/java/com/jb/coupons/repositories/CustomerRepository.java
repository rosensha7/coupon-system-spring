package com.jb.coupons.repositories;

import com.jb.coupons.beans.Coupon;
import com.jb.coupons.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Customer repository. Used for interacting with the database via JPA.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Find customer by email and password.
     *
     * @param email    the client's email
     * @param password the client's password
     * @return the customer (if email and password are correct)
     */
    Customer findByEmailAndPassword(String email, String password);

    /**
     * Find customer by id.
     *
     * @param customerId the customer id
     * @return the customer
     */
    Customer findById(int customerId);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);

    List<Customer> findByCouponListContaining(Coupon c);
}
