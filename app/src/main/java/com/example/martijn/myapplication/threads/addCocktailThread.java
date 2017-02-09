package com.example.martijn.myapplication.threads;

/**
 * Created by Quinten on 16-11-2016.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.martijn.myapplication.src.MyClient;
import com.example.martijn.myapplication.src.OnTaskCompleted;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import API.api.*;

/**
 * Created by Quinten on 16-11-2016.
 * Deze thread maakt een nieuw cocktail aan. Als je alle namen en hoeveelheden meegeeft. En retourneert een response of het gelukt is.
 */

public class addCocktailThread extends AsyncTask<String, Void, Response> {
    private OnTaskCompleted<Response> listener;

    public addCocktailThread(OnTaskCompleted<Response> listener)
    {
        this.listener = listener;
    }
    @Override
    protected Response doInBackground(String...params) {
        String cocktailName = params[0];
        String drinkOne = params[1];
        int quantityOne = Integer.parseInt(params[2]);
        String drinkTwo = params[3];
        int quantityTwo = Integer.parseInt(params[4]);
        String drinkThree = params[5];
        int quantityThree = Integer.parseInt(params[6]);

        try {
            MyClient client = new MyClient();
            Response response = client.addCocktail(cocktailName, drinkOne, quantityOne, drinkTwo, quantityTwo, drinkThree, quantityThree);
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
