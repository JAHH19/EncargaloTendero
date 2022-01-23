package com.example.encargalo;

import androidx.annotation.RawRes;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.encargalo.databinding.ActivityMapaCalorBinding;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MapaCalorActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapaCalorBinding binding;
    EditText et_mapa_calor_ini;
    EditText et_mapa_calor_fin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapaCalorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btn_mapa_busca_fecha = findViewById(R.id.btn_mapa_busca_fecha);
        et_mapa_calor_ini = findViewById(R.id.et_mapa_calor_ini);
        et_mapa_calor_fin = findViewById(R.id.et_mapa_calor_fin);
        btn_mapa_busca_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                llamarCordenadas(et_mapa_calor_ini.getText().toString(),et_mapa_calor_fin.getText().toString());
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //llamarCordenadas("2021-05-12","2021-05-12");
    }
    private void addHeatMap() {
        //antiguo
        List<LatLng> latLngs=new ArrayList<LatLng>();

        double pointX[]={-37.1886,-37.8361,-38.4034,-38.7597,-36.9672};
        double pointY[]={145.708,144.845,144.192,143.67,141.083};

        for (int i = 0 ; i < pointX.length; i++){
            latLngs.add(new LatLng(pointX[i],pointY[i]));
        };
        int[] colors = {Color.rgb(102, 225, 0),Color.rgb(255, 0, 0)};
        float[] startPoints = {0.2f, 1f};

        Gradient gradient = new Gradient(colors, startPoints);

        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().data(latLngs).gradient(gradient).build();
        TileOverlay overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }

    private List<LatLng> readItems(@RawRes int resource) throws JSONException {
        List<LatLng> result = new ArrayList<>();
        InputStream inputStream = getApplicationContext().getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            result.add(new LatLng(lat, lng));
        }
        return result;
    }


    private  void llamarCordenadas(String fecha_ini, String fecha_fin){
        String URL = Valores.getIP_SERVER()+"/APIS/tienda/ubicacionProductosMasVendidos.php?inicio="+fecha_ini+"&fin="+fecha_fin;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        JSONArray json = jsonObject.optJSONArray("consulta");

                        for (int j=0;j<json.length();j++){
                            JSONObject jsonObjectr = null;
                            jsonObjectr = json.getJSONObject(j);
                            String lat = jsonObjectr.getString("lat");
                            String lng = jsonObjectr.getString("lng");
                            LatLng sydney = new LatLng( Double.parseDouble(lat), Double.parseDouble(lng));
                            mMap.addMarker(new MarkerOptions().position(sydney).title(jsonObjectr.getString("nombre_zona")));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,20));

                            JSONObject jsonObject_2 = null;
                            jsonObject_2 = new JSONObject(jsonObjectr.getString("coordenadas"));
                            JSONArray json_2 = jsonObject_2.optJSONArray("consulta");

                            List<LatLng> latLngs=new ArrayList<LatLng>();

                            for (int i=0;i<json_2.length();i++){
                                JSONObject jsonObjectr_2 = null;
                                jsonObjectr_2 = json_2.getJSONObject(i);
                                String lat_2 = jsonObjectr_2.getString("lat");
                                String lng_2 = jsonObjectr_2.getString("lng");
                                latLngs.add(new LatLng(Double.parseDouble(lat_2),Double.parseDouble(lng_2)));
                            }

                            int[] colors = {Color.rgb(102, 225, 0),Color.rgb(255, 0, 0)};
                            float[] startPoints = {0.2f, 1f};

                            Gradient gradient = new Gradient(colors, startPoints);

                            HeatmapTileProvider provider = new HeatmapTileProvider.Builder().data(latLngs).gradient(gradient).build();
                            TileOverlay overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
                        }



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
}