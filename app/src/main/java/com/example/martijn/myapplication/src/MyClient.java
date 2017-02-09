package com.example.martijn.myapplication.src;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.martijn.myapplication.R;

import API.api.*;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import API.api.Response;
import com.example.martijn.myapplication.threads.*;
/**
 * Created by Martijn on 14-11-16.
 * Dit is de verbinding tussen de app en de server, hier gaat al het dataverkeer langs.
 */

public class MyClient{
    //Doelwit, de server
    private String target = "http://192.168.0.103:8001";
    //Retourneert alle mogelijke drankjes
    public PossibleMixesResponse getPossibleMixes(){
        try {
            final String url = target + "/database/get/possiblemixes";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            PossibleMixesResponse response = restTemplate.getForObject(url, PossibleMixesResponse.class);
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }
    //Test de drankuitgifte
    public Response testValve(){
        try {
            final String url = target + "/test/valve";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Response response = restTemplate.getForObject(url, Response.class);
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }
    //Retourneert de inhoud van het drankje met identifier id
   public MixDrankResponse getCocktail(int id){
       try {
           final String url = target + "/database/get/mixdrank/" + id;
           RestTemplate restTemplate = new RestTemplate();
           restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
           MixDrankResponse response = restTemplate.getForObject(url, MixDrankResponse.class);
           return response;
       } catch (Exception e) {
           Log.e("MainActivity", e.getMessage(), e);
       }
       return null;
    }
    //Serveert het drankje met identifier id.
    public Response serveCocktail(int id){
            try {
                final String url = target + "/dispenser/drink/" + id;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Response response = restTemplate.getForObject(url, Response.class);
                return response;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
    }
    //Voegt een nieuw drankje toe aan de cocktaildatabase, en heeft daarvoor de namen en hoeveelheden nodig van het nieuwe drankje.
    public Response addCocktail(String cocktailName, String drinkOne, int quantityOne, String drinkTwo, int quantityTwo, String drinkThree, int quantityThree) {
        try {
            final String url = target + "/database/insert/mixdrank/" + cocktailName + "/" + drinkOne + "/" + quantityOne + "/" + drinkTwo + "/" + quantityTwo + "/" + drinkThree + "/" + quantityThree;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Response response = restTemplate.getForObject(url, Response.class);
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }
    //Verander een al bestaand drankje, met identifier id. En er worden alle namen en hoeveelheden aan meegegeven, die aangepast gaan worden.
    public Response editContentsOfCocktail(int id, String cocktailName, String drinkOne, int quantityOne, String drinkTwo, int quantityTwo, String drinkThree, int quantityThree) {
        try {

            final String url = target + "database/set/mixdrank/" + id + "/" + cocktailName + "/" + drinkOne + "/" + quantityOne + "/" + drinkTwo + "/" + quantityTwo + "/" + drinkThree + "/" + quantityThree;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Response response = restTemplate.getForObject(url, Response.class);
            return response;

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }
    //Retourneert de inhoud van container met identifier id.
   public SlotResponse getContentOfContainer (int id) {
        try {
            final String url = target + "/database/get/slot/" + id;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            SlotResponse response = restTemplate.getForObject(url, SlotResponse.class);
            System.out.println(response.getDrankNaam());
            System.out.println(response.getStatus());
            System.out.println(response.getMlDrank());
            System.out.println(response.getMessage());
            System.out.println(response.getSlotId());
            return response;



        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;

    }
    //Past de inhoud van de container aan met identifier id. En de namen + hoeveelheid(altijd 1500)
    public Response setContentOfContainer(int id, String drinkName, int quantityInMl) {
        try {

            final String url = target + "/database/set/slot/" + id + "/" + drinkName + "/" + quantityInMl;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Response response = restTemplate.getForObject(url, Response.class);
            return response;

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }
    //Verwijdert het drankje met identifier id.
    public Response deleteMixdrank(int id){
        try {
            final String url = target + "/database/delete/mixdrank/" + id;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Response response = restTemplate.getForObject(url, Response.class);
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }





}