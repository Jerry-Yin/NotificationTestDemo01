package com.example.jerryyin.notificationtestdemo01;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "MainActivity";

    private static final String ACTION_DISMISS = "DISMISS";
    private static final String ACTION_SNOOZE = "SNOOZE";
    private String msg = "闹钟时间到啦，懒虫起床啦！";
    private final static int notifyID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setupNotifiction();                   //普通通知

//        setupBigViewNotifiction();              //大视图通知

//        setupDeterminateProgressNotifiction();  //固定进度条通知

        setupInDtProgressNotifiction();         //不固定的(持续的)进度条通知
    }

    /** normal notification */
    public void setupNotifiction() {
        /** 1.set up the notification */
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)  //为使它向下兼容低版本，采用这个v4里面的类NotificationCompat.Builder是个不错的选择
                .setSmallIcon(R.drawable.db1)
                .setContentTitle("Title of the notification")
                .setContentText("content text of the notification");

        /** 2. Action of the notification */
        Intent resultIntent = new Intent(this, ResultActivity.class);
                // Because clicking the notification opens a new ("special") activity, there's
                // no need to create an artificial back stack.
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //设置Activity在一个新的空的任务中启动
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /** 3. when click the notification */
        mBuilder.setContentIntent(resultPendingIntent);

        /** 4.publish the notification  */
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notifyID, mBuilder.build());


        //更新notification, 不用每次产生新的，秩序重复利用第一个即可，使用同一个 id；
//        int numMessages = 0;
//        for (int i =0; i < 5; i++){
//            mBuilder.setContentText("0").setNumber(++numMessages);
//            mNotifyMgr.notify(001, mBuilder.build());
//        }


    }

    /** big view notification (with button)*/
    public void setupBigViewNotifiction(){

        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra("EXTRA_MESSAGE", msg);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resuPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        /** Sets up the Snooze and Dismiss action Buttons that will appear in the  big view of the notification.*/
        Intent dismissIntent = new Intent(this, PingService.class);
        dismissIntent.setAction(ACTION_DISMISS);
        PendingIntent piDismiss = PendingIntent.getActivity(this, 0, dismissIntent, 0);

        Intent snoozIntent = new Intent(this, PingService.class);
        snoozIntent.setAction(ACTION_SNOOZE);
        PendingIntent piSnooze = PendingIntent.getActivity(this, 0, snoozIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)  //为使它向下兼容低版本，采用这个v4里面的类NotificationCompat.Builder是个不错的选择
                .setSmallIcon(R.drawable.db1)
                .setContentTitle("Title of the big view notification")
                .setContentText("content text of the big view notification")
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .addAction(R.drawable.ic_stat_dismiss, "Dismiss", piDismiss)
                .addAction(R.drawable.ic_stat_snnooze, "Snooze", piSnooze);

        mBuilder.setContentIntent(resuPendingIntent);

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notifyID, mBuilder.build());
    }


    public void setupDeterminateProgressNotifiction(){
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)  //为使它向下兼容低版本，采用这个v4里面的类NotificationCompat.Builder是个不错的选择
                .setSmallIcon(R.drawable.db1)
                .setContentTitle("Downloding...")
                .setContentText("content text of the notification");

        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resuPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resuPendingIntent);

        final NotificationManager mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Start a lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr = 0;
                for (incr = 0; incr < 100; incr += 5){      //固定长度，设置20次完成
                        // Sets the progress indicator to a max value,
                        // the current completion percentage, and "determinate" state
                    mBuilder.setProgress(100, incr, false);     //参数：（ 最大进度， 当前进度， false确定进度／true不定进度）
                        // Displays the progress bar for the first time.
                    mNotifyMgr.notify(notifyID, mBuilder.build());
                        // Sleeps the thread, simulating an operation
                    try {
                            // Sleep for 5 seconds
                        Thread.sleep(2*1000);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "sleep failure");
                    }
                }
                // When the loop is finished, updates the notification
                mBuilder.setContentText("Download complete")
                        // Removes the progress bar
                        .setProgress(0,0,false);
                mNotifyMgr.notify(notifyID, mBuilder.build());

            }
        }).start();
    }


    public void setupInDtProgressNotifiction(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)  //为使它向下兼容低版本，采用这个v4里面的类NotificationCompat.Builder是个不错的选择
                .setSmallIcon(R.drawable.db1)
                .setContentTitle("Downloding...")
                .setContentText("content text of the notification");

        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resuPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resuPendingIntent);

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setProgress(0, 0, true);
        mNotifyMgr.notify(notifyID, mBuilder.build());
    }

}
