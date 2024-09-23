package com.scaler.ecommerceprojectjune24.advice;

import com.scaler.ecommerceprojectjune24.dto.ErrorDTO;
import com.scaler.ecommerceprojectjune24.exception.CategoryNotFoundException;
import com.scaler.ecommerceprojectjune24.exception.ProductAlreadyExistsException;
import com.scaler.ecommerceprojectjune24.exception.ProductNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    // Whenever ProductNotFoundException is found this method is executed
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorDTO handleProductNotFoundException(ProductNotFoundException exception){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(exception.getMessage());
        errorDTO.setCode("Some status code");
        return errorDTO;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ErrorDTO handleCategoryNotFoundException(CategoryNotFoundException exception){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(exception.getMessage());
        errorDTO.setCode("Some status code");
        return errorDTO;
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ErrorDTO handleProductAlreadyExistsException(ProductAlreadyExistsException exception){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(exception.getMessage());
        errorDTO.setCode("Some status code");
        return errorDTO;
    }
}
