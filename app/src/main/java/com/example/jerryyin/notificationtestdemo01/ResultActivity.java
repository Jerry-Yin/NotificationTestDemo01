package com.example.jerryyin.notificationtestdemo01;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by JerryYin on 8/4/15.
 */
public class ResultActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.result_activity_layout);

        DismissNotifiction();
    }

    //点击通知之后消除
    public void DismissNotifiction(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.notifyID);
    }

}
