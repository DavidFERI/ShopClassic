package com.feri.david.com.shoppingcenter;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pozdrav extends AppCompatActivity implements Animation.AnimationListener {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Animation animFadein;
    Animation all;
    Animation FadeIn;
    Animation Fadeout;
    Animation Move;
    Animation Blink;
    Animation Right;
    Animation FadeIn2;


    CircleImageView slikica;
    ImageView denar;
    ImageView Krizec;
    ImageView Roka;
    ImageView Icon;
    ImageView Klukica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_pozdrav);

        slikica = (CircleImageView) findViewById(R.id.obesalnik);
        denar = (ImageView) findViewById(R.id.denar);
        Krizec = (ImageView) findViewById(R.id.krzi);
        Roka = (ImageView) findViewById(R.id.rokica);
        Icon = (ImageView) findViewById(R.id.logo);
        Klukica = (ImageView) findViewById(R.id.klukica);
        slikica.setVisibility(View.VISIBLE);
        denar.setVisibility(View.INVISIBLE);
        Krizec.setVisibility(View.INVISIBLE);
        Roka.setVisibility(View.INVISIBLE);
        Klukica.setVisibility(View.INVISIBLE);

        //Nastavitev animacij
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);
        all = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        FadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        FadeIn2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        Move = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        Blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        Right = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_right);
        Fadeout = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        animFadein.setAnimationListener(this);
        all.setAnimationListener(this);
        FadeIn.setAnimationListener(this);
        FadeIn2.setAnimationListener(this);
        Move.setAnimationListener(this);
        Blink.setAnimationListener(this);
        Right.setAnimationListener(this);
        Fadeout.setAnimationListener(this);
        //zagon animacije

        Icon.setVisibility(View.VISIBLE);
        Icon.startAnimation(Blink);
        slikica.startAnimation(animFadein);
        denar.setVisibility(View.VISIBLE);
        denar.startAnimation(all);
        Krizec.setVisibility(View.VISIBLE);
        Krizec.startAnimation(FadeIn);
    }
    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for fade in animation
        if (animation == FadeIn) {
            denar.startAnimation(Move);
            Krizec.startAnimation(Fadeout);
            slikica.startAnimation(Fadeout);
        }

        if (animation == FadeIn2) {
            Intent intent = new Intent(Pozdrav.this,
                    ActivitySignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            Pozdrav.this.finish();
        }

        if (animation == Fadeout) {
            Roka.setVisibility(View.VISIBLE);
            Roka.startAnimation(FadeIn2);
            Klukica.setVisibility(View.VISIBLE);
            Klukica.startAnimation(all);
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub
        if (animation == Blink) {

        }

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }
}
