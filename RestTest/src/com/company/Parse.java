package com.company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Parse {

    /**
     * Parse response body to get Date and Amount
     *
     * @param responseBody the JSON body
     * @return list of date and amount in all pages
     * @throws JSONException
     */
    public static ArrayList<Transactions> parse(String responseBody) throws JSONException {

        JSONObject fullData = new JSONObject(responseBody);
        // JSONObject transactions = fullData.getJSONObject("transactions");
        // Gson gson = new Gson();

        JSONArray arr = fullData.getJSONArray("transactions");
        ArrayList<Transactions> listData = new ArrayList<Transactions>();
        for (int i = arr.length() - 1; i >= 0; i--) {
            JSONObject transaction = arr.getJSONObject(i);
            LocalDate date = LocalDate.parse(transaction.getString("Date"));
            Double amount = Double.valueOf(transaction.getString("Amount"));
            Transactions transactionsAmounts = new Transactions(date, amount);
            listData.add(transactionsAmounts);
        }

        return listData;
    }
}
