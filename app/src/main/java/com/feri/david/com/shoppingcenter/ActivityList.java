package com.feri.david.com.shoppingcenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.DataAll;
import com.example.Hlace;
import com.example.Komplet_Elegant;
import com.example.Nogavice;
import com.example.Obutev;
import com.example.Pas;
import com.example.Sako;
import com.example.Srajca;
import com.example.Telovnik;

import java.io.File;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityList extends AppCompatActivity {
    private static final String TAG = "ActivityList" ;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ApplicationMy app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        app = (ApplicationMy) getApplication();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dva = new Intent(ActivityList.this, ActivitySecond.class);
                startActivity(dva);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // LINEARNO
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterOblacil(app.getAll(), this);
        mRecyclerView.setAdapter(mAdapter);

        setUserData();
    }

    private void setUserData() {
        String s= app.getAcct().getEmail();
        String ss= app.getAcct().getDisplayName();
        Uri personPhoto = app.getAcct().getPhotoUrl();
        if(personPhoto == null){

        }else{
            Log.d(TAG,personPhoto.toString());
            new DownloadImageTask((CircleImageView) findViewById(R.id.obprijavi))
                    .execute(app.getAcct().getPhotoUrl().toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_logout) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityList.this);
            builder1.setMessage("Ali se res želite odjaviti?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Da",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(ActivityList.this, ActivitySignIn.class);
                            i.putExtra(ActivitySignIn.LOGOUT, true);
                            startActivity(i);
                            ActivityList.this.finish();
                            dialog.cancel();
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
            return true;
        }

        if (id == R.id.Prof) {
            Intent dva = new Intent(ActivityList.this, Profil.class);
            startActivity(dva);
        }

        if (id == R.id.Predlagani) {
                LayoutInflater inflater = this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.belezka_insta,
                        (ViewGroup) this.findViewById(R.id.relativeLayout1));
                TextView text = (TextView) layout.findViewById(R.id.textView1);
                TextView text1 = (TextView) layout.findViewById(R.id.textView2);
                text.setText("Računam razdaljo med Vami in trgovinami!");
                text1.setText("Prosim počakajte!");
                Toast toast = new Toast(this);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                Intent dva = new Intent(ActivityList.this, Activity_Predlagani_PoClass.class);
                startActivity(dva);
        }


        return super.onOptionsItemSelected(item);
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
