package com.example.martijn.myapplication.threads;

/**
 * Created by Quinten on 16-11-2016.
 * Deze thread retourneert de inhoud van een cocktail, en heeft daarvoor een identifier nodig.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.martijn.myapplication.src.MyClient;
import com.example.martijn.myapplication.src.OnTaskCompleted;

import API.api.*;


public class getCocktailThread extends AsyncTask<String, Void, MixDrankResponse> {
    private OnTaskCompleted<MixDrankResponse> listener;

    public getCocktailThread(OnTaskCompleted<MixDrankResponse> listener)
    {
        this.listener = listener;
    }
    @Override
    protected MixDrankResponse doInBackground(String...params) {
        int identifier = Integer.parseInt(params[0]);
        try {
            MyClient client = new MyClient();
            MixDrankResponse response = client.getCocktail(identifier);
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(MixDrankResponse response)
    {
        listener.onTaskCompleted(response);
    }
}
