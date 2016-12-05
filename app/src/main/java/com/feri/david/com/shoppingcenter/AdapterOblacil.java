package com.feri.david.com.shoppingcenter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.DataAll;
import com.example.Prodajalna;

/**
 * Created by DavidPC on 13.3.2016.
 */
public class AdapterOblacil extends RecyclerView.Adapter<AdapterOblacil.ViewHolder>{
    public static final String PARAMETER_POSITION = "POSITION_TRGOVINA";
    private DataAll mDataset;
    Activity ac;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView txtFooter;
        public LinearLayout lin;
        public ImageView iv;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            iv = (ImageView)v.findViewById(R.id.icon);
            lin = (LinearLayout) v.findViewById(R.id.vsi);
        }
    }

    public AdapterOblacil(DataAll myDataset, Activity ac) {
        this.ac = ac;
        mDataset = myDataset;
    }

    @Override
    public AdapterOblacil.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Prodajalna trenutni = mDataset.getProd(position);
        final String name = trenutni.getNaziv();
        holder.txtHeader.setText(trenutni.getNaziv());
        if (DataAll.picture(trenutni.getNaziv()) == 1)
            holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.zara));
        if (DataAll.picture(trenutni.getNaziv()) == 2)
            holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.hm));
        if (DataAll.picture(trenutni.getNaziv()) == 3)
            holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.ca));
        if (DataAll.picture(trenutni.getNaziv()) == 4)
            holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.tak));
        if (DataAll.picture(trenutni.getNaziv()) == 5)
            holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.no_image));
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dva = new Intent(ac, ActivityListOblacil.class);
                dva.putExtra(PARAMETER_POSITION,position);
                ac.startActivity(dva);
                System.out.println(name);
            }
        });

        holder.txtFooter.setText("Lokacija: " + mDataset.getProd(position).getLokacija().getNaslov() + " " + mDataset.getProd(position).getLokacija().getHisna());
    }

    @Override
    public int getItemCount() {
        return mDataset.getProd().size();
    }

}
