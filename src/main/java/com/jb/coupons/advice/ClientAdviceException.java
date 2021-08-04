package com.jb.coupons.advice;

import com.jb.coupons.exceptions.CategoryAlreadyExistsException;
import com.jb.coupons.exceptions.CompanyException;
import com.jb.coupons.exceptions.CouponException;
import com.jb.coupons.exceptions.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Client advice exception.
 */
@RestController
@ControllerAdvice //aop of exception
public class ClientAdviceException {

    /**
     * Handle customer exception error detail.
     *
     * @param e the e
     * @return the error detail
     */
    @ExceptionHandler(value = {CustomerException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCustomerException(Exception e){
        return new ErrorDetail("Customer Exception", e.getMessage());
    }

    /**
     * Handle company exception error detail.
     *
     * @param e the e
     * @return the error detail
     */
    @ExceptionHandler(value = {CompanyException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCompanyException(Exception e){
        return new ErrorDetail("Company Exception", e.getMessage());
    }

    /**
     * Handle coupon exception error detail.
     *
     * @param e the e
     * @return the error detail
     */
    @ExceptionHandler(value = {CouponException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCouponException(Exception e){
        return new ErrorDetail("Coupon Exception", e.getMessage());
    }

    /**
     * Handle category exception error detail.
     *
     * @param e the e
     * @return the error detail
     */
    @ExceptionHandler(value = {CategoryAlreadyExistsException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCategoryException(Exception e){
        return new ErrorDetail("Category Already Exists Exception", e.getMessage());
    }

    /**
     * Handle exception error detail.
     *
     * @param e the e
     * @return the error detail
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception e){
        return new ErrorDetail("An exception was thrown.", e.getMessage());
    }
}
