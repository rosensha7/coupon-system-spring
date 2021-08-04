package com.jb.coupons.beans;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * The type Company.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Company{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 20)
    private String password;

    @Singular("coupon")
    @OneToMany(mappedBy="companyId", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Coupon> couponList;

    public boolean removeCoupon(Coupon couponToDelete) {
        if (couponList == null)
            return false;
        return couponList.removeIf((Coupon cp) -> cp.getId() == couponToDelete.getId());
    }
}
