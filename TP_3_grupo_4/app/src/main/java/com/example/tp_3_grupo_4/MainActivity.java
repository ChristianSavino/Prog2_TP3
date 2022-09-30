package com.example.tp_3_grupo_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import SQLite.DbHelper;

public class MainActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView passwordText;

    DbHelper dbHelper = new DbHelper(this, "BDP",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primaryDark)));

        nameText = (TextView) findViewById(R.id.etUsuario);
        passwordText = (TextView) findViewById(R.id.etNuevaContrasenia);
    }

    public void Ingresar(View view){
        dbHelper.openDB();
        String nombre = nameText.getText().toString();
        String password = passwordText.getText().toString();

        if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Se deben completar todos los campos", Toast.LENGTH_SHORT).show();
        }else {
            Boolean check = dbHelper.consultarUserPass(nombre, password);
            if (check == true) {
                dbHelper.closeDB();
                //a pagina principal !!!!!!! (cambiar cuando este creada)
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent ingresar = new Intent(this, Registrarse.class);
                startActivity(ingresar);
            } else {
                Toast.makeText(this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
            nameText.setText("");
            passwordText.setText("");
            nameText.findFocus();
        }
    }

    public void Registrarse(View view){
        Intent registrar = new Intent(this, Registrarse.class);
        startActivity(registrar);
    }
}