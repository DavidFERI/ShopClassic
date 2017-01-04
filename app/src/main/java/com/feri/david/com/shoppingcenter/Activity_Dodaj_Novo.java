package com.feri.david.com.shoppingcenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.FloatMath;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.ads.mediation.customevent.CustomEventNativeListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Activity_Dodaj_Novo extends AppCompatActivity {
    private String Trg[];
    private String Stevilka_st[];
    private ApplicationMy app;
    private String razdalja;
    private String odlocitev;
    private String Cena_weka;
    private Double razdalja_v_dub;
    private int a=0;
    private int pozicija=0;
    private int pozicija_st=0;
    private Komplet_Elegant kom;
    private String Ime_Trgovine;
    private ImageView slikaj_se;
    private EditText Komplet_naziv1;
    private EditText Cena;
    private Integer indeks;
    private Bitmap imageBitmap;
    static List<Address> geocodeMatches = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;




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
        Stevilka_st = new String[8];
        Stevilka_st[0] = "XS";
        Stevilka_st[1] = "S";
        Stevilka_st[2] = "M";
        Stevilka_st[3] = "L";
        Stevilka_st[4] = "XL";
        Stevilka_st[5] = "XXL";
        Stevilka_st[6] = "XXXL";
        Stevilka_st[7] = "XXXXL";

        Intent intent = getIntent();
        if (intent!=null) {
            indeks = intent.getIntExtra(ActivityListOblacil.PARAMETER_POSITION_1,0); //id
            //System.out.println(indeks);
        }

        Komplet_naziv1 = (EditText) findViewById(R.id.Komplet_naziv);
        Cena = (EditText) findViewById(R.id.Komplet_cena);
        slikaj_se = (ImageView) findViewById(R.id.slikica_suit);
        // Selection of the spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner stevilke_spinner = (Spinner) findViewById(R.id.spinnerNovi);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, Trg);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, Stevilka_st);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stevilke_spinner.setAdapter(spinnerArrayAdapter1);

        ImageView img = (ImageView) findViewById(R.id.slikica_suit);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Activity_Dodaj_Novo.this, "Odpiram kamero!", Toast.LENGTH_SHORT).show();
                dispatchTakePictureIntent();
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_Dodaj_Novo.this);
                builder1.setMessage("Ali res želite dodati artikel?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Spinner spinner = (Spinner)findViewById(R.id.spinner);
                                pozicija = spinner.getSelectedItemPosition();

                                Spinner spinner_st = (Spinner)findViewById(R.id.spinnerNovi);
                                pozicija_st = spinner_st.getSelectedItemPosition();

                                if(imageBitmap!=null)
                                {
                                    kom = new Komplet_Elegant(Komplet_naziv1.getText().toString(), "Moski",
                                            new Hlace("Klasik - Zelene hlace", "Moski", "34/32", "Slim Fit", "Crna", 20.99),
                                            new Sako("Klasik - Zeleni Sako", "Moski", Stevilka_st[pozicija_st]  ,"Slim Fit", "Crna", Double.parseDouble(Cena.getText().toString())),
                                            new Obutev("Klasik - elegantni zeleni cevlji", "Elegant", "Moski", 43, "Crna", "Usnje", 23.99),
                                            new Srajca("Klasik - Rumena srajca", "Moski", "M", "Slim Fit", "Bela", 25.99),
                                            new Telovnik("Klasik - Zeleni telovnik", "Moski", "M", "Slim Fit", "Crna", 79.99),
                                            new Nogavice("Klasik - Zelene nogavice", "Moski", 43, "Crna", "Svila", 15.99),
                                            new Pas("Klasik - Rumeni pas", "Moski", "95-105", "Crna", 11.99), 0.0,
                                            app.getExternalFilesDir("ShoppingCenter_slike")+"/"+app.getAll().vel()+"ID.png");
                                    System.out.println(app.getExternalFilesDir("ShoppingCenter_slike")+"/"+app.getAll().vel()+"ID.png");
                                    app.getAll().poveci();
                                }
                                else
                                {
                                    kom = new Komplet_Elegant(Komplet_naziv1.getText().toString(), "Moski",
                                            new Hlace("Klasik - Zelene hlace", "Moski", "34/32", "Slim Fit", "Crna", 20.99),
                                            new Sako("Klasik - Zeleni Sako", "Moski", Stevilka_st[pozicija_st]  ,"Slim Fit", "Crna", Double.parseDouble(Cena.getText().toString())),
                                            new Obutev("Klasik - elegantni zeleni cevlji", "Elegant", "Moski", 43, "Crna", "Usnje", 23.99),
                                            new Srajca("Klasik - Rumena srajca", "Moski", "M", "Slim Fit", "Bela", 25.99),
                                            new Telovnik("Klasik - Zeleni telovnik", "Moski", "M", "Slim Fit", "Crna", 79.99),
                                            new Nogavice("Klasik - Zelene nogavice", "Moski", 43, "Crna", "Svila", 15.99),
                                            new Pas("Klasik - Rumeni pas", "Moski", "95-105", "Crna", 11.99), 0.0);
                                }
                                app.getAll().getProd().get(pozicija).dodaj_novega(kom);
                                app.save();


                                try {
                                    //racunanje razdalje od tukaj kje si do trgovin
                                    if (ActivityCompat.checkSelfPermission(Activity_Dodaj_Novo.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Activity_Dodaj_Novo.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        Toast.makeText(Activity_Dodaj_Novo.this, "Prvo vklopite LOCATION ACCESS v nastavitvah.", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    //trenutna pozicija
                                    double longitude1, latitude1;
                                    if (location != null) {
                                        longitude1 = location.getLongitude();
                                        latitude1 = location.getLatitude();
                                    }else{
                                        longitude1 = 15.648791;
                                        latitude1 = 46.558412;
                                    }
                                    //pozicija trgovine
                                    geocodeMatches = new Geocoder(Activity_Dodaj_Novo.this.getBaseContext()).getFromLocationName(app.getAll().getProd().get(pozicija).getLokacija().getHisna() + app.getAll().getProd().get(pozicija).getLokacija().getNaslov() + app.getAll().getProd().get(pozicija).getLokacija().getPostna_st(), 1);
                                    razdalja_v_dub = distance(latitude1,longitude1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                //Diskretizacije
                                if(razdalja_v_dub < 19.5){
                                    razdalja = "'\\'" + "(-inf-19.5]" + "\\" +  "\'" + "\'";
                                    //System.out.println(razdalja);
                                }else{
                                    if(razdalja_v_dub >=19.5 && razdalja_v_dub < 42.5 ){
                                        razdalja = "'\\'" + "(19.5-42.5]" + "\\" +  "\'" + "\'";
                                    }else{
                                        if(razdalja_v_dub >=42.5 && razdalja_v_dub < 125 ){
                                            razdalja = "'\\'" + "(42.5-125]" + "\\" +  "\'" + "\'";
                                        }else{
                                            if(razdalja_v_dub >=125 && razdalja_v_dub < 302.5 ){
                                                razdalja = "'\\'" + "(125-302.5]" + "\\" +  "\'" + "\'";
                                            }else{
                                                if(razdalja_v_dub >=302.5){
                                                    razdalja = "'\\'" + "(302.5-inf)" + "\\" +  "\'" + "\'";
                                                }
                                            }
                                        }
                                    }
                                }
                                Double cena = kom.getSak().getCena();
                                if(cena < 19.5){
                                    Cena_weka = "'\\'" + "(-inf-19.5]" + "\\" +  "\'" + "\'";
                                    //System.out.println(razdalja);
                                }else{
                                    if(cena >= 19.5 && cena < 42.5){
                                        Cena_weka = "'\\'" + "(19.5-42.5]" + "\\" +  "\'" + "\'";
                                    }else{
                                        if(cena >= 42.5 && cena < 85){
                                            Cena_weka = "'\\'" + "(42.5-85]" + "\\" +  "\'" + "\'";
                                        }else{
                                            if(cena >= 85 && cena < 275){
                                                Cena_weka = "'\\'" + "(85-275]" + "\\" +  "\'" + "\'";
                                            }else{
                                                if(cena >= 275){
                                                    Cena_weka = "'\\'" + "(275-inf)" + "\\" +  "\'" + "\'";
                                                }
                                            }
                                        }
                                    }
                                }

                                if(razdalja_v_dub < 19.5){
                                    odlocitev = "Prfektno";
                                    //System.out.println(razdalja);
                                }else{
                                    if(razdalja_v_dub >=19.5 && razdalja_v_dub < 42.5 ){
                                        odlocitev = "Odlicno";
                                    }else{
                                        if(razdalja_v_dub >=42.5 && razdalja_v_dub < 125 ){
                                            odlocitev = "Dobro";
                                        }else{
                                            if(razdalja_v_dub >=125 && razdalja_v_dub < 302.5 ){
                                                odlocitev = "Slabo";
                                            }else{
                                                if(razdalja_v_dub >=302.5){
                                                    odlocitev = "Najslabse";
                                                }
                                            }
                                        }
                                    }
                                }
                                String ime_tr = app.getAll().getProd().get(pozicija).getNaziv();
                                if(ime_tr.equals("ZARA")){
                                    Ime_Trgovine = "Zara";
                                }else{
                                    if(ime_tr.equals("C&A")){
                                        Ime_Trgovine = "CA";
                                    }else{
                                        if(ime_tr.equals("H&M")){
                                            Ime_Trgovine = "HM";
                                        }else{
                                            if(ime_tr.equals("Takko")){
                                                Ime_Trgovine = "Tacco";
                                            }else{
                                                if(ime_tr.equals("New Yorker")){
                                                    Ime_Trgovine = "NewYorker";
                                                }else{
                                                    if(ime_tr.equals("Hugo Boss")){
                                                        Ime_Trgovine = "HugoBoss";
                                                    }else{
                                                        Ime_Trgovine = app.getAll().getProd().get(pozicija).getNaziv();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                //Za dodajanje v veko
                                try(FileWriter fw = new FileWriter("/storage/sdcard1/bluetooth/misc/" + "Primerki_Najnovejse.txt", true);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    PrintWriter out = new PrintWriter(bw))
                                {
                                    out.println(Ime_Trgovine + ","+kom.getSak().getVelikost()+","+Cena_weka+"," + "'\\'" + "(0.5-2.5]" + "\\" +  "\'" + "\'" + ",Da,"+razdalja+"," + odlocitev);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Snackbar.make(view, "Komplet " + Komplet_naziv1.getText().toString() + " je bil shranjen!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                dialog.cancel();


                                Intent notificationIntent = new Intent(getApplicationContext(), ActivityListOblacil.class);

                                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

                                Intent notificationIntent1 = new Intent(getApplicationContext(), ActivityList.class);
                                PendingIntent intent1 = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent1, 0);

                                // Build notification
                                // Actions are just fake
                                Notification noti = new Notification.Builder(Activity_Dodaj_Novo.this)
                                        .setContentTitle("Hvala za uporabo!")
                                        .setContentText("Komplet " + kom.getNaziv() + " je bil dodan v bazo!").setSmallIcon(R.drawable.druga)
                                        .setContentIntent(intent)
                                        .addAction(R.drawable.trgovina, "Preglej trgovine", intent1)
                                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.jtnotification)).build();

                                // hide the notification after its selected
                                noti.sound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.jtnotification);
                                noti.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
                                noti.defaults |= Notification.DEFAULT_VIBRATE;
                                noti.ledARGB = 0xFF33ffff;
                                noti.ledOnMS = 100;
                                noti.ledOffMS = 100;

                                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


                                notificationManager.notify(0, noti);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK)
        {
            imageBitmap=app.getThumbnailBitmap(app.getExternalFilesDir("ShoppingCenter_slike")+"/"+app.getAll().vel()+"ID.png",100);
            slikaj_se.setImageBitmap(imageBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String file_name=+app.getAll().vel()+"ID.png";
        File file= new File(app.getExternalFilesDir("ShoppingCenter_slike"),""+file_name);
        Uri urisave= Uri.fromFile(file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,urisave);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public static double distance(double lat2, double lon2) {
        double latitude;
        double longitude;
        double distance;

        if (geocodeMatches != null) {
            latitude = geocodeMatches.get(0).getLatitude();
            longitude = geocodeMatches.get(0).getLongitude();
            double lat1 = latitude;
            double lon1 = longitude;

            //System.out.println("Zarino: " + String.valueOf(lat1) + "    " + String.valueOf(lon1));
            //System.out.println("Mojo: " + String.valueOf(lat2) + "    " + String.valueOf(lon2));
            Location locationA = new Location("point A");

            locationA.setLatitude(lat1);
            locationA.setLongitude(lon1);

            Location locationB = new Location("point B");

            locationB.setLatitude(lat2);
            locationB.setLongitude(lon2);

            float distance1 = locationA.distanceTo(locationB)/1000;
            distance = (double) distance1;
            //System.out.println(distance);

        }else{
            distance = 80000;
        }
        return distance;
    }

}
