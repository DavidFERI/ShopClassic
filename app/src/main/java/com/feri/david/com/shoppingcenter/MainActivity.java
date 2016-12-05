package com.feri.david.com.shoppingcenter;

import android.content.DialogInterface;
import android.content.Intent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import com.example.Belezka;
import com.example.DataAll;
import com.example.Komplet_Elegant;
import com.example.Prodajalna;
import com.example.Sako;

public class MainActivity extends AppCompatActivity {
    public static final String PARAMETER_ACTIVITY_1="ActivityFirst_PARAMETER_ACTIVITY_1";
    public static final String PARAMETER_POSITION_1 = "POSITION_TRGOVINA";
    private TextView Naziv_Trgovine, Naslov, Kraj, Komplet_st, Komplet_cena;
    private EditText Komplet_naziv;
    private Prodajalna pro;
    private ApplicationMy app;
    private Komplet_Elegant kom;
    private RatingBar r_bar;
    private Integer ids;
    private Integer ids_zbris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Naziv_Trgovine = (TextView) findViewById(R.id.Naziv_Trgovine);
        Kraj = (TextView) findViewById(R.id.Kraj);
        Naslov = (TextView) findViewById(R.id.Naslov);
        Komplet_naziv = (EditText) findViewById(R.id.Komplet_naziv);
        Komplet_st = (TextView) findViewById(R.id.Komplet_st);
        Komplet_cena = (TextView) findViewById(R.id.Komplet_cena);
        r_bar = (RatingBar) findViewById(R.id.ratingBar);

        app = (ApplicationMy) getApplication();
        pro = app.getAll().getProd(0); //default for test
        kom = pro.getKom().get(0);
        if (pro!=null) {
            SetProdajalna(pro);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dva = new Intent(MainActivity.this, Mapa.class);
                dva.putExtra(PARAMETER_POSITION_1,ids);
                startActivity(dva);
            }
        });

        r_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                kom.dodaj_oceno(Float.parseFloat(String.valueOf(rating)));
                Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Hvala za vašo oceno!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                app.getAll().getOc().dodaj();
                app.save();
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            }
        });
    }


    public void ShowMenuOption(MenuItem item) {
        popravi(pro);
    }

    public void Odpri_Novo(MenuItem item) {
        Intent dva = new Intent(this, ActivitySecond.class);
        dva.putExtra(PARAMETER_ACTIVITY_1,pro.getKom().get(0).getNaziv());
        startActivity(dva);
    }

    public void Zemljevid(MenuItem item) {
        Intent dva = new Intent(MainActivity.this, Mapa.class);
        dva.putExtra(PARAMETER_POSITION_1,ids);
        startActivity(dva);
    }

    public void Trgovine(MenuItem item) {
        Intent dva = new Intent(this, ActivityList.class);
        startActivity(dva);
    }

    public void Kompleti(MenuItem item) {
        Intent dva = new Intent(this, ActivityListOblacil.class);
        startActivity(dva);
    }

    public void Kos(MenuItem item) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("Ali res želite izbrisati artikel: " + kom.getNaziv() + "?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(app.getAll().getProd().get(ids).getKom().get(ids_zbris).getSlika_uri()!=null)
                        {
                            File file=new File(app.getAll().getProd().get(ids).getKom().get(ids_zbris).getSlika_uri());
                            file.delete();
                        }
                        app.getAll().getProd().get(ids).odstrani(ids_zbris);
                        app.save();
                        dialog.cancel();
                        NavUtils.navigateUpFromSameTask(MainActivity.this);
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


    private void popravi(Prodajalna k) {
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), kom.getNaziv() + " je bil shranjen v beležko!" , Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        if(kom.getSlika_uri() == null){
            app.getAll().dodaj(new Belezka(kom.getNaziv(),kom.getSpol(),new Sako("Klasik - sako", "Moski", "M" ,"Slim Fit", "Crna", kom.getSak().getCena()),k));
        }else{
            app.getAll().dodaj(new Belezka(kom.getNaziv(),kom.getSpol(),new Sako("Klasik - sako", "Moski", "M" ,"Slim Fit", "Crna", kom.getSak().getCena()),k,kom.getSlika_uri()));
        }
        app.save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            popravi(pro);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void SetProdajalna(Prodajalna p) {
        Naziv_Trgovine.setText(p.getNaziv());
        Kraj.setText(p.getLokacija().getPosta());
        Naslov.setText(p.getLokacija().getNaslov()+ " " + p.getLokacija().getHisna());
        Komplet_naziv.setText(kom.getNaziv());
        Komplet_st.setText(kom.getSak().getVelikost());
        Komplet_cena.setText((int)kom.Skupaj_dobi(kom) + "€");
        r_bar.setRating((float)kom.skupaj_ocena());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent!=null) {
            //Integer message = intent.getIntExtra(AdapterKompletov.PARAMETER_POSITION_1,0);
            //Integer message1 = intent.getIntExtra(AdapterOblacil.PARAMETER_POSITION_1,0);
            Object[] s = (Object[]) getIntent().getSerializableExtra(AdapterKompletov.PARAMETER_POSITION_1);
            if(s != null){
                Integer[] newArray = Arrays.copyOf(s, s.length, Integer[].class);
                ids = newArray[0];
                ids_zbris = newArray[1];
                kom = app.getAll().getProd(newArray[0]).getKom().get(newArray[1]);
                pro = app.getAll().getProd(newArray[0]); //default for test
                SetProdajalna(pro);
            }
        }
    }
}
