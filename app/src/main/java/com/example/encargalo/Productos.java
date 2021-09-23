package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.encargalo.Adapatadores.Adaptador;
import com.example.encargalo.Items.ItemPro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Productos extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    public String usuario,nombre,appaterno,apmaterno,tienda,cate;
    private RecyclerView rvLista;
    private Adaptador adapter;
    private List<ItemPro> items;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        cate = getIntent().getStringExtra("cate");
        SharedPreferences sharedpref = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        usuario = sharedpref.getString("usuario","non");
        nombre = sharedpref.getString("nombre","non");
        appaterno = sharedpref.getString("appaterno","non");
        apmaterno = sharedpref.getString("apmaterno","non");
        tienda = sharedpref.getString("tienda","non");

        initViews();
        initValues();
    }

    private void initViews(){
        rvLista = findViewById(R.id.lista_pro);
    }

    private void initValues(){
        String URL = "http://"+Valores.getIP_SERVER()+"/APIS/tienda/MostrarProductosUsuCat.php?idusuario="+usuario+"&idcategoria="+cate;
        request = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null, this,this);
        request.add(jsonObjectRequest);
    }

    private List<ItemPro> getItems(JSONObject response){
        List<ItemPro> itemLists = new ArrayList<>();
        JSONArray json = response.optJSONArray("consulta");
        try {
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                itemLists.add(new ItemPro(
                        jsonObject.getString("idtienda") ,
                        jsonObject.getString("nombreproducto"),
                        jsonObject.getString("precio"),
                        jsonObject.getString("promocion")));
            }
        }catch (JSONException e){

        }
        return itemLists;
    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }



    public void onErrorResponse(VolleyError error) {

    }


    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);
        items = getItems(response);
        adapter = new Adaptador(items);
        rvLista.setAdapter(adapter);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}