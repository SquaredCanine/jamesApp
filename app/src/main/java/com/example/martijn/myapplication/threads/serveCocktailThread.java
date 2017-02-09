package com.example.martijn.myapplication.threads;

/**
 * Created by Martijn on 17-11-16.
 * Deze thread serveert het drankje met identifier id, en retourneert een response als het gelukt is.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.martijn.myapplication.src.MyClient;
import com.example.martijn.myapplication.src.OnTaskCompleted;

import API.api.*;

public class serveCocktailThread extends AsyncTask<String, Void, Response> {

private OnTaskCompleted<Response> listener;

    public serveCocktailThread(OnTaskCompleted<Response> listener) {
        this.listener = listener;
    }

    @Override
    protected Response doInBackground(String...params) {
        int identifier = Integer.parseInt(params[0]);
        try {
            MyClient client = new MyClient();
            Response response = client.serveCocktail(identifier);
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
        }

    @Override
    protected void onPostExecute(Response response) {
        listener.onTaskCompleted(response);
    }

}
