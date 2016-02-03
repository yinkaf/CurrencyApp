package com.xipsoft.currency.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.xipsoft.currency.app.receivers.CurrencyReceiver;
import com.xipsoft.currency.app.services.CurrencyService;
import com.xipsoft.currency.app.utils.LogUtils;
import com.xipsoft.currency.app.value_objects.Currency;

public class MainActivity extends AppCompatActivity implements CurrencyReceiver.Receiver{

    private String baseCurrency = Constants.CURRENCY_CODES[30];
    private String targetCurrency = Constants.CURRENCY_CODES[0];

    public static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiveCurrencyExchangeRate();
    }

    private void receiveCurrencyExchangeRate() {
        CurrencyReceiver currencyReceiver = new CurrencyReceiver(new Handler());
        currencyReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC,null,getApplicationContext(), CurrencyService.class);
        intent.setExtrasClassLoader(CurrencyService.class.getClassLoader());

        Bundle bundle = new Bundle();
        String url = Constants.CURRENCY_URL + baseCurrency;
        bundle.putString(Constants.URL,url);
        bundle.putParcelable(Constants.RECEIVER,currencyReceiver);
        bundle.putInt(Constants.REQUEST_ID,Constants.REQUEST_ID_NUM);
        bundle.putString(Constants.CURRENCY_BASE,baseCurrency);
        bundle.putString(Constants.CURRENCY_NAME,targetCurrency);

        intent.putExtra(Constants.BUNDLE,bundle);
        startService(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecieveResult(int resultCode, final Bundle resultData) {
        switch (resultCode){
            case Constants.STATUS_RUNNING:
                LogUtils.log(TAG,"Service Running..");
                break;
            case Constants.STATUS_FINISHED:
                LogUtils.log(TAG,"Service Finished..");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Currency currency = resultData.getParcelable(Constants.RESULT);
                        if(currency != null){
                            String message = String.format("Currency: %s - %s : %f",currency.getBase(),currency.getName(),currency.getRate());
                            LogUtils.log(TAG,message);
                        }
                    }
                });
                break;
            case Constants.STATUS_ERROR:
                String error = resultData.getString(Intent.EXTRA_TEXT);
                LogUtils.log(TAG, error);
                Toast.makeText(this,error,Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
