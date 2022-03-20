package com.shop.services.mycart.exception;

import com.shop.services.mycart.api.ErrorController;
import com.shop.services.mycart.api.OrderController;
import com.shop.services.mycart.constant.Constants;
import com.shop.services.mycart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
public class ErrorControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    ErrorController errorController;

    @MockBean
    private OrderService orderService;


    @Test
    public void testHandleNotFound() throws Exception
    {
        MyCartException exception = new MyCartException(Constants.EC_ORDER_NOT_EXISTS
                , Constants.EM_ORDER_NOT_EXISTS);

        doThrow(exception).when(orderService).deleteOrderByCustomerId(anyInt());

        mockMvc.perform(delete("/deleteOrder/10")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(orderService).deleteOrderByCustomerId(anyInt());
    }


}
