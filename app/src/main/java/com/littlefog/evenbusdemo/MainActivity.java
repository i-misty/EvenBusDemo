package com.littlefog.evenbusdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * evenbus 基础实践
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
    }

    //测试提交
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)
                // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        EventBus.getDefault().register(this);
        Logger.d("onCreate");
        MessageEvent messageEvent = new MessageEvent("test message");
        //EventBus.getDefault().post(messageEvent);
        /*普通的消息传播只有先注册然后再发送才能收到的
        就算发送的是粘性消息同时订阅了普通消息（同一个事件）,普通消息的订阅者也是可以收到的，
         普通消息和粘性消息的区别只在于是否为未注册的组件保留最新的消息
         另外,消息是可以取消的，通常在高优先级的线程中取消，或者在运行中的线程中（也就是传递的过程中）
         */
        /*
        * 注意：1.当前注册的组件一定要实现订阅方法但是发布方不需要知道
        *       2.优先级不会影响注册订阅者所在线程*/

        EventBus.getDefault().postSticky(messageEvent);
        startActivity(new Intent(this,SecondActivity.class));

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    Toast toast;
    @Subscribe(threadMode = ThreadMode.MAIN ,priority = 10,sticky = false)
    public void onMessage(MessageEvent event){
       if(toast == null){
           toast = Toast.makeText(this,event.message+"0"+"\r\n"+Thread.currentThread().getId(),Toast.LENGTH_LONG);
       }else{
           toast.setText(event.message);
           toast.setDuration(Toast.LENGTH_LONG);
       }
       Logger.d(0+""+Thread.currentThread().getName());
       toast.show();
        EventBus.getDefault().cancelEventDelivery(event);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

/*    @Subscribe(threadMode = ThreadMode.POSTING ,priority = 2,sticky = false)
    public void onMessage1(MessageEvent event){


        Logger.d(1+""+Thread.currentThread().getName());

    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND ,priority = 3,sticky = false)
    public void onMessage2(MessageEvent event){

        Logger.d(2+""+Thread.currentThread().getName());

    }
    @Subscribe(threadMode = ThreadMode.ASYNC ,priority = 4,sticky = false)
    public void onMessage3(MessageEvent event){

        Logger.d(3+""+Thread.currentThread().getName());

    }*/
}
