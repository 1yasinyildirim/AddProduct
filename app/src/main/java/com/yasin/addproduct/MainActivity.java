package com.yasin.addproduct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button kamera,galeri,yukle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kamera = findViewById(R.id.camera);
        galeri = findViewById(R.id.galery);
        yukle = findViewById(R.id.uplo);

        kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camer = new Intent(MainActivity.this,Camera.class);
                startActivity(camer);
            }
        });

        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galer = new Intent(MainActivity.this,AddPro.class);
                startActivity(galer);
            }
        });

        yukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uplo = new Intent(MainActivity.this,Upload.class);
                startActivity(uplo);
            }
        });
    }
}