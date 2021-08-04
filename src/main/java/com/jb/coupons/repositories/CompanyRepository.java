package com.jb.coupons.repositories;

import com.jb.coupons.beans.Company;
import com.jb.coupons.exceptions.CompanyException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


/**
 * The interface Company repository.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    /**
     * Find by email and password company.
     *
     * @param email    the email
     * @param password the password
     * @return the company
     */
    Company findByEmailAndPassword(String email, String password);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);

    /**
     * Exists by name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(String name);

    /**
     * Find by id company.
     *
     * @param id the id
     * @return the company
     */
    Company findById(int id);

//    @Transactional
//    @Modifying
//    @Query(value = "DELETE FROM company WHERE id=:companyID")
//    void deleteCompany(int companyID) throws CompanyException;
//
//    @Transactional
//    @Modifying
//    @Query(value="DELETE FROM coupon WHERE coupon.company_id=:companyId")
//    void deleteCompanyCoupons(int companyID) throws CompanyException;

}
