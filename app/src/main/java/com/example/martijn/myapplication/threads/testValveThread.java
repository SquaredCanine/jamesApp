package com.example.martijn.myapplication.threads;

import API.api.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import android.os.AsyncTask;
import android.util.Log;

import com.example.martijn.myapplication.src.MyClient;
import com.example.martijn.myapplication.src.OnTaskCompleted;

/**
 * Created by Quinten on 16-11-2016.
 * Deze thread test de valves, en retourneert een response als het gelukt is.
 */

public class testValveThread extends AsyncTask<Void, Void, Response> {

    private OnTaskCompleted<Response> listener;

    public testValveThread(OnTaskCompleted<Response> listener)
    {
        this.listener = listener;
    }

    @Override
        protected Response doInBackground(Void... params) {
            try {
                MyClient client = new MyClient();
                Response response = client.testValve();
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
