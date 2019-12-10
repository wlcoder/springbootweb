package com.example.springboot.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask1 {
    private int count=0;

    @Scheduled(cron="* * /5 * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }
}
