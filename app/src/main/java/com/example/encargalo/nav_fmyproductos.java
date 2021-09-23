package com.example.encargalo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_fmyproductos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_fmyproductos extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public nav_fmyproductos() {

    }

    public static nav_fmyproductos newInstance(String param1, String param2) {
        nav_fmyproductos fragment = new nav_fmyproductos();
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
        View v = inflater.inflate(R.layout.fragment_nav_fmyproductos, container, false);
        FloatingActionButton btn_flat = (FloatingActionButton)v.findViewById(R.id.btn_flat);
        btn_flat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), reg_producto.class);
                in.putExtra("some","same data");
                startActivity(in);
            }
        });
        Button b1 = v.findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","1");
                startActivity(in);
            }
        });
        Button b2 = v.findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","2");
                startActivity(in);
            }
        });
        Button b3 = v.findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","3");
                startActivity(in);
            }
        });
        Button b4 = v.findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","4");
                startActivity(in);
            }
        });
        Button b5 = v.findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","5");
                startActivity(in);
            }
        });
        Button b6 = v.findViewById(R.id.button6);
        b6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","6");
                startActivity(in);
            }
        });
        Button b7 = v.findViewById(R.id.button7);
        b7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","7");
                startActivity(in);
            }
        });
        Button b8 = v.findViewById(R.id.button8);
        b8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","8");
                startActivity(in);
            }
        });
        Button b9 = v.findViewById(R.id.button9);
        b9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","9");
                startActivity(in);
            }
        });
        Button b10 = v.findViewById(R.id.button10);
        b10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","10");
                startActivity(in);
            }
        });
        Button b11 = v.findViewById(R.id.button11);
        b11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","11");
                startActivity(in);
            }
        });
        Button b12 = v.findViewById(R.id.button12);
        b12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","12");
                startActivity(in);
            }
        });
        Button b13 = v.findViewById(R.id.button13);
        b13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","13");
                startActivity(in);
            }
        });
        Button b14 = v.findViewById(R.id.button14);
        b14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","14");
                startActivity(in);
            }
        });
        Button b15 = v.findViewById(R.id.button15);
        b15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","15");
                startActivity(in);
            }
        });
        Button b16 = v.findViewById(R.id.button16);
        b16.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","16");
                startActivity(in);
            }
        });
        Button b17 = v.findViewById(R.id.button14);
        b17.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), Productos.class);
                in.putExtra("cate","17");
                startActivity(in);
            }
        });
        return v;
    }
}