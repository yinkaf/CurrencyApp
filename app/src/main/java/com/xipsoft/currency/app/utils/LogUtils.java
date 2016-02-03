package com.xipsoft.currency.app.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yinka on 2/3/16.
 */
public class LogUtils {

    private static StringBuffer sStringBuffer = new StringBuffer();


    public interface LogListener {
        void onLogged(StringBuffer log);
    }

    private static LogListener sLogListener;

    public static void log(String tag, String message) {
        Log.d(tag,message);
        StringBuilder builder = new StringBuilder();
        String date = formatDate(Calendar.getInstance());
        builder.append(date);
        builder.append(" ");
        builder.append(tag);
        builder.append(" ");
        builder.append(message);
        builder.append("\n\n");
        sStringBuffer.insert(0,builder.toString());
        printLogs();
    }

    private static void printLogs() {
        if(sLogListener != null){
            sLogListener.onLogged(sStringBuffer);
        }
    }

    private static String formatDate(Calendar instance) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.format(instance.getTime());
    }

    public static void clearLogs(){
        sStringBuffer = new StringBuffer();
        printLogs();
    }

    public static void setsLogListener(LogListener sLogListener) {
        LogUtils.sLogListener = sLogListener;
    }
}