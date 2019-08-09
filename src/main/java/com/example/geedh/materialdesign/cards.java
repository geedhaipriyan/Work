package com.example.geedh.materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

public class cards extends AppCompatActivity {
    private Button b1,b2;
    private ImageView i1,i2;
    private CardView c1,c2;
    Toolbar t;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        t = findViewById(R.id.toolba);
        b1= findViewById(R.id.info_text);
        b2= findViewById(R.id.info);
        c1= findViewById(R.id.card_view);
        c2= findViewById(R.id.cardv);
        setSupportActionBar(t);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cards.this,MainActivity.class);
                startActivity(i);
                }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cards.this,MainActivity.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(cards.this,mein.class);
                startActivity(i1);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(cards.this,mein.class);
                startActivity(i1);
            }
        });
    }
}
