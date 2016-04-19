package com.feri.david.com.shoppingcenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.DataAll;
import com.example.Komplet_Elegant;
import com.example.Prodajalna;

/**
 * Created by DavidPC on 16.3.2016.
 */
public class AdapterKompletov extends RecyclerView.Adapter<AdapterKompletov.ViewHolder> {
    public static final String PARAMETER_POSITION_1 = "POSITION_KOMPLET";
    private ApplicationMy app;
    Activity ac;
    private Integer indeks;


    @Override////
    public void onBindViewHolder(ViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Komplet_Elegant trenutni = app.getAll().getProd(indeks).getKom().get(position);
        final String name = trenutni.getNaziv();
        holder.txtHeader.setText(trenutni.getNaziv());
        holder.txtHeader.setWidth(300);
        if (DataAll.picture1(trenutni.getNaziv()) == 1)
            holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.zara));
        if (DataAll.picture1(trenutni.getNaziv()) == 2)
            holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.other));
        if (DataAll.picture1(trenutni.getNaziv()) == 3)
            holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.no_image));
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dva = new Intent(ac, MainActivity.class);
                Integer[] integerArray = {indeks, position };
                dva.putExtra(PARAMETER_POSITION_1,  integerArray);
                ac.startActivity(dva);
                System.out.println(name);
            }
        });
        holder.druga.setText("Stevilka: " + trenutni.getSak().getVelikost());
        holder.tretja.setText(app.getAll().getProd(indeks).getLokacija().getNaslov() + " " + app.getAll().getProd(indeks).getLokacija().getHisna());
        holder.cetrta.setText(String.valueOf(trenutni.Skupaj(trenutni)) + "€");
    }

    @Override
    public int getItemCount() {
        return app.getAll().getProd().get(indeks).getKom().size();
    }

    @Override
    public AdapterKompletov.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_oblacila, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public AdapterKompletov(ApplicationMy app,Integer indeks, Activity ac) {
        this.ac = ac;
        this.app = app;
        this.indeks = indeks;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView druga;
        public TextView tretja;
        public TextView cetrta;
        public ImageView iv;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            druga = (TextView) v.findViewById(R.id.secondLine);
            tretja = (TextView) v.findViewById(R.id.thrirdline);
            cetrta = (TextView) v.findViewById(R.id.fourthline);
            iv = (ImageView)v.findViewById(R.id.icon);
        }
    }
}

