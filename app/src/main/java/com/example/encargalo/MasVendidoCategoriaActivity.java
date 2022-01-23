package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.encargalo.Adapatadores.Adapter_productomasvendido;
import com.example.encargalo.Adapatadores.Adapter_promociones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MasVendidoCategoriaActivity extends AppCompatActivity {

    TextView pinifecha;
    TextView pfinfecha;
    RecyclerView recyclerView;
    ArrayList<String> dato1,dato2,dato3;
    String item_mvc_cantidad,item_mvc_nombre,item_mvc_categoria;
    RequestQueue queue2;
    EditText et_estadistica_inicio, et_estadistica_fin;
    EditText et_mvc_categoria;
    TextView tv_estadistica_rpt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_vendido_categoria);


        queue2 = Volley.newRequestQueue(getApplicationContext());
        et_mvc_categoria = findViewById(R.id.et_mvc_categoria);
        recyclerView  = findViewById(R.id.re_mvc);

        tv_estadistica_rpt2  = findViewById(R.id.tv_estadistica_rpt);

        et_estadistica_inicio = findViewById(R.id.et_estadistica_inicio);
        et_estadistica_fin = findViewById(R.id.et_estadistica_fin);

        pinifecha = findViewById(R.id.et_estadistica_inicio);
        pfinfecha = findViewById(R.id.et_estadistica_fin);

        Button btn_mvc_buscar = findViewById(R.id.btn_mvc_buscar);
        btn_mvc_buscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String et_mvc_categoria_s = et_mvc_categoria.getText().toString();
                cargarProductos(et_mvc_categoria_s, "0","vacio","vacio","vacio","vacio");
            }
        });

        final Spinner sp_estadistica_tipo2 = findViewById(R.id.sp_estadistica_tipo);
        String[] items = new String[]{"Más vendido","Mejor día","Mejor horario"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sp_estadistica_tipo2.setAdapter(adapter);

        Button btn_estadistica_buscar2 = findViewById(R.id.btn_estadistica_buscar);
        btn_estadistica_buscar2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences prefs = getSharedPreferences("credenciales", MODE_PRIVATE);
                String IdTienda = prefs.getString("idtienda", "vacio");

                String value_est_tipo = sp_estadistica_tipo2.getSelectedItem().toString();

                String s_et_estadistica_inicio = pinifecha.getText().toString();
                String s_et_estadistica_fin = pfinfecha.getText().toString();

                if(value_est_tipo == "mas_vendido"){
                    cargarProductos("vacio","1",s_et_estadistica_inicio,s_et_estadistica_fin,value_est_tipo, IdTienda);
                } else {
                    estadistica(s_et_estadistica_inicio,s_et_estadistica_fin,value_est_tipo,IdTienda);
                }
            }
        });


    }

    private void cargarProductos(String et_mvc_categoria_s, String is_cat, String start, String end, String tipo, String tienda)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dato1 = new ArrayList<String>();
        dato2 = new ArrayList<String>();
        dato3 = new ArrayList<String>();
        String url = "";
        if(is_cat == "0"){
            url = Valores.getIP_SERVER()+"/APIS/tienda/productosMasVendidosCategoria.php?id_tienda=1&_categoria="+et_mvc_categoria_s;
        } else {
            url = Valores.getIP_SERVER()+"/backend/public/api/estadistica?fecha_inicio="+start+"&fecha_fin="+end+"&tipo="+tipo+"&id_tienda="+tienda;
        }


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("consulta");

                    for (int i = 0 ; i < array.length() ; i++) {
                        JSONObject a = array.getJSONObject(i);

                        item_mvc_cantidad = a.getString("cont_products");
                        item_mvc_nombre = a.getString("nombreproducto");
                        item_mvc_categoria = a.getString("categoria");

                        dato1.add(item_mvc_cantidad);
                        dato2.add(item_mvc_nombre);
                        dato3.add(item_mvc_categoria);
                    }
                    Adapter_productomasvendido adapter_items = new Adapter_productomasvendido(dato1, dato2, dato3);
                    recyclerView.setAdapter(adapter_items);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue2.add(request);

    }

    private void estadistica(String start, String end, String tipo, String tienda)
    {

        String url = Valores.getIP_SERVER()+"/backend/public/api/estadistica?fecha_inicio="+start+"&fecha_fin="+end+"&tipo="+tipo+"&id_tienda="+tienda;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    if(tipo == "mejor_dia"){
                        tv_estadistica_rpt2.setText("Mejor dia para vender: "+response);
                    } else {
                        tv_estadistica_rpt2.setText("Mejor horario para vender: "+response);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Error...", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue2.add(request);

    }
    public void inifecha_boton(View v){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(MasVendidoCategoriaActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        DatePickerDialog dpd = new DatePickerDialog(MasVendidoCategoriaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int month_aux = month+1;
                String fecha = year + "-" + month_aux + "-" + dayOfMonth;
                pfinfecha.setText(fecha);
            }
        }, anio, mes, dia);
        dpd.show();

    }
}