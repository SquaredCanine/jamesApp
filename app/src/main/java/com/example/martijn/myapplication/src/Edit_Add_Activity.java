package com.example.martijn.myapplication.src;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.martijn.myapplication.R;
import com.example.martijn.myapplication.threads.addCocktailThread;
import com.example.martijn.myapplication.threads.getCocktailThread;
import com.example.martijn.myapplication.threads.editContentsOfCocktailThread;

import API.api.MixDrankResponse;
import API.api.Response;
/*
deze activity is zo geschreven dat je hier met 2 verschillende dingen terecht kan
je kan een bestaande drank aanpassen, in dat geval word er een identifier meegegeven om de correcte gegevens op te halen
Of je kan een nieuw drankje maken, in dat geval word de activity leeg opgestart.
 */
public class Edit_Add_Activity extends AppCompatActivity {

    Button   AddButton;
    Button   ClearButton;
    EditText CocktailName;
    EditText DrinkOne;
    EditText DrinkTwo;
    EditText DrinkThree;
    EditText VolumeDrinkOne;
    EditText VolumeDrinkTwo;
    EditText VolumeDrinkThree;

    MixDrankResponse response;

    boolean add = true;

    public void init(){
        AddButton = (Button) findViewById(R.id.AddButton);
        ClearButton = (Button) findViewById(R.id.ClearButton);

        CocktailName = (EditText) findViewById(R.id.CocktailName);

        DrinkOne = (EditText) findViewById(R.id.DrinkOne);
        DrinkTwo = (EditText) findViewById(R.id.DrinkTwo);
        DrinkThree = (EditText) findViewById(R.id.DrinkThree);

        VolumeDrinkOne = (EditText) findViewById(R.id.volumeDrinkOne);
        VolumeDrinkTwo = (EditText) findViewById(R.id.volumeDrinkTwo);
        VolumeDrinkThree = (EditText) findViewById(R.id.volumeDrinkThree);

        //Dit knopje voegt, aan de hand van een boolean, een drankje toe, of wijzigt een bestaand drankje
        AddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                System.out.println("click AddButton");

                String cocktailName = CocktailName.getText().toString();

                String drinkOne = DrinkOne.getText().toString();
                String drinkTwo = DrinkTwo.getText().toString();
                String drinkThree = DrinkThree.getText().toString();

                String volumeDrinkOne = VolumeDrinkOne.getText().toString();
                String volumeDrinkTwo = VolumeDrinkTwo.getText().toString();
                String volumeDrinkThree = VolumeDrinkThree.getText().toString();
                if(add)
                {
                    OnTaskCompleted<Response> listener = new OnTaskCompleted<Response>() {
                        @Override
                        public void onTaskCompleted(Response response) {
                            newCocktailAddedDialog();
                        }
                    };
                    addCocktailThread request = new addCocktailThread(listener);
                    request.execute(cocktailName, drinkOne, volumeDrinkOne, drinkTwo, volumeDrinkTwo, drinkThree, volumeDrinkThree);

                    System.out.println(cocktailName + " " + drinkOne + " " + drinkTwo + " " + drinkThree + " " + volumeDrinkOne + " " + volumeDrinkTwo + " " + volumeDrinkThree);

                    clearText();
                }else{
                    OnTaskCompleted<Response> listener = new OnTaskCompleted<Response>() {
                        @Override
                        public void onTaskCompleted(Response response) {
                            newCocktailEditedDialog();
                        }
                    };
                    editContentsOfCocktailThread request = new editContentsOfCocktailThread(listener);
                    request.execute(Integer.toString(response.getMixDrankId()),cocktailName, drinkOne, volumeDrinkOne, drinkTwo, volumeDrinkTwo, drinkThree, volumeDrinkThree);

                    System.out.println(cocktailName + " " + drinkOne + " " + drinkTwo + " " + drinkThree + " " + volumeDrinkOne + " " + volumeDrinkTwo + " " + volumeDrinkThree);

                    clearText();
                }


            }

        });
        //Verwijdert alle info die in de fields staat.
        ClearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                clearText();




            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Cocktail");
        //Als er geen identifier word meegegeven, crasht hij hier. En gaat de activity ervan uit dat er een nieuw drankje word gemaakt.
        try{
            Bundle b = getIntent().getExtras();
            int identifier = b.getInt("id");
            updateText(Integer.toString(identifier));
            setTitle("Edit Cocktail");
            add = false;
        }
        catch(Exception ee)
        {

        }

        setContentView(R.layout.activity_edit__add);
        init();
    }
    //Venster waarin staat dat je drankje toegevoegd is.
    private void newCocktailAddedDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(
                Edit_Add_Activity.this);

        builder.setTitle("Succes");
        builder.setMessage("Your Cocktail has been added");
        builder.show();
    }
    //Venster waarin staat dat je drankje aangepast is.
    private void newCocktailEditedDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(
                Edit_Add_Activity.this);

        builder.setTitle("Succes");
        builder.setMessage("Your Cocktail has been edited");
        builder.show();
    }
    //Verwijder alle tekst in de tekstfields
    public void clearText(){
        CocktailName.setText("");

        DrinkOne.setText("");
        DrinkTwo.setText("");
        DrinkThree.setText("");

        VolumeDrinkOne.setText("");
        VolumeDrinkTwo.setText("");
        VolumeDrinkThree.setText("");
    }
    //Als alle gegevens binnen zijn van het aan te passen drankje word hierin de tekst geupdate
    public void updateText(String identifier){
        OnTaskCompleted<MixDrankResponse> listener = new OnTaskCompleted<MixDrankResponse>() {
            @Override
            public void onTaskCompleted(MixDrankResponse _response) {
                CocktailName.setText(_response.getMixDrankNaam());

                DrinkOne.setText(_response.getNaamDrankEen());
                DrinkTwo.setText(_response.getNaamDrankTwee());
                DrinkThree.setText(_response.getNaamDrankDrie());

                VolumeDrinkOne.setText("" + _response.getMlDrankEen());
                VolumeDrinkTwo.setText("" + _response.getMlDrankTwee());
                VolumeDrinkThree.setText("" + _response.getMlDrankDrie());
                response = _response;
            }
        };
        getCocktailThread request = new getCocktailThread(listener);
        request.execute(identifier);
    }
}
