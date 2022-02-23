package com.company;

import org.json.JSONException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static final HttpClient httpClient = HttpClient.newBuilder().
            version(HttpClient.Version.HTTP_2).
            connectTimeout((Duration.ofSeconds(10))).
            build();

    public static void main(String[] args) throws JSONException {
        ArrayList<Transactions> transactionLists = new ArrayList<Transactions>();
        int status;
        String statusBody;
        int pageNum = 0;
        do {
            pageNum += 1;
            HttpRequest request = HttpRequest.newBuilder()
                    .GET().
                    uri(URI.create("https://resttest.bench.co/transactions/" + pageNum + ".json"))
                    .build();

            HttpResponse<String> responseContent = null;
            try {
                responseContent = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            status = responseContent.statusCode();
            statusBody = responseContent.body();

            if (status == 200) {

                ArrayList<Transactions> transactionList;
                transactionList = Parse.parse(statusBody);
                for (Transactions element : transactionList) {
                    transactionLists.add(element);
                }
            }

        } while (!(status > 299));
        

        Map<LocalDate, Double> dailyTransactions = transactionLists.stream().
                collect(Collectors.
                        groupingBy(Transactions::getTransactionDate,
                                Collectors.mapping(Transactions::getAmount,
                                        Collectors.summingDouble(e -> e))));

        List<LocalDate> dates = new ArrayList<>(dailyTransactions.keySet());
        Collections.sort(dates);

        double finalAmount = 0.0;
        for (LocalDate key : dates) {
            finalAmount += dailyTransactions.get(key);
            System.out.println(key + " " + String.format("%.2f", finalAmount));
        }


    }



}




