package com.shop.services.mycart.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;


public class SchedulerErrorHandler implements ErrorHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerErrorHandler.class);

    @Override
    public void handleError(Throwable throwable)
    {
        LOG.error(String.format("<ERROR> Scheduler Error : %s", throwable.getLocalizedMessage()), throwable);
    }
}
