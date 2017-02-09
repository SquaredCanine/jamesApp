package com.example.martijn.myapplication.src;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.martijn.myapplication.R;

import API.api.*;
import com.example.martijn.myapplication.threads.*;
/*
Deze activity weergeeft de informatie van een cocktail. Als de activity word aangeroepen word er een identifier meegestuurd van het drankje
waar je de info van wilt.
Hij stuurt dan een request naar de server om de correcte gegevens te krijgen.
Je kan dan het drankje bestellen of verwijderen of aanpassen.
 */
public class CocktailInfo_Activity extends AppCompatActivity {

    TextView cocktailName;
    TextView drinkOne;
    TextView drinkTwo;
    TextView drinkThree;

    MixDrankResponse response;

    Button serveCocktailButton;
    Button editCocktailButton;
    Button removeCocktailButton;

    public void init(){
        setTitle("Cocktail Name");

        serveCocktailButton = (Button) findViewById(R.id.OrderButton);
        editCocktailButton = (Button) findViewById(R.id.EditCocktailButton);
        removeCocktailButton = (Button) findViewById(R.id.RemoveCocktailButton);
        //Dit knopje serveert de cocktail en geeft de identifier mee van het drankje
        serveCocktailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                OnTaskCompleted<Response> listener = new OnTaskCompleted<Response>() {
                    @Override
                    public void onTaskCompleted(Response response) {
                        drinkServedDialog();
                    }
                };
                serveCocktailThread request = new serveCocktailThread(listener);
                request.execute(Integer.toString(response.getMixDrankId()));
            }


        });
        //Dit knopje opent een nieuw scherm waar je de cocktail kan aanpassen, en stuurt ook de identifier mee van het drankje.
        editCocktailButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent openEdit_Add_Activity = new Intent(CocktailInfo_Activity.this, Edit_Add_Activity.class);
                openEdit_Add_Activity.putExtra("id", response.getMixDrankId());
                startActivity(openEdit_Add_Activity);
            }
        });
        //Dit knopje verwijdert het drankje
        removeCocktailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                OnTaskCompleted<Response> listener = new OnTaskCompleted<Response>() {
                    @Override
                    public void onTaskCompleted(Response response) {
                        drinkRemovedDialog();
                    }
                };
                drinkRemoveVerificationDialog(listener);
                deleteMixdrankThread request = new deleteMixdrankThread(listener);
                request.execute(Integer.toString(response.getMixDrankId()));
            }


        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_info);
        Bundle b = getIntent().getExtras();
        int identifier = b.getInt("id");
        init();
        //Hiermee vraagt de activity de benodigde gegevens op.
        OnTaskCompleted<MixDrankResponse> listener = new OnTaskCompleted<MixDrankResponse>() {
            @Override
            public void onTaskCompleted(MixDrankResponse _response) {
                updateText(_response);
                response = _response;
            }
        };
        getCocktailThread request = new getCocktailThread(listener);
        request.execute(Integer.toString(identifier));
    }
    //Weergeeft een popup met daarin de woorden dat het drankje geserveerd/uitgeschonken is
    private void drinkServedDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(
                CocktailInfo_Activity.this);

        builder.setTitle("Enjoy!");
        builder.setMessage("Your drink is served.");
        builder.show();
    }
    //Weergeeft een popup waarin aangegeven word, dat het drankje verwijderd is.
    private void drinkRemovedDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(
                CocktailInfo_Activity.this);

        builder.setTitle("Succes :(");
        builder.setMessage("The drink has been removed from the menu.");
        builder.show();
    }
    //Deze dialoog vraagt of je zeker weet of je het drankje wil verwijderen, zoja verwijdert hij het drankje. Zo nee gebeurt er niks en verdwijnt het venster
    private void drinkRemoveVerificationDialog(final OnTaskCompleted<Response> listener)
    {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to remove this entry?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteMixdrankThread request = new deleteMixdrankThread(listener);
                        request.execute(Integer.toString(response.getMixDrankId()));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    //Als de gegevens gevonden zijn, worden de textfiels in de activity geupdate met dit stukje code
    private void updateText(MixDrankResponse response){
        cocktailName = (TextView) findViewById(R.id.CocktailName);
        drinkOne = (TextView) findViewById(R.id.drinkOne);
        drinkTwo = (TextView) findViewById(R.id.drinkTwo);
        drinkThree = (TextView) findViewById(R.id.drinkThree);

        cocktailName.setText(response.getMixDrankNaam());
        drinkOne.setText(response.getNaamDrankEen());
        drinkTwo.setText(response.getNaamDrankTwee());
        drinkThree.setText(response.getNaamDrankDrie());




    }

}
