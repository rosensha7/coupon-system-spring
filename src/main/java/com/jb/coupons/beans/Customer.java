package com.jb.coupons.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.List;

/**
 * The type Customer.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false)
    private int id;
    @Column(nullable = false, length = 50)
    private String firstname;
    @Column(nullable = false, length = 50)
    private String lastname;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 50)
    private String password;

    @ManyToMany
    private List<Coupon> couponList;

    public boolean removeCoupon(Coupon coupon){
        if (couponList == null)
            return false;
        return couponList.removeIf((Coupon cp) -> cp.getId() == coupon.getId());
    }

}
