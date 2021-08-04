package com.jb.coupons.exceptions;

/**
 * The type Category already exists exception.
 * This exception occurs when attempting to save a category that already exists in the database
 */
public class CategoryAlreadyExistsException extends Exception{
    /**
     * Instantiates a new Category already exists exception.
     *
     * @param message the message
     */
    public CategoryAlreadyExistsException(String message) {super(message); }
}
