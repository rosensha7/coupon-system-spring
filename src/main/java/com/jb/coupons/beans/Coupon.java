package com.jb.coupons.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * The type Coupon.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"companyId", "title"})
})
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    private String description;
    @Column(nullable = false)
    private String title;
    private double price;
    private int amount;
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class) //better handler with date with DateDeserializer.class
    @JsonSerialize(using = DateSerializer.class) //better handler with date with DateDeserializer.class
    private Date startDate;
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date endDate;
    private String image;

    @Column(updatable = false)
    private int companyId;

    @Enumerated(EnumType.ORDINAL)
    private CategoryEnum categoryId;

}
