package com.feri.david.com.shoppingcenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ActivitySecond extends AppCompatActivity {
    public static final String PARAMETER_POSITION_1 = "POSITION_TRGOVINA";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RelativeLayout celi;
    ApplicationMy app;
    private Integer indeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = (ApplicationMy) getApplication();
        celi = (RelativeLayout) findViewById(R.id.kordi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(app.getAll().getBel().size() == 0){
                    Toast.makeText(ActivitySecond.this, "Beležka je prazna!", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivitySecond.this);
                    builder1.setMessage("Ali res želite izbrisati celotno beležko!?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Da",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    final Animation animation = AnimationUtils.loadAnimation(ActivitySecond.this, android.R.anim.fade_out);
                                    celi.startAnimation(animation);
                                    Handler handle = new Handler();
                                    handle.postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            app.getAll().izprazni();
                                            app.save();
                                            Toast.makeText(ActivitySecond.this, "Beležka je bila izbrisana!", Toast.LENGTH_SHORT).show();
                                            mAdapter.notifyDataSetChanged();
                                            animation.cancel();
                                        }
                                    }, 400);
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
                }
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // LINEARNO
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterBelezke(app, indeks, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

}
