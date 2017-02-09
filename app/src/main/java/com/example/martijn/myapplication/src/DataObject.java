package com.example.martijn.myapplication.src;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.martijn.myapplication.threads.getCocktailThread;
import com.example.martijn.myapplication.threads.getPossibleMixesThread;

import API.api.MixDrankResponse;
import API.api.PossibleMixesResponse;

/**
 * Created by Martijn on 14-12-16.
 * In dit object word de naam en de identifier van het drankje opgeslagen. Zodat er verder in het programma mee gewerkt kan worden
 * Elk mogelijke drankje heeft zijn eigen dataobject
 */

public class DataObject {
    private String identifier;
    private String mixName;



    DataObject (String identifier){
        this.identifier = identifier;
        if(identifier.equals("+"))
        {
            mixName = "add";
        }
    }



    public void getRandom()
    {
        OnTaskCompleted<PossibleMixesResponse> listener = new OnTaskCompleted<PossibleMixesResponse>() {
            @Override
            public void onTaskCompleted(PossibleMixesResponse response) {
                        setIdentifier(Integer.toString(response.getPossibleMixes().length/2));
                    }
                };
        getPossibleMixesThread request = new getPossibleMixesThread(listener);
        request.execute();
    }
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMixName() {
        return mixName;
    }

    public void setMixName(String mixName) {
        this.mixName = mixName;
    }
}