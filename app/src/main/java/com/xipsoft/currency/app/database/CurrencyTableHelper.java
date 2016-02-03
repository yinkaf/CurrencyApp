package com.xipsoft.currency.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import com.xipsoft.currency.app.Constants;
import com.xipsoft.currency.app.utils.LogUtils;
import com.xipsoft.currency.app.value_objects.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinka on 2/3/16.
 */
public class CurrencyTableHelper {
    public static final String TAG = CurrencyTableHelper.class.getName();

    private CurrencyDatabaseAdapter adapter;

    public CurrencyTableHelper(CurrencyDatabaseAdapter adapter) {
        this.adapter = adapter;
    }


    public long insertCurrency(Currency currency) {
        List<Currency> currencies = getCurrencyHistory(currency.getBase(), currency.getName(), currency.getDate());
        if(currencies != null && currencies.size() == 0){
            LogUtils.log(TAG,"No records found in DB, inserting data...");
            ContentValues initialValues = new ContentValues();
            initialValues.put(Constants.KEY_BASE,currency.getBase());
            initialValues.put(Constants.KEY_NAME,currency.getName());
            initialValues.put(Constants.KEY_RATE,currency.getRate());
            initialValues.put(Constants.KEY_DATE,currency.getDate());
            long id = adapter.getWritableDatabase().insert(Constants.CURRENCY_TABLE,null,initialValues);
            adapter.getWritableDatabase().close();
            return id;
        }else{
            LogUtils.log(TAG,"Records found in DB.");
        }
        return currencies.get(0).getId();
    }

    private List<Currency> getCurrencyHistory(String base, String name, String date) {
        List<Currency> currencies = new ArrayList<>();
        Cursor cursor = adapter.getWritableDatabase().query(
                Constants.CURRENCY_TABLE, new String[]{Constants.KEY_ID, Constants.KEY_BASE, Constants.KEY_NAME, Constants.KEY_RATE, Constants.KEY_DATE},
                Constants.KEY_BASE + " = '" + base + "' AND " + Constants.KEY_NAME + " = '" + name + "' AND " + Constants.KEY_DATE + "  = '" + date + "'",
                null, null, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                currencies.add(parseCurrency(cursor));
            }
            while ((cursor.moveToNext())){
                currencies.add(parseCurrency(cursor));
            }
        }


        return currencies;
    }

    private Currency parseCurrency(Cursor cursor) {
        Currency currency = new Currency();
        currency.setId(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID)));
        currency.setBase(cursor.getString(cursor.getColumnIndex(Constants.KEY_BASE)));
        currency.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME)));
        currency.setRate(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_RATE)));
        currency.setDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE)));
        return currency;
    }


    public void clearCurrencyTable(){
        adapter.getWritableDatabase().delete(Constants.CURRENCY_TABLE,null,null);
    }


    public List<Currency> getCurrencyHistory(String base, String name) {
        List<Currency> currencies = new ArrayList<>();
        Cursor cursor = adapter.getWritableDatabase().query(
                Constants.CURRENCY_TABLE, new String[]{Constants.KEY_ID, Constants.KEY_BASE, Constants.KEY_NAME, Constants.KEY_RATE, Constants.KEY_DATE},
                Constants.KEY_BASE + " = '" + base + "' AND " + Constants.KEY_NAME + " = '" + name + "'" ,
                null, null, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                currencies.add(parseCurrency(cursor));
            }
            while ((cursor.moveToNext())){
                currencies.add(parseCurrency(cursor));
            }
        }


        return currencies;
    }

    public Currency getCurrency(long id) throws SQLException{
        Currency currency = null;
        Cursor cursor = adapter.getWritableDatabase().query(Constants.CURRENCY_TABLE,new String[]{Constants.KEY_ID, Constants.KEY_BASE,
                Constants.KEY_NAME, Constants.KEY_RATE, Constants.KEY_DATE},
                Constants.KEY_ID + " = " + id  ,
                null, null, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                currency = parseCurrency(cursor);
            }
        }
        return currency;
    }
}
