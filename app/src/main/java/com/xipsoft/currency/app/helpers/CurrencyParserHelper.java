package com.xipsoft.currency.app.helpers;

import com.xipsoft.currency.app.Constants;
import com.xipsoft.currency.app.value_objects.Currency;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yinka on 2/3/16.
 */
public class CurrencyParserHelper {

    public static Currency parseCurrency(JSONObject jsonObject, String currencyName){
        Currency currency = new Currency();
        currency.setBase(jsonObject.optString(Constants.BASE));
        currency.setDate(jsonObject.optString(Constants.DATE));
        JSONObject rateObject = jsonObject.optJSONObject(Constants.RATES);
        if(rateObject != null) {
            currency.setRate(rateObject.optDouble(currencyName));

        }
        currency.setName(currencyName);

        return currency;
    }
}
