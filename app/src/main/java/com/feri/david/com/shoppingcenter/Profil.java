package com.feri.david.com.shoppingcenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profil extends AppCompatActivity {
    private static final String TAG = "Profil" ;
    private TextView ime, mail, ocene;
    private CircleImageView slikica;
    ApplicationMy app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ime = (TextView) findViewById(R.id.ime);
        mail = (TextView) findViewById(R.id.mail);
        ocene = (TextView) findViewById(R.id.ocene);
        slikica = (CircleImageView) findViewById(R.id.prikaz);
        app = (ApplicationMy) getApplication();
        setUserData();
    }

    private void setUserData() {
        String s= app.getAcct().getEmail();
        String ss= app.getAcct().getDisplayName();
        Uri personPhoto = app.getAcct().getPhotoUrl();
        if(personPhoto == null){

        }else{
            Log.d(TAG,personPhoto.toString());
            new DownloadImageTask((CircleImageView) findViewById(R.id.prikaz))
                    .execute(app.getAcct().getPhotoUrl().toString());
            ime.setText(ss);
            mail.setText(s);
            ocene.setText(app.getAll().getOc().getStevilo().toString());
        }
    }

    public void Resetiraj(MenuItem item) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Profil.this);
        builder1.setMessage("Ali res zelite resetirati ocene!?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        app.getAll().getOc().nastavi();
                        app.save();
                        dialog.cancel();
                        finish();
                        startActivity(getIntent());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prof_menu, menu);
        return true;
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
