package com.example.martijn.myapplication.threads;

/**
 * Created by Martijn on 17-11-16.
 * Deze thread past de inhoud van een cocktail aan, heeft daarvoor een id nodig, en alle dranken + hoeveelheden. Retourneert een response als het gelukt is.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.martijn.myapplication.src.MyClient;
import com.example.martijn.myapplication.src.OnTaskCompleted;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import API.api.*;



public class editContentsOfCocktailThread extends AsyncTask<String, Void, Response> {
    private OnTaskCompleted<Response> listener;

    public editContentsOfCocktailThread(OnTaskCompleted<Response> listener)
    {
        this.listener = listener;
    }
    @Override
    protected Response doInBackground(String...params) {
        int id = Integer.parseInt(params[0]);
        String cocktailName = params[1];
        String drinkOne = params[2];
        int quantityOne = Integer.parseInt(params[3]);
        String drinkTwo = params[4];
        int quantityTwo = Integer.parseInt(params[5]);
        String drinkThree = params[6];
        int quantityThree = Integer.parseInt(params[7]);

        try {
            MyClient client = new MyClient();
            Response response = client.editContentsOfCocktail(id, cocktailName, drinkOne, quantityOne, drinkTwo, quantityTwo, drinkThree, quantityThree);
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
