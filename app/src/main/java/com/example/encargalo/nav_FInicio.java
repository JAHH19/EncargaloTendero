package com.example.encargalo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.encargalo.Adapatadores.Adapter_promociones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_FInicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_FInicio extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<String> dato1,dato2,dato3,dato4,dato5,dato6;
    String item_tipo_promocion,item_stock,item_descripcion,item_f_fin,item_f_inicio,item_img_promo;
    RequestQueue queue2;

    public nav_FInicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_FInicio.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_FInicio newInstance(String param1, String param2) {
        nav_FInicio fragment = new nav_FInicio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav__f_inicio, container, false);

        queue2 = Volley.newRequestQueue(getContext());

        recyclerView  = view.findViewById(R.id.re);

        cargarProductos();
        return view;
    }

    private void cargarProductos()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dato1 = new ArrayList<String>();
        dato2 = new ArrayList<String>();
        dato3 = new ArrayList<String>();
        dato4 = new ArrayList<String>();
        dato5 = new ArrayList<String>();
        dato6 = new ArrayList<String>();

        SharedPreferences prefs = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String IdTienda = prefs.getString("idtienda", "vacio");
        String url = Valores.getIP_SERVER()+"/APIS/tienda/listarPromociones.php?IdTienda="+IdTienda;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("consulta");

                    for (int i = 0 ; i < array.length() ; i++) {
                        JSONObject a = array.getJSONObject(i);

                        item_tipo_promocion = a.getString("tipo_promocion");
                        item_stock = a.getString("stock");
                        item_descripcion = a.getString("descripcion");
                        item_f_inicio = a.getString("f_inicio");
                        item_f_fin = a.getString("f_fin");
                        item_img_promo = a.getString("img_promo");

                        dato1.add(item_tipo_promocion);
                        dato2.add(item_stock);
                        dato3.add(item_descripcion);
                        dato4.add(item_f_inicio);
                        dato5.add(item_f_fin);
                        dato6.add(item_img_promo);
                    }
                    Adapter_promociones adapter_items = new Adapter_promociones(dato1, dato2, dato3, dato4, dato5, dato6);
                    recyclerView.setAdapter(adapter_items);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue2.add(request);

    }
}