package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    EditText edtUsuario, edtPassword;
    Button btnlogin;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String token_app;
    public String usuario="0",nombre="0",appaterno="0",apmaterno="0",tienda="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnIngresar = findViewById(R.id.btn_ingresar);
        Button btnCrearCuenta = findViewById(R.id.btn_crear_cuenta);

        edtUsuario=findViewById(R.id.txtusuario);
        edtPassword=findViewById(R.id.txtclave);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUsuario.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Campo usuario se encuentra vacio.",Toast.LENGTH_SHORT).show();
                } else {
                    if(edtPassword.getText().toString().isEmpty())
                    {
                        Toast.makeText(LoginActivity.this,"Campo contraseña se encuentra vacio.",Toast.LENGTH_SHORT).show();
                    } else {
                        validarUsuario();
                    }
                }
            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                LoginActivity.this.startActivity(i);
            }
        });

        btnlogin =findViewById(R.id.btn_ingresar);

        SharedPreferences prefs = getSharedPreferences("data_user", MODE_PRIVATE);
        token_app = prefs.getString("token_app", "vacio");
    }
    private  void validarUsuario(){
        String URL = Valores.getIP_SERVER()+"/backend/public/api/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    try {
                        JSONObject json= new JSONObject(response);
                        String usuario = json.getString("usuario");
                        String nombre = json.getString("nombre");
                        String appaterno = json.getString("appaterno");
                        String apmaterno = json.getString("apmaterno");
                        String tienda = json.getString("tienda");

                        Intent i = new Intent(getApplicationContext(), InicioMenuActivity.class);
                        i.putExtra("usuario",usuario);
                        i.putExtra("nombre",nombre);
                        i.putExtra("appaterno",appaterno);
                        i.putExtra("apmaterno",apmaterno);
                        i.putExtra("tienda",tienda);
                        SharedPreferences.Editor editor = getSharedPreferences("data_user", MODE_PRIVATE).edit();
                        editor.putString("value_user", edtUsuario.getText().toString());
                        editor.apply();
                        startActivity(i);
                        Toast.makeText(LoginActivity.this,"¡Bienvenido!",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecta.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecta..",Toast.LENGTH_SHORT).show();
                //Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",edtUsuario.getText().toString());
                parametros.put("password",edtPassword.getText().toString());
                parametros.put("token_app",token_app);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("consulta");
        JSONObject jsonObject = null;
        try {
            jsonObject = json.getJSONObject(0);
            usuario = jsonObject.getString("usuario");
            nombre = jsonObject.getString("nombre");
            appaterno = jsonObject.getString("appaterno");
            apmaterno = jsonObject.getString("apmaterno");
            tienda = jsonObject.getString("tienda");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}