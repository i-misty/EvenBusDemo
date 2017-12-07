package com.littlefog.evenbusdemo;

import android.app.Application;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by user10 on 2017/10/16.
 */

public class InitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
      //初始化日志
        //配置evenbus
        EventBus eventBus = EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false).
                throwSubscriberException(true)
                .build();

        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();



    }
}
