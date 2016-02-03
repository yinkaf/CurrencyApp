package com.xipsoft.currency.app.receivers;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by yinka on 2/3/16.
 */
public class CurrencyReceiver extends ResultReceiver {


    private Receiver receiver;
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public CurrencyReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public interface Receiver {
        void onRecieveResult(int resultCode, Bundle resultData);
    }


    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null){
            receiver.onRecieveResult(resultCode,resultData);
        }
    }
}
