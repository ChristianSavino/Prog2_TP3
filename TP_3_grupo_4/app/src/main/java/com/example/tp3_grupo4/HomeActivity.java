package com.example.tp3_grupo4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp3_grupo4.databinding.ActivityHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp3_grupo4.databinding.ActivityMainBinding;

import java.util.ArrayList;

import SQLite.DbHelper;
import model.Parkings;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    private TextView Nombre;
    private TextView Mail;
    private NavigationView navView;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnclickAdd(view);
            }
        });
        navView = (NavigationView) findViewById(R.id.nav_view);
        Mail = (TextView) navView.getHeaderView(0).findViewById(R.id.tvMail);
        Nombre = (TextView) navView.getHeaderView(0).findViewById(R.id.tvNombre);
        gridView = (GridView) binding.getRoot().findViewById(R.id.parkingView);

        Mail.setText(getIntent().getStringExtra("email"));
        Nombre.setText(getIntent().getStringExtra("userName"));
        GetParkingsByUserId(getIntent().getStringExtra("IdUsuario"),null);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.primaryDark));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.cerrarSesion){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().clear().apply();
        Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
        Intent cerrar = new Intent(this, MainActivity.class);
        startActivity(cerrar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean areValuesValid(EditText Patente, EditText Tiempo){
        if(Patente.getText().toString().isEmpty() || Tiempo.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Debe completar ambos campos.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void OnclickAdd (View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Configura el titulo.
        alertDialogBuilder.setTitle("Registrar parqueo");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText Patente = new EditText(this);
        Patente.setHint("Nro patente");
        Patente.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(Patente);
        final EditText Tiempo = new EditText(this);
        Tiempo.setHint("Tiempo");
        Tiempo.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(Tiempo);

        // Configura el mensaje.
        alertDialogBuilder
                .setView(layout)
                .setCancelable(false)
                .setPositiveButton("Registrar",null)
                .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(areValuesValid(Patente, Tiempo))
                {
                    AddNewParking(Patente.getText().toString(), Tiempo.getText().toString(), getIntent().getStringExtra("IdUsuario"));
                    dialog.dismiss();
                }
            }
        });
    }

    private boolean AddNewParking(String patente, String tiempo, String IdUsuario) {
        try {
            DbHelper h = new DbHelper(HomeActivity.this,"BDP",null,1);
            Long fA = h.createParking(patente,tiempo,IdUsuario);
            GetParkingsByUserId(IdUsuario,h);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void GetParkingsByUserId(String idUsuario,DbHelper dbHelper) {
        if(dbHelper == null)
            dbHelper = new DbHelper(HomeActivity.this, "BDP", null, 1);
        ArrayList<Parkings> parkings = dbHelper.ParkingsById(idUsuario);

        ParkingAdapter adapter = new ParkingAdapter(this,parkings);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                DeleteParking(idUsuario,String.valueOf(parkings.get(position).getId()));
            }
        });
    }

    private void DeleteParking(String idUsuario, String idParking){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("¿Desea eliminar este parking?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbHelper h = new DbHelper(HomeActivity.this,"BDP",null,1);
                        h.DeleteParking(idParking);
                        Toast.makeText(getApplicationContext(),"Parking borrado con exito",Toast.LENGTH_LONG).show();
                        GetParkingsByUserId(idUsuario,h);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}