package com.feri.david.com.shoppingcenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.DataAll;
import com.example.Hlace;
import com.example.Komplet_Elegant;
import com.example.Nogavice;
import com.example.Obutev;
import com.example.Pas;
import com.example.Prodajalna;
import com.example.Sako;
import com.example.Srajca;
import com.example.Telovnik;

public class Activity_Dodaj_Novo extends AppCompatActivity {
    private String Trg[];
    private ApplicationMy app;
    private int a=0;
    private int pozicija=0;
    private Komplet_Elegant kom;
    private EditText Komplet_naziv1;
    private EditText Stevilka;
    private Integer indeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__dodaj__novo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        app = (ApplicationMy) getApplication();
        a = app.getAll().Velikost();
        Trg = new String[a];
        for(int i=0; i<a; i++){
            Trg[i] =app.getAll().getProd().get(i).getNaziv();
        }

        Intent intent = getIntent();
        if (intent!=null) {
            indeks = intent.getIntExtra(ActivityListOblacil.PARAMETER_POSITION_1,0); //id
            //System.out.println(indeks);
        }

        Komplet_naziv1 = (EditText) findViewById(R.id.Komplet_naziv);
        Stevilka = (EditText) findViewById(R.id.Komplet_st);
        // Selection of the spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, Trg);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Dodaj_Novo.this);
                builder1.setMessage("Ali res želite dodati artikel?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Spinner spinner = (Spinner)findViewById(R.id.spinner);
                                pozicija = spinner.getSelectedItemPosition();
                                kom = new Komplet_Elegant(Komplet_naziv1.getText().toString(), "Moski",
                                        new Hlace("Klasik - Zelene hlace", "Moski", "34/32", "Slim Fit", "Crna", 20.99),
                                        new Sako("Klasik - Zeleni Sako", "Moski",Stevilka.getText().toString()  ,"Slim Fit", "Crna", 110.99),
                                        new Obutev("Klasik - elegantni zeleni cevlji", "Elegant", "Moski", 43, "Crna", "Usnje", 23.99),
                                        new Srajca("Klasik - Rumena srajca", "Moski", "M", "Slim Fit", "Bela", 25.99),
                                        new Telovnik("Klasik - Zeleni telovnik", "Moski", "M", "Slim Fit", "Crna", 79.99),
                                        new Nogavice("Klasik - Zelene nogavice", "Moski", 43, "Crna", "Svila", 15.99),
                                        new Pas("Klasik - Rumeni pas", "Moski", "95-105", "Crna", 11.99));
                                app.getAll().getProd().get(pozicija).dodaj_novega(kom);
                                app.save();
                                Snackbar.make(view, "Komplet " + Komplet_naziv1.getText().toString() + " je bil shranjen!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                dialog.cancel();
                                NavUtils.navigateUpFromSameTask(Activity_Dodaj_Novo.this);
                            }
                        });

                builder1.setNegativeButton(
                        "Ne",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });
    }

}
