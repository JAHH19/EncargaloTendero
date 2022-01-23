package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.encargalo.Items.itemSolicitados;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromocionarActivity extends AppCompatActivity {

    TextView pinifecha;
    TextView pfinfecha;
    Spinner sp_promocion_producto;
    EditText stockTextNumber,imagenTextRuta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocionar);
        pinifecha = findViewById(R.id.printfechainiTextView);
        pfinfecha = findViewById(R.id.printfechafinTextView);

        final Spinner spinner1 = findViewById(R.id.spinner1);
        String[] items = new String[]{"Descuento (10%)", "Oferta (2x1)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner1.setAdapter(adapter);

        Button promocionarButton = findViewById(R.id.promocionarButton);
        stockTextNumber = findViewById(R.id.stockTextNumber);
        imagenTextRuta = findViewById(R.id.imagenTextRuta);


        promocionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value_producto = sp_promocion_producto.getSelectedItem().toString();
                String value_promocion = spinner1.getSelectedItem().toString();
                String value_pinifecha = pinifecha.getText().toString();
                String value_pfinfecha = pfinfecha.getText().toString();
                String value_stock = stockTextNumber.getText().toString();
                String value_imagen_ruta = imagenTextRuta.getText().toString();

                SharedPreferences prefs = getSharedPreferences("credenciales", MODE_PRIVATE);
                String IdTienda = prefs.getString("idtienda", "vacio");

                enviarPromocion(IdTienda, value_producto, value_promocion, value_pinifecha, value_pfinfecha, value_stock, value_imagen_ruta);
                Intent in = new Intent(getApplicationContext(), InicioMenuActivity.class);
                startActivity(in);
            }
        });

        SharedPreferences prefs = getSharedPreferences("data_user", MODE_PRIVATE);
        String value_user = prefs.getString("value_user", "vacio");
        cargarProductos(value_user);

    }

    private  void cargarProductos(String usu){
        String URL = Valores.getIP_SERVER()+"/APIS/tienda/ListarProductos.php?idusuario="+usu;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    List<String> items = new ArrayList<String>();

                    sp_promocion_producto = findViewById(R.id.sp_promocion_producto);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        JSONArray json = jsonObject.optJSONArray("consulta");

                        for (int i=0;i<json.length();i++){
                            JSONObject jsonObjectr = null;
                            jsonObjectr = json.getJSONObject(i);
                            items.add(jsonObjectr.getString("nombreproducto"));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
                        sp_promocion_producto.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
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
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void inifecha_boton(View v){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(PromocionarActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int month_aux = month+1;
                String fecha = year + "-" + month_aux + "-"  + dayOfMonth ;
                pinifecha.setText(fecha);
            }
        }, anio, mes, dia);
        dpd.show();
    }
    public void finfecha_boton(View v){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(PromocionarActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int month_aux = month+1;
                String fecha = year + "-" + month_aux + "-" + dayOfMonth;
                pfinfecha.setText(fecha);
            }
        }, anio, mes, dia);
        dpd.show();

    }

    private void enviarPromocion(String IdTienda, String producto, String tipo_promo, String f_ini, String f_fin, String stock, String imagen_ruta){
        String URL = Valores.getIP_SERVER()+"/APIS/tienda/crearPromocion.php?IdTienda="+IdTienda+"&producto="+producto+"&tipo_promo="+tipo_promo+
                "&f_ini="+f_ini+"&f_fin="+f_fin+"&stock="+stock+"&imagen_ruta="+imagen_ruta;
        Toast.makeText(getApplicationContext(), "asd:::"+URL, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Promocion registrada",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
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
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}