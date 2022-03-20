package com.shop.services.mycart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CustomerDTO
{

    @NotNull
    @JsonProperty("id")
    private Integer id;


    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
}
