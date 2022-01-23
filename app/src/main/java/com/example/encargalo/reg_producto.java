package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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

public class reg_producto extends AppCompatActivity {

    Button btnguardar;
    EditText edtcate, edtnombrepro,edtpreciov,edtpromoci;
    public String usuario,nombre,appaterno,apmaterno,tienda,idtienda;
    String img_min="null",img_max="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_producto);

        btnguardar=(Button) findViewById(R.id.btnguardar);
        edtcate=(EditText) findViewById(R.id.edtcategoria);
        edtnombrepro=(EditText) findViewById(R.id.edtnombreproducto);
        edtpreciov=(EditText) findViewById(R.id.edtprecioventa);
        edtpromoci=(EditText) findViewById(R.id.edtpromocion);
        SharedPreferences sharedpref = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        usuario = sharedpref.getString("usuario","non");
        nombre = sharedpref.getString("nombre","non");
        appaterno = sharedpref.getString("appaterno","non");
        apmaterno = sharedpref.getString("apmaterno","non");
        tienda = sharedpref.getString("tienda","non");
        idtienda = sharedpref.getString("idtienda","non");

        btnguardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ejecutarServicio(idtienda);
            }
        });

    }
    private void ejecutarServicio(final String idtien){
        String URL=Valores.getIP_SERVER()+"/APIS/tienda/registroProducto.php";
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
                edtcate=(EditText) findViewById(R.id.edtcategoria);
                edtnombrepro=(EditText) findViewById(R.id.edtnombreproducto);
                edtpreciov=(EditText) findViewById(R.id.edtprecioventa);
                edtpromoci=(EditText) findViewById(R.id.edtpromocion);

                String desc = edtnombrepro.getText().toString();
                String pre = edtpreciov.getText().toString();
                String cat = edtcate.getText().toString();
                String pro = edtpromoci.getText().toString();

                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nombrepro",desc);
                parametros.put("precioventa",pre);
                parametros.put("imagen",img_min);
                parametros.put("imagen",img_max);
                parametros.put("categoria",cat);
                parametros.put("promocion",pro);
                parametros.put("idtienda",idtien);

               return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}