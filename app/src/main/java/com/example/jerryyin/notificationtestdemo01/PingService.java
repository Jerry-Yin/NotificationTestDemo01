package com.example.jerryyin.notificationtestdemo01;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by JerryYin on 8/4/15.
 */

class PingService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PingService(String name) {
        super(name);
    }


    /**指定了当用户点击notification时启动一个新的activity*/
    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
