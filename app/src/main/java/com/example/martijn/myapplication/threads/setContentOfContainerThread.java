package com.example.martijn.myapplication.threads;

/**
 * Created by Martijn on 17-11-16.
 * Deze thread past een container met identifier id aan met de nieuwe drank + hoeveelheid, en retourneert een response als het gelukt is.
 */


import android.os.AsyncTask;
import android.util.Log;

import com.example.martijn.myapplication.src.MyClient;
import com.example.martijn.myapplication.src.OnTaskCompleted;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import API.api.*;


public class setContentOfContainerThread extends AsyncTask<String, Void, Response> {
    private OnTaskCompleted<Response> listener;

    public setContentOfContainerThread(OnTaskCompleted<Response> listener)
    {
        this.listener = listener;
    }
    @Override
    protected Response doInBackground(String...params) {
        int id = Integer.parseInt(params[0]);
        String drinkName = params[1];
        int quantityInMl = Integer.parseInt(params[2]);

        try {
            MyClient client = new MyClient();
            Response response = client.setContentOfContainer(id, drinkName, quantityInMl);
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Response response)
    {
        listener.onTaskCompleted(response);
    }
}
