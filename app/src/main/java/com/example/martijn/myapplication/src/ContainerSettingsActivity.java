package com.example.martijn.myapplication.src;

import android.content.DialogInterface;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.martijn.myapplication.R;
import com.example.martijn.myapplication.threads.*;

import API.api.*;
/*
Deze activity weergeeft de inhoud en status van de containers, en stelt de gebruiker ook in staat om de containers bij te vullen/aan te passen
 */
public class ContainerSettingsActivity extends AppCompatActivity {

    static final int container1 = 0;
    static final int container2 = 0;
    static final int container3 = 0;

    private ImageButton editContainerOne;
    private ImageButton editContainerTwo;
    private ImageButton editContainerThree;



    ProgressBar containerBar1;
    ProgressBar containerBar2;
    ProgressBar containerBar3;

    TextView containerName1;
    TextView containerName2;
    TextView containerName3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_settings);

        containerBar1 = (ProgressBar) findViewById(R.id.containerBar1);
        containerBar2 = (ProgressBar) findViewById(R.id.containerBar2);
        containerBar3 = (ProgressBar) findViewById(R.id.containerBar3);

        containerName1 = (TextView) findViewById(R.id.containerName1);
        containerName2 = (TextView) findViewById(R.id.containerName2);
        containerName3 = (TextView) findViewById(R.id.containerName3);

        containerBar1.setProgress(0);
        containerBar2.setProgress(0);
        containerBar3.setProgress(0);

        editContainerOne = (ImageButton) findViewById(R.id.imageButton);
        editContainerTwo = (ImageButton) findViewById(R.id.imageButton2);
        editContainerThree = (ImageButton) findViewById(R.id.imageButton3);

        getContainerBars();
        setContainer1();
        setContainer2();
        setContainer3();

    }
    //Hiermee word de inhoud van de containers opgevraagd van de server.
    private void getContainerBars()
    {
        OnTaskCompleted<SlotResponse> listener = new OnTaskCompleted<SlotResponse>() {
            @Override
            public void onTaskCompleted(SlotResponse response) {
                setContainerBars(response);
            }
        };
        getContentOfContainerThread request = new getContentOfContainerThread(listener);
        request.execute("1");
        getContentOfContainerThread request2 = new getContentOfContainerThread(listener);
        request2.execute("2");
        getContentOfContainerThread request3 = new getContentOfContainerThread(listener);
        request3.execute("3");
    }


    //Als de gegevens aanwezig zijn, vult hij hier de textfiels/bars.
    private void setContainerBars(SlotResponse response)
    {
        switch(response.getSlotId())
        {
            case 1:
                containerBar1.setProgress(response.getMlDrank());
                containerName1.setText(response.getDrankNaam());
                break;
            case 2:
                containerBar2.setProgress(response.getMlDrank());
                containerName2.setText(response.getDrankNaam());
                break;
            case 3:
                containerBar3.setProgress(response.getMlDrank());
                containerName3.setText(response.getDrankNaam());
                break;
        }

    }



    //Als je een container wilt aanpassen gebeurt dat hier. Er verschijnt een dialoog waar je de container kan aanpassen.
    private void setContainer1(){
        editContainerOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ContainerSettingsActivity.this);
                inputAlert.setTitle("Edit container one");
                inputAlert.setMessage(containerName1.getText());

                final EditText containerDrinkName = new EditText(ContainerSettingsActivity.this);

                inputAlert.setView(containerDrinkName);

                inputAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newContainerDrinkName = containerDrinkName.getText().toString();

                        OnTaskCompleted<Response> listener = new OnTaskCompleted<Response>() {
                            @Override
                            public void onTaskCompleted(Response response) {
                                getContainerBars();
                            }
                        };
                        setContentOfContainerThread request = new setContentOfContainerThread(listener);
                        request.execute("1",newContainerDrinkName,"1500");
                    }
                });

                inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = inputAlert.create();
                alertDialog.show();
            }
        });
    }

    private void setContainer2(){
        editContainerTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ContainerSettingsActivity.this);
                inputAlert.setTitle("Edit container two");
                inputAlert.setMessage(containerName2.getText());

                final EditText containerDrinkName = new EditText(ContainerSettingsActivity.this);

                inputAlert.setView(containerDrinkName);

                inputAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newContainerDrinkName = containerDrinkName.getText().toString();

                        OnTaskCompleted<Response> listener = new OnTaskCompleted<Response>() {
                            @Override
                            public void onTaskCompleted(Response response) {
                                getContainerBars();
                            }
                        };
                        setContentOfContainerThread request = new setContentOfContainerThread(listener);
                        request.execute("2",newContainerDrinkName,"1500");
                    }
                });

                inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = inputAlert.create();
                alertDialog.show();
            }
        });

    }

    private void setContainer3(){
        editContainerThree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ContainerSettingsActivity.this);
                inputAlert.setTitle("Edit container three");
                inputAlert.setMessage(containerName3.getText());

                final EditText containerDrinkName = new EditText(ContainerSettingsActivity.this);

                inputAlert.setView(containerDrinkName);

                inputAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newContainerDrinkName = containerDrinkName.getText().toString();

                        OnTaskCompleted<Response> listener = new OnTaskCompleted<Response>() {
                            @Override
                            public void onTaskCompleted(Response response) {
                                getContainerBars();
                            }
                        };
                        setContentOfContainerThread request = new setContentOfContainerThread(listener);
                        request.execute("3",newContainerDrinkName,"1500");
                    }
                });

                inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = inputAlert.create();
                alertDialog.show();
            }
        });
    }
}
