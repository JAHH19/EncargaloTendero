package com.example.encargalo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_fmyestadisticas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_fmyestadisticas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nav_fmyestadisticas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_fmyestadisticas.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_fmyestadisticas newInstance(String param1, String param2) {
        nav_fmyestadisticas fragment = new nav_fmyestadisticas();
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
        View view =inflater.inflate(R.layout.fragment_nav_fmyestadisticas, container, false);
        Button B1 =view.findViewById(R.id.btn_promocion_producto);
        B1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), PromocionarActivity.class);
                startActivity(in);
            }
        });
        Button btn_est_mapa_calor =view.findViewById(R.id.btn_est_mapacalor);
        btn_est_mapa_calor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(),MapaCalorActivity.class);
                startActivity(in);
            }
        });

        Button btn_est_masvendidocategoria =view.findViewById(R.id.btn_est_masvendidocategoria);
        btn_est_masvendidocategoria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(),MasVendidoCategoriaActivity.class);
                startActivity(in);
            }
        });

        return  view;
    }
}