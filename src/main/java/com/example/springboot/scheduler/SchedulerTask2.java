package com.example.springboot.scheduler;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerTask2 {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  //  @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }
}
