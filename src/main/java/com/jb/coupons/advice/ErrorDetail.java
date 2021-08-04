package com.jb.coupons.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Error detail.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String key;
    private String value;
}
