package com.example.martijn.myapplication.threads;

import android.os.AsyncTask;
import android.util.Log;

import com.example.martijn.myapplication.src.MyClient;
import com.example.martijn.myapplication.src.OnTaskCompleted;

import API.api.MixDrankResponse;
import API.api.PossibleMixesResponse;

/**
 * Created by Quinten on 19-12-2016.
 * Deze thread retourneert alle drank identifiers die gemaakt kunnen worden.
 */

public class getPossibleMixesThread extends AsyncTask<String, Void, PossibleMixesResponse> {
        private OnTaskCompleted<PossibleMixesResponse> listener;

        public getPossibleMixesThread(OnTaskCompleted<PossibleMixesResponse> listener)
        {
            this.listener = listener;
        }

        @Override
        protected PossibleMixesResponse doInBackground(String...params) {
            try {
                MyClient client = new MyClient();
                PossibleMixesResponse response = client.getPossibleMixes();
                return response;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(PossibleMixesResponse response)
        {
            listener.onTaskCompleted(response);
        }
    }
