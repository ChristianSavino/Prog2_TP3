package com.example.tp3_grupo4;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import SQLite.DbHelper;
import model.Users;

public class Registrarse extends AppCompatActivity {

    private TextView nameText;
    private TextView mailText;
    private TextView passwordText;
    private TextView confirmPasswordText;

    DbHelper dbHelper = new DbHelper(this, "BDP",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primaryDark)));

        nameText = (TextView) findViewById(R.id.etNombre);
        mailText = (TextView) findViewById(R.id.etMail);
        passwordText = (TextView) findViewById(R.id.etNuevaContrasenia);
        confirmPasswordText = (TextView) findViewById(R.id.etRepetirContrasenia);
    }

    public void Aceptar(View view) {
        dbHelper.openDB();
        String user = nameText.getText().toString();
        String password = passwordText.getText().toString();
        String mail = mailText.getText().toString();
        String ConfPassword = confirmPasswordText.getText().toString();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(ConfPassword)) {
            Toast.makeText(this, "Todos los campos deben ser completados", Toast.LENGTH_SHORT).show();
        }else{
        Users u = new Users();
        u.setId(0);
        u.setName(user);
        u.setEmail(mail);
        u.setPassword(password);

            if(password.equals(ConfPassword)){
                Boolean insertar = dbHelper.createUser(u);
                if (insertar == true) {
                    dbHelper.closeDB();
                    Toast.makeText(this, "Registro guardado con exito", Toast.LENGTH_SHORT).show();
                    Intent aceptar = new Intent(this, MainActivity.class);
                    startActivity(aceptar);
                } else {
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
        dbHelper.closeDB();
    }
}