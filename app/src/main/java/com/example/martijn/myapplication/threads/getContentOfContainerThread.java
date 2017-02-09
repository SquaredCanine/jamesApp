package com.example.martijn.myapplication.threads;

/**
 * Created by Quinten on 16-11-2016.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.martijn.myapplication.src.MyClient;
import com.example.martijn.myapplication.src.OnTaskCompleted;

import API.api.*;

/**
 * Created by Quinten on 16-11-2016.
 * Deze thread retourneert de inhoud van een container, en heeft daarvoor de identifier van de container nodig.
 */

public class getContentOfContainerThread extends AsyncTask<String, Void, SlotResponse> {
    private OnTaskCompleted<SlotResponse> listener;

    public getContentOfContainerThread(OnTaskCompleted<SlotResponse> listener)
    {
        this.listener = listener;
    }
    @Override
    protected SlotResponse doInBackground(String...params) {
        int identifier = Integer.parseInt(params[0]);
        try {
            MyClient client = new MyClient();
            SlotResponse response = client.getContentOfContainer(identifier);
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(SlotResponse response)
    {
        listener.onTaskCompleted(response);
    }
}
