package com.xipsoft.currency.app.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.xipsoft.currency.app.Constants;
import com.xipsoft.currency.app.helpers.CurrencyParserHelper;
import com.xipsoft.currency.app.receivers.CurrencyReceiver;
import com.xipsoft.currency.app.utils.LogUtils;
import com.xipsoft.currency.app.utils.WebServiceUtils;
import com.xipsoft.currency.app.value_objects.Currency;
import org.json.JSONObject;

/**
 * Created by yinka on 2/3/16.
 */
public class CurrencyService extends IntentService {
    public static final String TAG = CurrencyService.class.getName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CurrencyService(String name) {
        super(TAG);
    }

    public CurrencyService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtils.log(TAG,"Currency Service has started");
        Bundle intentBundle = intent.getBundleExtra(Constants.BUNDLE);
        final ResultReceiver resultReceiver = intentBundle.getParcelable(Constants.RECEIVER);
        Parcel parcel = Parcel.obtain();
        resultReceiver.writeToParcel(parcel,0);
        parcel.setDataPosition(0);
        ResultReceiver resultReceiverForSending =  ResultReceiver.CREATOR.createFromParcel(parcel);
        parcel.recycle();

        String url = intentBundle.getString(Constants.URL);
        String currencyName = intentBundle.getString(Constants.CURRENCY_NAME);

        Bundle bundle =  new Bundle();
        if(url != null && !TextUtils.isEmpty(url)){
            resultReceiverForSending.send(Constants.STATUS_RUNNING, Bundle.EMPTY);
            if(WebServiceUtils.hasInternetConnection(getApplicationContext())){
                try {
                    JSONObject obj = WebServiceUtils.requestJSONObject(url);
                    if(obj != null){
                        Currency currency = CurrencyParserHelper.parseCurrency(obj, currencyName);
                        bundle.putParcelable(Constants.RESULT,currency);
                        resultReceiverForSending.send(Constants.STATUS_FINISHED,bundle);
                    }
                } catch (Exception e) {
                    bundle.putString(Intent.EXTRA_TEXT,e.getMessage());
                    resultReceiverForSending.send(Constants.STATUS_ERROR,bundle);
                }
            }else{
                LogUtils.log(TAG,"No Internet Connection.");
            }

        }
        LogUtils.log(TAG,"Currency Service Stopped!");
        stopSelf();

    }
}
