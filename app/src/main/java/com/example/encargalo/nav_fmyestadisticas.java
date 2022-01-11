package com.example.encargalo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class nav_fmyestadisticas extends Fragment {


    MapsHeatFragment mapsHeatFragment = new MapsHeatFragment();

    Button btnmapheat;
    TextView codtienda;




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {



            View view= inflater.inflate(R.layout.fragment_nav_fmyestadisticas, container, false);
            codtienda= view.findViewById(R.id.txtidtienda);
            btnmapheat= view.findViewById(R.id.btnmapacalor);

            btnmapheat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    createFragment(mapsHeatFragment);
                }
            });


            return view;
       }




    private void createFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, fragment)

                .commit();
    }
    private void showFragment(Fragment fragment){

        getActivity().getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commit();
    }
    private void hideFragment(Fragment fragment){

        getActivity().getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commit();
    }
}