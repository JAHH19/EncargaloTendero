package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.encargalo.Adapatadores.AdaptadorDetalles;
import com.example.encargalo.Adapatadores.AdapterSolicitados;
import com.example.encargalo.Items.itemdetallepedido;
import com.example.encargalo.Valores;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class detalle_pedido extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    String idped,nom,fec,hor,imp;
    TextView nombre,fecha,hora,importe;
    Button btnaceptarped,btndenegarped;
    public String descrppro,cantidad,preciouni,subtotalpro,nombreclien,direccioncli;
    TextView nombrecli,direcccli;
    private RecyclerView rvLista;
    private AdaptadorDetalles adapter;
    private List<itemdetallepedido> items;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        idped = getIntent().getStringExtra("idpedido");

        nom = getIntent().getStringExtra("nombrecli");
        fec = getIntent().getStringExtra("fechaped");
        hor = getIntent().getStringExtra("horaped");
        imp = getIntent().getStringExtra("impoped");

        nombre = (TextView)findViewById(R.id.txtnomcli);
        fecha = (TextView)findViewById(R.id.txtfecha);
        hora = (TextView)findViewById(R.id.txthora);
        importe = (TextView)findViewById(R.id.tximporte);

        btnaceptarped = findViewById(R.id.btn_acep);
        btndenegarped = findViewById(R.id.btn_denegar);

        nombre.setText(nom);
        fecha.setText(fec);
        hora.setText(hor);
        importe.setText(imp);

        btnaceptarped.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                actualizar_pedido("2");
                iraSolicitados(view);
            }
        });
        btndenegarped.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                actualizar_pedido("3");
                iraSolicitados(view);
            }
        });
        initViews();
        initValues();

    }
    private void initViews(){
        rvLista = findViewById(R.id.lista_deta);
    }

    private void initValues(){

        String URL = Valores.getIP_SERVER()+"/APIS/tienda/Detallepedidotienda.php?idpedido="+idped;
        request = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null, this,this);
        request.add(jsonObjectRequest);
    }
    private List<itemdetallepedido> getItems(JSONObject response){
        List<itemdetallepedido> itemLists = new ArrayList<>();
        JSONArray json = response.optJSONArray("consulta");
        try {
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                itemLists.add(new itemdetallepedido(
                        jsonObject.getString("descrippro"),
                        jsonObject.getString("Cantidadpro") ,
                        jsonObject.getString("PrecioUniPro"),
                        jsonObject.getString("Subtotalpro")));
            }
        }catch (JSONException e){

        }
        return itemLists;
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Ha ocurrido un error"+error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);
        items = getItems(response);
        adapter = new AdaptadorDetalles(items);
        rvLista.setAdapter(adapter);
    }

    private void actualizar_pedido(String estado){
        final String est = estado;
        String URL = Valores.getIP_SERVER()+"/APIS/tienda/AceptarRechazarPedidotendero.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "PEDIDO ACTUALIZADO...", Toast.LENGTH_SHORT).show();

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
                parametros.put("idpedido",idped);
                parametros.put("estadopedido",est);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void iraSolicitados (View View){
        Intent i = new Intent(this, solicitados.class);
        startActivity(i);
    }
}