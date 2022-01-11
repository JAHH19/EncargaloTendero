package com.example.encargalo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapsHeatFragment extends Fragment {
    private ProgressDialog progressDialog;
    private ArrayList<LatLng> result;

    private String url;

    private static final String TAG = "MyActivity";

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            LatLng city = new LatLng(41.8964005,  -87.6610031);

            googleMap.addMarker(new MarkerOptions().position(city).title("Huancayo"));
            CameraUpdate panToOrigin = CameraUpdateFactory.newLatLng(city);
            googleMap.moveCamera(panToOrigin);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 400, null);

            addItemsHeat(googleMap);


        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_maps_heat, container, false);
        result = new ArrayList<>();
        result.clear();


        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String id = preferences.getString("idtienda", "empty");
        Log.i(TAG, "MyClass.getView() — get item number " + id);
        url="http://192.168.1.125:2020/APIS/tienda/consultarubicaciones.php?idtienda=1";

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Espere por favor");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
      if(hidden){
            FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
            mFragmentTransaction.replace(R.id.nav_host_fragment_container, this);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();

            super.onHiddenChanged(hidden);
        }

    }

    private  void addItemsHeat(final GoogleMap map) {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        double lat = object.getDouble("latitud");
                        double lng = object.getDouble("longitud");

                        result.add(new LatLng(lat, lng));

                        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                                .data(result)
                                .build();

                        // Add a tile overlay to the map, using the heat map tile provider.
                        TileOverlay overlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));

                    }
                } catch (Exception e) {

                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        progressDialog.dismiss();
    }




}