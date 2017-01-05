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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Belezka;
import com.example.Komplet_Elegant;
import com.example.PredlaganiKompleti;
import com.example.Prodajalna;

public class Activity_Predlagani_Podrobnosti extends AppCompatActivity {
    public static final String PARAMETER_ACTIVITY_1="ActivityFirst_PARAMETER_ACTIVITY_1";
    public static final String PARAMETER_POSITION_1 = "POSITION_PREDLAGAN";
    private TextView Naziv_Trgovine, Naslov, Kraj, Komplet_st, Komplet_cena;
    private EditText Komplet_naziv;
    private Prodajalna pro;
    private ApplicationMy app;
    private Komplet_Elegant kom;
    private Integer ind;
    private PredlaganiKompleti predl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__predlagani__podrobnosti);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Naziv_Trgovine = (TextView) findViewById(R.id.Naziv_Trgovine);
        Kraj = (TextView) findViewById(R.id.Kraj);
        Naslov = (TextView) findViewById(R.id.Naslov);
        Komplet_naziv = (EditText) findViewById(R.id.Komplet_naziv);
        Komplet_st = (TextView) findViewById(R.id.Komplet_st);
        Komplet_cena = (TextView) findViewById(R.id.Komplet_cena);

        app = (ApplicationMy) getApplication();
        pro = app.getAll().getPredlag().get(0).getPro(); //default for test
        kom = pro.getKom().get(0);
        predl = app.getAll().getPredlag().get(0);
        if (predl!=null) {
            SetProdajalna(predl);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dva = new Intent(Activity_Predlagani_Podrobnosti.this, Mapa.class);
                dva.putExtra(PARAMETER_POSITION_1,0);
                startActivity(dva);
            }
        });
    }

    private void SetProdajalna(PredlaganiKompleti p) {
        Naziv_Trgovine.setText(pro.getNaziv());
        Kraj.setText(pro.getLokacija().getPosta());
        Naslov.setText(pro.getLokacija().getNaslov()+ " " + pro.getLokacija().getHisna());
        Komplet_naziv.setText(p.getNaziv());
        Komplet_st.setText(p.getSak().getVelikost());
        Komplet_cena.setText((int)p.Skupaj_dobi(p) + "€");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent!=null) {
            //Integer message = intent.getIntExtra(AdapterKompletov.PARAMETER_POSITION_1,0);
            //Integer message1 = intent.getIntExtra(AdapterOblacil.PARAMETER_POSITION_1,0);
            ind = intent.getIntExtra(AdapterPredlaganih.PARAMETER_POSITION_1,0); //id
            predl = app.getAll().getPredlag().get(ind);
            pro = app.getAll().getPredlag().get(ind).getPro(); //default for test
            SetProdajalna(predl);
        }
    }

}
