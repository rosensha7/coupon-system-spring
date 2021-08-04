package com.jb.coupons.exceptions;

/**
 * The type Coupon exception.
 * This exception will be thrown when an error occurs with adding, deleting or fetching a coupon
 */
public class CouponException extends Exception{
    /**
     * Instantiates a new Coupon exception.
     *
     * @param message the message
     */
    public CouponException(String message) {super(message); }
}
