package com.example.tp_3_grupo_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import SQLite.BD_Info;
import SQLite.DbHelper;
import model.Users;

public class Registrarse extends AppCompatActivity {

    private TextView nameText;
    private TextView mailText;
    private TextView passwordText;
    private TextView confirmPasswordText;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameText = (TextView) findViewById(R.id.etNombre);
        mailText = (TextView) findViewById(R.id.etMail);
        passwordText = (TextView) findViewById(R.id.etNuevaContrasenia);
        confirmPasswordText = (TextView) findViewById(R.id.etRepetirContrasenia);
        setContentView(R.layout.activity_registrarse);
        dbHelper = new DbHelper(getApplicationContext(), BD_Info.usersTable,null,1);
    }

    public void Aceptar(View view){
        boolean validated = true;

        if(passwordText.getText().toString() != confirmPasswordText.getText().toString())
            validated = false;

        Users user = new Users(0,nameText.getText().toString(),mailText.getText().toString(),passwordText.getText().toString());

        user = dbHelper.getUser(user);

        if(user.getId() != 0)
            validated = false;

        if(validated) {
            dbHelper.createUser(user);
            Intent aceptar = new Intent(this, MainActivity.class);
            startActivity(aceptar);
        }
    }
}