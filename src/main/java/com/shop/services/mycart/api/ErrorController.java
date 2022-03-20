package com.shop.services.mycart.api;

import com.shop.services.mycart.dto.ResultBean;
import com.shop.services.mycart.exception.MyCartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController
{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MyCartException.class)
    public ResponseEntity<ResultBean> handleNotFound(MyCartException exception)
    {
        logger.warn(exception.getErrorMessage());
        return new ResponseEntity<ResultBean>(new ResultBean(exception.getErrorCode(), exception.getErrorMessage())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalError(Exception e)
    {
        logger.error("Unhandled Exception in Controller", e);
        return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
