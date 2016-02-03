package com.xipsoft.currency.app.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;
import com.xipsoft.currency.app.Constants;
import com.xipsoft.currency.app.MainActivity;
import com.xipsoft.currency.app.R;

import java.util.List;

/**
 * Created by yinka on 2/3/16.
 */
public class NotificationUtils {
    public static void showNotificationMessage(Context context,String title,String message){
        if(TextUtils.isEmpty(message)){
            return;
        }
        if(isAppInBackground(context)){
            int icon = R.mipmap.ic_launcher;
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Notification notification = builder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentText(title)
                    .setStyle(inboxStyle)
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                    .setContentText(message)
                    .build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(Constants.NOTIFICATION_ID,notification);
        }else{
            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isAppInBackground(Context context) {
        boolean ret = true;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            List<ActivityManager.RunningAppProcessInfo> runningProcess = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : runningProcess) {
                if(info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                    for(String activeProcess : info.pkgList){
                        if(activeProcess.equals(context.getPackageName())){
                            ret = false;
                        }
                    }
                }
            }
        }else{
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            ComponentName componentName = runningTasks.get(0).topActivity;
            if(componentName.getPackageName().equals(context.getPackageName())){
                ret = false;
            }

        }
        return ret;
    }
}
