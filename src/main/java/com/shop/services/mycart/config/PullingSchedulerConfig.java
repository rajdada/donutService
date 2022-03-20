package com.shop.services.mycart.config;


import com.shop.services.mycart.handler.SchedulerErrorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class PullingSchedulerConfig implements SchedulingConfigurer
{


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar)
    {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(1);
        scheduler.setErrorHandler(new SchedulerErrorHandler());
        scheduler.setThreadNamePrefix("Polling-Scheduler-");
        scheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(scheduler);
    }

}
