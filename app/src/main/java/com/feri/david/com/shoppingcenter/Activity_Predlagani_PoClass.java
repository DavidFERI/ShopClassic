package com.feri.david.com.shoppingcenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Belezka;
import com.example.Komplet_Elegant;
import com.example.PredlaganiKompleti;
import com.example.Sako;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


//Delujocas
import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_Predlagani_PoClass extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PARAMETER_POSITION_1 = "POSITION_TRGOVINA";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CircleImageView slikica;
    private RelativeLayout celi;
    ApplicationMy app;
    private TextView ime, mail;
    private Integer indeks;
    static List<Address> geocodeMatches = null;
    private Double razdalja_v_dub;
    NavigationView navigationView;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__predlagani__po_class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = (ApplicationMy) getApplication();
        celi = (RelativeLayout) findViewById(R.id.predl);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dva = new Intent(Activity_Predlagani_PoClass.this, ActivitySecond.class);
                startActivity(dva);
            }
        });

        app.getAll().izprazni_predlagane();
        for(int i=0; i<app.getAll().Vel_Prod();i++){
            try {
                //racunanje razdalje od tukaj kje si do trgovin
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Prvo vklopite LOCATION ACCESS v nastavitvah.", Toast.LENGTH_LONG).show();
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
                geocodeMatches = new Geocoder(this.getBaseContext()).getFromLocationName(app.getAll().getProd().get(i).getLokacija().getHisna() + app.getAll().getProd().get(i).getLokacija().getNaslov() + app.getAll().getProd().get(i).getLokacija().getPostna_st(), 1);
                razdalja_v_dub = distance(latitude1,longitude1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                for(int j=0; j<app.getAll().Velikost_Kompletov_V_Trg(i);j++) {
                    Komplet_Elegant trenutni = app.getAll().getProd(i).Dobi_Komplet(j);
                    if (trenutni.getSak().getCena() < 125 && razdalja_v_dub < 125) {
                        if (trenutni.getSlika_uri() == null) {
                            app.getAll().dodaj(new PredlaganiKompleti(trenutni.getNaziv(), trenutni.getSpol(), new Sako("Klasik - sako", "Moski", "M", "Slim Fit", "Crna", trenutni.getSak().getCena()), app.getAll().getProd(i)));
                        } else {
                            app.getAll().dodaj(new PredlaganiKompleti(trenutni.getNaziv(), trenutni.getSpol(), new Sako("Klasik - sako", "Moski", "M", "Slim Fit", "Crna", trenutni.getSak().getCena()), app.getAll().getProd(i), trenutni.getSlika_uri()));
                        }
                    }
                }
            } catch(Exception e) {
                Toast.makeText(this, "Slab net!", Toast.LENGTH_LONG).show();
            }
        }
        app.save();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // LINEARNO
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterPredlaganih(app, indeks, this);
        mRecyclerView.setAdapter(mAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);

        ime = (TextView) headerView.findViewById(R.id.user);
        mail = (TextView) headerView.findViewById(R.id.emailj);
        slikica = (CircleImageView) headerView.findViewById(R.id.prikaz);

        try {
            setUserData();
        } catch (Exception e) {
            Toast.makeText(this, "Slab net!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__predlagani__po_class, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.poceni) {
            // Handle the camera action
        } else if (id == R.id.blizina) {

        } else if (id == R.id.fbshare) {
            String urlToShare = "http://stackoverflow.com/questions/7545254";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
            intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

            // See if official Facebook app is found
            boolean facebookAppFound = false;
            List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                    intent.setPackage(info.activityInfo.packageName);
                    facebookAppFound = true;
                    break;
                }
            }

            // As fallback, launch sharer.php in a browser
            if (!facebookAppFound) {
                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            }

            startActivity(intent);

        } else if (id == R.id.messenger) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent
                    .putExtra(Intent.EXTRA_TEXT,
                            "Živjo!\nSi navdušenec nakupovanja in bi rad kaj prihranil?!\n\nKar pogumno klikni na to najbolj noro aplikacijo stoletja!\n\nShopping Center!!!\n\nKlikni tukaj-> ŠE PRIJE LINK BMK");
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.facebook.orca");
            try {
                startActivity(sendIntent);
            }
            catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this,"Prosim namesti prvo Facebook messenger!", Toast.LENGTH_LONG).show();
            }
        }else if (id == R.id.Oceni) {
            try {
                Intent  intent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.maartendekkers.cyanapps"));
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.maartendekkers.cyanapps")));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
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

    private void setUserData() {
        String s= app.getAcct().getEmail();
        String ss= app.getAcct().getDisplayName();
        Uri personPhoto = app.getAcct().getPhotoUrl();
        if(personPhoto == null){

        }else{
            ime.setText(ss);
            mail.setText(s);
            new DownloadImageTask((CircleImageView) headerView.findViewById(R.id.prikaz))
                    .execute(app.getAcct().getPhotoUrl().toString());
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}