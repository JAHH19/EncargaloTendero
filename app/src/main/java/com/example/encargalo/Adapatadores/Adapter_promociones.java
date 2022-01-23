package com.example.encargalo.Adapatadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.encargalo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_promociones extends RecyclerView.Adapter<Adapter_promociones.ViewHolderDatos> {
    private ArrayList<String> datos1,datos2,datos3,datos4,datos5,datos6;

    public Adapter_promociones(ArrayList<String> adatos1, ArrayList<String> adatos2, ArrayList<String> adatos3, ArrayList<String> adatos4, ArrayList<String> adatos5, ArrayList<String> adatos6){
        this.datos1 = adatos1;
        this.datos2 = adatos2;
        this.datos3 = adatos3;
        this.datos4 = adatos4;
        this.datos5 = adatos5;
        this.datos6 = adatos6;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_promociones,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_promociones.ViewHolderDatos holder, int position) {
        holder.ponerDatos(datos1.get(position),datos2.get(position),datos3.get(position), datos4.get(position),datos5.get(position),datos6.get(position));
    }

    @Override
    public int getItemCount() {
        return datos1.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView txt1,txt2,txt3,txt4,txt5;
        ImageView img;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.item_tipo_promocion);
            //txt2 = itemView.findViewById(R.id.item_stock);
            txt3 = itemView.findViewById(R.id.item_descripcion);
            //txt4 = itemView.findViewById(R.id.item_f_inicio);
            //txt5 = itemView.findViewById(R.id.item_f_fin);
            img = itemView.findViewById(R.id.item_img_);
        }
        public void ponerDatos(String a1,String a2,String a3 , String a4,String a5,String a6){
            txt1.setText(a1);
            //txt2.setText(a2);
            txt3.setText(a3);
            //txt4.setText(a4);
            //txt5.setText(a5);

            Picasso.get().load(a6).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).fit().centerCrop().into(img);
        }
    }
}