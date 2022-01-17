package com.example.encargalo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.encargalo.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InicioMenuActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    public String usuario="0",nombre,appaterno,apmaterno,tienda,idtienda="0";
    private AppBarConfiguration mAppBarConfiguration;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciomenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        usuario = getIntent().getStringExtra("usuario");
        nombre = getIntent().getStringExtra("nombre");
        appaterno = getIntent().getStringExtra("appaterno");
        apmaterno = getIntent().getStringExtra("apmaterno");
        tienda = getIntent().getStringExtra("tienda");

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_finicio,
                R.id.nav_fmyperfil,
                R.id.nav_fmypedidos,
                R.id.nav_fmyproductos,
                R.id.nav_fmyestadisticas,
                R.id.nav_fmynotificaciones,
                R.id.nav_frepartidor,
                R.id.nav_ajustes,
                R.id.nav_calificacion,
                R.id.nav_fgen_codigo)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setItemIconTintList(null);

        View headView = navigationView.getHeaderView(0);
       TextView tusuario = headView.findViewById(R.id.navhusuario);
       TextView ttienda = headView.findViewById(R.id.navhtienda);
      tusuario.setText(appaterno+" "+apmaterno+" "+nombre);

        ttienda.setText(tienda);
        Idtienda(usuario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.iniciomenu, menu);
        Idtienda(usuario);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Idtienda(usuario);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    public void Idtienda(String user){
        String URL = Valores.getIP_SERVER()+"/APIS/tienda/Consultatendero.php?idusuario="+user;
        request = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Ha ocurrido un error"+error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("consulta");
        JSONObject jsonObject = null;
        try {
            jsonObject = json.getJSONObject(0);
            idtienda = jsonObject.getString("idtienda");
            Toast.makeText(this,"idtienda : "+idtienda,Toast.LENGTH_SHORT).show();

            SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("usuario",usuario);
            editor.putString("nombre",nombre);
            editor.putString("appaterno",appaterno);
            editor.putString("apmaterno",apmaterno);
            editor.putString("tienda",tienda);
            editor.putString("idtienda",jsonObject.getString("idtienda"));
            editor.commit();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}