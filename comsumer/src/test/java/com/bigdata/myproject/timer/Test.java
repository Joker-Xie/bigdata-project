package com.bigdata.myproject.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test {

    @org.junit.Test
    public void timerTest(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
            }
        },2000,1000);
        try {
            while (true) {
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
