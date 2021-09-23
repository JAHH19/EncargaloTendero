
package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VerificarActivity extends AppCompatActivity {

    Dialog myDialog;
    String sexo= "";
    String foto= "";
    String descripcion="";
    String latitud= "0";
    String longitud= "0";
    String correo= "";
    EditText usuario,contraseña1,contraseña2;

    String nombres,appaterno,apmaterno,identidad,celular,tienda,ruc,rubro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar);
        myDialog = new Dialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Crear Cuenta");
        setSupportActionBar(toolbar);
    }
    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("apPaterno",appaterno);
                parametros.put("apMaterno",apmaterno);
                parametros.put("nombres",nombres);
                parametros.put("nidentificacion",identidad);
                parametros.put("celular",celular);
                parametros.put("sexo",sexo);
                parametros.put("email",correo);
                parametros.put("foto",foto);
                parametros.put("descripcion",descripcion);
                parametros.put("longitud",longitud);
                parametros.put("latitud",latitud);
                parametros.put("ruc",ruc);
                parametros.put("nombreTienda",tienda);
                parametros.put("idRubro",rubro);
                parametros.put("usuario",usuario.getText().toString());
                parametros.put("password",contraseña1.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void ShowDialog(View v){
        Button btnAceptar,btnCancelar;
        myDialog.setContentView(R.layout.pass);
        btnAceptar = (Button) myDialog.findViewById(R.id.btn_Aceptar);
        btnCancelar = (Button) myDialog.findViewById(R.id.bnt_Cancelar);
        usuario = (EditText) myDialog.findViewById(R.id.edtUsuario);
        contraseña1 = (EditText) myDialog.findViewById(R.id.edtpass);
        contraseña2 = (EditText) myDialog.findViewById(R.id.edtrepetirpass);
        appaterno = getIntent().getStringExtra("appaterno");
        apmaterno = getIntent().getStringExtra("apmaterno");
        nombres = getIntent().getStringExtra("nombres");
        identidad = getIntent().getStringExtra("identidad");
        celular = getIntent().getStringExtra("celular");
        tienda = getIntent().getStringExtra("tienda");
        ruc = getIntent().getStringExtra("ruc");
        rubro = getIntent().getStringExtra("rubro");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Crear Cuenta");
        setSupportActionBar(toolbar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ejecutarServicio("http://"+Valores.getIP_SERVER()+"/APIS/tienda/registrartienda.php");
                iraMenu(v);
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void iraMenu (View View){
        Intent i = new Intent(this, InicioMenuActivity.class);
        i.putExtra("usuario",usuario.getText().toString());
        i.putExtra("nombre",nombres);
        i.putExtra("appaterno",appaterno);
        i.putExtra("apmaterno",apmaterno);
        i.putExtra("tienda",tienda);
        startActivity(i);
    }

}