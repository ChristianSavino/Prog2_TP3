package com.example.tp_3_grupo_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import SQLite.BD_Info;
import SQLite.DbHelper;

public class MainActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView passwordText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameText = (TextView) findViewById(R.id.etUsuario);
        passwordText = (TextView) findViewById(R.id.etNuevaContrasenia);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this, BD_Info.usersTable,null,1);
    }

    public void Ingresar(View view){
        Cursor c = dbHelper.consultarUserPass(nameText.toString(), passwordText.toString());

        if(((Cursor) c).getCount()>0){
            //a pagina principal
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
            /*Intent ingresar = new Intent(this, Registrarse.class);
            startActivity(ingresar);*/
        }else{
            Toast.makeText(this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
        nameText.setText("");
        passwordText.setText("");
        nameText.findFocus();
    }

    public void Registrarse(View view){
        Intent registrar = new Intent(this, Registrarse.class);
        startActivity(registrar);
    }
}