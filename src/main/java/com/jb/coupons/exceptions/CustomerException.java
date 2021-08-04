package com.jb.coupons.exceptions;

/**
 * The type Customer exception.
 * This exception will be thrown when an error occurs with a customer operation
 */
public class CustomerException extends Exception{
    /**
     * Instantiates a new Customer exception.
     *
     * @param message the message
     */
    public CustomerException(String message) {super(message); }
}
