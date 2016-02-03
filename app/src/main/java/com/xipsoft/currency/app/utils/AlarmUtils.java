package com.xipsoft.currency.app.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by yinka on 2/3/16.
 */
public class AlarmUtils {
    public static final String TAG = AlarmUtils.class.getName();
    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;

    public enum REPEAT{
        REPEAT_EVERY_MINUTE,
        REPEAT_EVERY_2_MINUTES,
        REPEAT_EVERY_5_MINUTES,
        REPEAT_EVERY_20_MINUTES,
        REPEAT_EVERY_HOUR,
        REPEAT_EVERY_DAY
    }
    private static final int[] REPEAT_TIME = {60,120,300,1200,3600,86400};

    public static void startService(Context context, Intent intent,REPEAT repeat){
        stopService();
        pendingIntent = PendingIntent.getService(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),REPEAT_TIME[repeat.ordinal()]*1000,pendingIntent);
        LogUtils.log(TAG,"Alarm has been started : "+repeat);
    }

    private static void stopService() {
        if(pendingIntent != null && alarmManager != null){
            alarmManager.cancel(pendingIntent);
            alarmManager = null;
            pendingIntent = null;
            LogUtils.log(TAG,"Alarm has been stopped.");
        }
    }
}
