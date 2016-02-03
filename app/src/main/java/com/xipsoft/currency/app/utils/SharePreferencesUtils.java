package com.xipsoft.currency.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import com.xipsoft.currency.app.Constants;
import com.xipsoft.currency.app.value_objects.Currency;

/**
 * Created by yinka on 2/3/16.
 */
public class SharePreferencesUtils {
    public static final String TAG = SharePreferencesUtils.class.getName();

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.CURRENCY_PREFERENCES,Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.edit();
    }

    public static int get(Context context ,String name, int def){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(name,def);
    }

    public static void update(Context context, String name,int value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(name,value);
        editor.commit();
    }

    public static String get(Context context ,String name, String def){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(name,def);
    }

    public static void update(Context context, String name,String value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(name,value);
        editor.commit();
    }
    public static String getCurrency(Context context, boolean isBase){
        return get(context, isBase?Constants.BASE_CURRENCY:Constants.TARGET_CURRENCY,isBase?"USD":"AUD");
    }

    public static void updateCurrency(Context context, String currency, boolean isBaseCurrency){
        update(context, isBaseCurrency?Constants.BASE_CURRENCY:Constants.TARGET_CURRENCY,currency);
    }

    public static int getServiceRepetition(Context context) {
        return get(context, Constants.SERVICE_REPETITION, 0);
    }

    public static void updateServiceRepetition(Context context, int value){
        update(context, Constants.SERVICE_REPETITION,value);
    }


    public static int getNumDownLoads(Context context){
        return get(context, Constants.NUM_DOWNLOADS,0);
    }

    public static void updateNumDownLoads(Context context, int value){
        update(context, Constants.NUM_DOWNLOADS,value);
    }




}
