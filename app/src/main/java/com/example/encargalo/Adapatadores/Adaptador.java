package com.example.encargalo.Adapatadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.encargalo.Items.ItemPro;
import com.example.encargalo.R;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.RecyclerHolder> {
    private List<ItemPro> items;
    private List<ItemPro> originalItems;

    public Adaptador(List<ItemPro> items) {
        this.items = items;
    }

    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemprofrag,parent,false);
        return new RecyclerHolder(view);
    }

    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        ItemPro item = (ItemPro) items.get(position);
        holder.txtnombre.setText(item.getNombrepro());
        holder.txtprecio.setText(item.getPrecio());
        holder.txtpromo.setText(item.getPromocion());
    }

    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView txtnombre, txtprecio, txtpromo;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            txtnombre = itemView.findViewById(R.id.rrNombre);
            txtprecio = itemView.findViewById(R.id.rrPrecio);
            txtpromo =itemView.findViewById(R.id.rrPromocion);
        }
    }
}
