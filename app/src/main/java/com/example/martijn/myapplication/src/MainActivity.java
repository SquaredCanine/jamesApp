package com.example.martijn.myapplication.src;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import API.api.*;


import com.example.martijn.myapplication.R;
import com.example.martijn.myapplication.threads.getCocktailThread;
import com.example.martijn.myapplication.threads.getPossibleMixesThread;
import com.example.martijn.myapplication.threads.serveCocktailThread;

import java.util.ArrayList;
import java.util.Random;
/*
Deze activiteit is het eerste wat je ziet, vanaf hier kan je drankjes bestellen/toevoegen. En een random drankje bestellen of de containers aanpassen.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "MainActivity";
    private ArrayList<DataObject> mDataset;
    private boolean enabled = false;
    private int[] possibleDrinks;
    private int chosenDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        OnTaskCompleted<PossibleMixesResponse> listener = new OnTaskCompleted<PossibleMixesResponse>() {
            @Override
            public void onTaskCompleted(PossibleMixesResponse response) {
                mDataset = getDataSet(response.getPossibleMixes());
                mAdapter = new MyRecyclerViewAdapter(mDataset);
                mRecyclerView.setAdapter(mAdapter);
                ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                        .MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        if (mDataset.get(position).getIdentifier().equals("+")) {
                            Intent addCocktailActivity = new Intent(MainActivity.this, Edit_Add_Activity.class);
                            startActivity(addCocktailActivity);
                        }else{
                            Intent openCocktailActivity = new Intent(MainActivity.this, CocktailInfo_Activity.class);
                            openCocktailActivity.putExtra("id", Integer.parseInt(mDataset.get(position).getIdentifier()));
                            startActivity(openCocktailActivity);
                            Log.i(LOG_TAG, " Clicked on Item " + position);
                        }
                    }
                });

                enabled = true;
            }
        };
        getPossibleMixesThread request = new getPossibleMixesThread(listener);
        request.execute();
    }

//Kijkt of de data aanwezig is, en update de data.
    @Override
    protected void onResume() {
        super.onResume();
        OnTaskCompleted<PossibleMixesResponse> listener = new OnTaskCompleted<PossibleMixesResponse>() {
            @Override
            public void onTaskCompleted(PossibleMixesResponse response) {
                possibleDrinks = response.getPossibleMixes();
                mDataset = getDataSet(response.getPossibleMixes());
                mAdapter = new MyRecyclerViewAdapter(mDataset);
                mRecyclerView.setAdapter(mAdapter);
                ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                        .MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        if (mDataset.get(position).getIdentifier().equals("+")) {
                            Intent addCocktailActivity = new Intent(MainActivity.this, Edit_Add_Activity.class);
                            startActivity(addCocktailActivity);
                        } else {
                            Intent openCocktailActivity = new Intent(MainActivity.this, CocktailInfo_Activity.class);
                            openCocktailActivity.putExtra("id", Integer.parseInt(mDataset.get(position).getIdentifier()));
                            startActivity(openCocktailActivity);
                            Log.i(LOG_TAG, " Clicked on Item " + position);
                        }
                    }
                });
                Update();
                enabled = true;
            }
        };
        getPossibleMixesThread request = new getPossibleMixesThread(listener);
        request.execute();
        if(enabled) {
            Update();
        }
    }
    //Updates the available drinks.
    protected void Update()
    {
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mDataset.get(position).getIdentifier().equals("+")) {
                    Intent addCocktailActivity = new Intent(MainActivity.this, Edit_Add_Activity.class);
                    startActivity(addCocktailActivity);
                } else {
                    Intent openCocktailActivity = new Intent(MainActivity.this, CocktailInfo_Activity.class);
                    openCocktailActivity.putExtra("id", Integer.parseInt(mDataset.get(position).getIdentifier()));
                    startActivity(openCocktailActivity);
                    Log.i(LOG_TAG, " Clicked on Item " + position);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
    //Optie menu aan de bovenkant van het scherm, vanuit hier kan je een random drankje bestellen of de containers aanpassen.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent openContainerSettingsActivity = new Intent(MainActivity.this, ContainerSettingsActivity.class);
            startActivity(openContainerSettingsActivity);
            return true;
        }
        if (id == R.id.random_settings){
            OnTaskCompleted<Response> listener = new OnTaskCompleted<Response>() {
                @Override
                public void onTaskCompleted(Response response) {
                    drinkServedDialog();
                }
            };
            Random random = new Random();
            int chosenDrink = random.nextInt(possibleDrinks.length);
            serveCocktailThread request = new serveCocktailThread(listener);
            request.execute(Integer.toString(possibleDrinks[chosenDrink]));
        }

        return super.onOptionsItemSelected(item);
    }

    //Deze functie stelt alle knopjes in.
    private ArrayList<DataObject> getDataSet(int[] data) {
        ArrayList results = new ArrayList<DataObject>();
        int test = 0;
        for (int index = 0; index < data.length; index++) {

            DataObject obj = new DataObject(Integer.toString(data[index]));
            results.add(index, obj);
            test = index;
        }
        DataObject add = new DataObject("+");
        DataObject random = new DataObject("-");
        results.add((test+1), add);
        results.add((test+2), random);
        return results;
    }
    //Geeft een popup waarin staat dat je drankje geserveerd is.
    private void drinkServedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setTitle("Enjoy!");
        builder.setMessage("Your drink is served.");
        builder.show();
    }
}