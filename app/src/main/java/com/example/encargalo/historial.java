package com.example.encargalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.encargalo.Adapatadores.AdapterHistorial;
import com.example.encargalo.Items.itemHistorial;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class historial extends AppCompatActivity implements  Response.Listener<JSONObject>, Response.ErrorListener  {
    public String usuario,nombre,appaterno,apmaterno,tienda,idtienda;
    private RecyclerView rvLista;
    private AdapterHistorial adapter;
    private List<itemHistorial> items;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        SharedPreferences sharedpref = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        usuario = sharedpref.getString("usuario","non");
        nombre = sharedpref.getString("nombre","non");
        appaterno = sharedpref.getString("appaterno","non");
        apmaterno = sharedpref.getString("apmaterno","non");
        tienda = sharedpref.getString("tienda","non");
        idtienda = sharedpref.getString("idtienda","non");
        initViews();
        initValues();
    }

    private void initViews(){
        rvLista = findViewById(R.id.lista_his);
    }

    private void initValues(){
        String URL = "http://"+Valores.getIP_SERVER()+"/APIS/tienda/listarsolicitudesAceptadasTendero.php?idtienda="+idtienda;
        request = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null, this,this);
        request.add(jsonObjectRequest);
    }

    private List<itemHistorial> getItems(JSONObject response){
        List<itemHistorial> itemLista = new ArrayList<>();
        JSONArray json = response.optJSONArray("consulta");
        try {
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                itemLista.add(new  itemHistorial(
                        jsonObject.getString("idpedidoAc"),
                        jsonObject.getString("appaternocliAc")+" "+jsonObject.getString("apmaternocliAc")+" "+jsonObject.getString("nombrecliAc"),
                        jsonObject.getString("importetotalAc"),
                        jsonObject.getString("horasoliAc"),
                        jsonObject.getString("fechasoliAc"),
                        jsonObject.getString("nombreestado")));
            }
        }catch (JSONException e){
            Toast.makeText(this,"Ha ocurrido un error"+e.toString(),Toast.LENGTH_SHORT).show();
        }
        return itemLista;
    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }



    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Ha ocurrido un error"+error.toString(),Toast.LENGTH_SHORT).show();
    }


    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);
        items = getItems(response);
        adapter = new AdapterHistorial(items);
        rvLista.setAdapter(adapter);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}