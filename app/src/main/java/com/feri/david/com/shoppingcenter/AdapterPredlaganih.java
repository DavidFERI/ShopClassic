package com.feri.david.com.shoppingcenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DataAll;
import com.example.PredlaganiKompleti;

import java.io.File;

/**
 * Created by DavidPC on 4.1.2017.
 */

public class AdapterPredlaganih extends RecyclerView.Adapter<AdapterPredlaganih.ViewHolder> {
    public static final String PARAMETER_POSITION_1 = "POSITION_PREDLAGAN";
    private ApplicationMy app;
    Activity ac;
    private Integer indeks;

    @Override////
    public void onBindViewHolder(final AdapterPredlaganih.ViewHolder holder, final int position1) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final PredlaganiKompleti trenutni = app.getAll().getPredlag().get(position1);
        final String name = trenutni.getNaziv();
        holder.txtHeader.setText(trenutni.getNaziv());
        holder.txtHeader.setWidth(300);
        if(trenutni.getSlika_uri()!=null)
        {
            File f=new File(trenutni.getSlika_uri());
            if(f.exists())
            {
                Bitmap image=getThumbnailBitmap(trenutni.getSlika_uri(),400);
                holder.iv.setBackgroundResource(R.drawable.obroba);
                holder.iv.setImageBitmap(image);
                holder.iv.setPadding(11,11,11,11);
            }
            else
            {
                holder.iv.setPadding(0,0,0,0);
                holder.iv.setBackgroundResource(R.color.siva);
                if (DataAll.picture1(trenutni.getNaziv()) == 1)
                    holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.zara));
                if (DataAll.picture1(trenutni.getNaziv()) == 2)
                    holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.other));
                if (DataAll.picture1(trenutni.getNaziv()) == 3)
                    holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.no_image));
            }

        }
        else
        {
            holder.iv.setPadding(0,0,0,0);
            holder.iv.setBackgroundResource(R.color.siva);
            if (DataAll.picture1(trenutni.getNaziv()) == 1)
                holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.zara));
            if (DataAll.picture1(trenutni.getNaziv()) == 2)
                holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.other));
            if (DataAll.picture1(trenutni.getNaziv()) == 3)
                holder.iv.setImageDrawable(this.  ac.getDrawable(R.drawable.no_image));
        }


        holder.iv.setOnTouchListener(new OnSwipeTouchListener(ac) {

            @Override
            public void onLongClick() {
                super.onLongClick();
                LayoutInflater inflater = ac.getLayoutInflater();
                View layout = inflater.inflate(R.layout.belezka_insta,
                        (ViewGroup) ac.findViewById(R.id.relativeLayout1));

                ImageView image = (ImageView) layout.findViewById(R.id.imageView1);
                if(trenutni.getSlika_uri() == null){
                    image.setImageResource(R.drawable.no_image);
                }else{
                    File f=new File(trenutni.getSlika_uri());
                    if(f.exists()){
                        Bitmap slikica=getThumbnailBitmap(trenutni.getSlika_uri(),600);
                        image.setImageBitmap(slikica);
                    }else{
                        image.setImageResource(R.drawable.no_image);
                    }
                }
                TextView text = (TextView) layout.findViewById(R.id.textView1);
                TextView text1 = (TextView) layout.findViewById(R.id.textView2);
                text.setText(trenutni.getNaziv());
                text1.setText(String.format("%.2f", trenutni.Skupaj_dobi(trenutni)) + "€");
                Toast toast = new Toast(ac);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();


            }

            @Override//popravi
            public void onClick() {
                super.onClick();
                Intent dva = new Intent(ac, Belezka_Podrobnosti.class);
                dva.putExtra(PARAMETER_POSITION_1,position1);;
                ac.startActivity(dva);
                System.out.println(name);
            }

        });



        holder.vseh.setOnTouchListener(new OnSwipeTouchListener(ac) {

            @Override
            public void onClick() {
                super.onClick();
                Intent dva = new Intent(ac, Belezka_Podrobnosti.class);
                dva.putExtra(PARAMETER_POSITION_1,position1);;
                ac.startActivity(dva);
                System.out.println(name);
            }

                /*@Override
                public void onDoubleClick() {
                    super.onDoubleClick();
                    Toast.makeText(ac, "Double", Toast.LENGTH_SHORT).show();
                }*/

            /*@Override
            public void onLongClick() {
                super.onLongClick();
                Toast.makeText(ac, "Long click!", Toast.LENGTH_SHORT).show();
            }*/

                /*@Override
                public void onSwipeUp() {
                    super.onSwipeUp();
                    // your swipe up here
                }*/

                /*@Override
                public void onSwipeDown() {
                    super.onSwipeDown();
                    // your swipe down here.
                }*/

                /*@Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    // your swipe left here.
                }*/

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ac);
                builder1.setMessage("Ali res želite izbrisati artikel: " + trenutni.getNaziv() + "?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final Animation animation = AnimationUtils.loadAnimation(ac, android.R.anim.slide_out_right);
                                holder.vseh.startAnimation(animation);
                                Handler handle = new Handler();
                                handle.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        app.getAll().odstrani_iz_predlaganih(position1);
                                        app.save();
                                        notifyDataSetChanged();
                                        animation.cancel();
                                    }
                                }, 400);
                                dialog.cancel();
                            }
                        }).setNegativeButton(
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

        holder.druga.setText("Stevilka: " + trenutni.getSak().getVelikost());
        holder.tretja.setText(app.getAll().getPredlag().get(position1).getPro().getLokacija().getNaslov() + " " + app.getAll().getPredlag().get(position1).getPro().getLokacija().getHisna());
        holder.cetrta.setText(String.format("%.2f", trenutni.Skupaj_dobi(trenutni)) + "€");
    }


    @Override
    public int getItemCount() {
        return app.getAll().getPredlag().size();
    }

    @Override
    public AdapterPredlaganih.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_bel, parent, false);
        // set the view's size, margins, paddings and layout parameters
        AdapterPredlaganih.ViewHolder vh = new AdapterPredlaganih.ViewHolder(v);
        return vh;
    }

    public AdapterPredlaganih(ApplicationMy app,Integer indeks, Activity ac) {
        this.ac = ac;
        this.app = app;
        this.indeks = indeks;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView druga;
        public TextView tretja;
        public TextView cetrta;
        public LinearLayout vseh;
        public ImageView iv;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            druga = (TextView) v.findViewById(R.id.secondLine);
            tretja = (TextView) v.findViewById(R.id.thrirdline);
            cetrta = (TextView) v.findViewById(R.id.fourthline);
            iv = (ImageView)v.findViewById(R.id.icon);
            vseh = (LinearLayout) v.findViewById(R.id.vse);
        }
    }

    public static class MyDialogFragment extends DialogFragment
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            return new AlertDialog.Builder(getActivity())
                    .setTitle("Dialog Title")
                    .setPositiveButton("OK", null)
                    .create();
        }
    }

    private Bitmap getThumbnailBitmap(String path, int thumbnailSize) {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            return null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / thumbnailSize;
        return BitmapFactory.decodeFile(path, opts);
    }
}
