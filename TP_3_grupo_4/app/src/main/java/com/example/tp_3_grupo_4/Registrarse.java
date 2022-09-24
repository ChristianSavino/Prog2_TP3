package com.example.tp_3_grupo_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Registrarse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
    }

    public void Aceptar(View view){
        Intent aceptar = new Intent(this, MainActivity.class);
        startActivity(aceptar);
    }
}