package com.feri.david.com.shoppingcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.DataAll;

public class ActivityListOblacil extends AppCompatActivity {
    public static final String PARAMETER_POSITION_1 = "POSITION_TRGOVINA";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ApplicationMy app;
    private Integer indeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list_oblacil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = (ApplicationMy) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent!=null) {
            indeks = intent.getIntExtra(AdapterOblacil.PARAMETER_POSITION,0); //id
            //System.out.println(indeks);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dva = new Intent(ActivityListOblacil.this, ActivitySecond.class);
                startActivity(dva);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // LINEARNO
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterKompletov(app, indeks, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_oblacil, menu);
        return true;
    }


    public void ShowMenuOption(MenuItem item) {
        Intent dva = new Intent(ActivityListOblacil.this, Activity_Dodaj_Novo.class);
        dva.putExtra(PARAMETER_POSITION_1,indeks);
        startActivity(dva);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent!=null) {
            indeks = intent.getIntExtra(AdapterOblacil.PARAMETER_POSITION,0); //id
            System.out.println(indeks);
        }
        app = (ApplicationMy) getApplication();
        mAdapter.notifyDataSetChanged();
    }
}
