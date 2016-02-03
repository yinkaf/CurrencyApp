package com.xipsoft.currency.app.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xipsoft.currency.app.Constants;
import com.xipsoft.currency.app.utils.LogUtils;

/**
 * Created by yinka on 2/3/16.
 */
public class CurrencyDatabaseAdapter extends SQLiteOpenHelper {

    private static final String TAG = CurrencyDatabaseAdapter.class.getName();
    public static final int DATABASE_VERSION=1;
    public static final String CURRENCY_TABLE_CREATE="create table "+Constants.CURRENCY_TABLE+" ("+
                                                    Constants.KEY_ID+" integer primary key autoincrement, "+
                                                    Constants.KEY_BASE+" text not null, "+
                                                    Constants.KEY_NAME+" text not null, "+
                                                    Constants.KEY_RATE+" real, "+
                                                    Constants.KEY_DATE+" date)";

    public CurrencyDatabaseAdapter(Context context) {
        super(context, Constants.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CURRENCY_TABLE_CREATE);
            LogUtils.log(TAG,"Currency table created");
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.log(TAG,"Currency table creation error :"+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        clearCurrencyTable(db);
        onCreate(db);
    }

    private void clearCurrencyTable(SQLiteDatabase db) {
        try {
            db.execSQL("drop table if exists "+Constants.CURRENCY_TABLE);
            LogUtils.log(TAG,"Currency table dropped");
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.log(TAG,"Failed to drop currency table : "+e.getMessage());
        }
    }
}
