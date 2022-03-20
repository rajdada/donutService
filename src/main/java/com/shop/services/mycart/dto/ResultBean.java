package com.shop.services.mycart.dto;

public class ResultBean
{
    private String code;
    private String message;

    public ResultBean(String errorCode, String errorMessage)
    {
        this.code = errorCode;
        this.message = errorMessage;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
