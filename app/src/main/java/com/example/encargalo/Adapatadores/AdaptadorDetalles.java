package com.example.encargalo.Adapatadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.encargalo.Items.ItemPro;
import com.example.encargalo.Items.itemdetallepedido;
import com.example.encargalo.R;

import java.util.List;

public class AdaptadorDetalles extends RecyclerView.Adapter<AdaptadorDetalles.RecyclerHolder> {



    private List<itemdetallepedido> items;
    private List<itemdetallepedido> originalItems;

    public AdaptadorDetalles(List<itemdetallepedido> items) {
        this.items = items;
    }
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdetallefrag,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        itemdetallepedido item = (itemdetallepedido) items.get(position);
        holder.txdescripro.setText(item.getDescpro());
        holder.txtcantidad.setText(item.getCantidad());
        holder.txtpreciounit.setText(item.getPreciouni());
        holder.txtsubtotal.setText(item.getSubtotal());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView txdescripro, txtcantidad, txtpreciounit, txtsubtotal;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);

            txdescripro = itemView.findViewById(R.id.txtdescppro);
            txtcantidad = itemView.findViewById(R.id.txtCantPro);
            txtpreciounit = itemView.findViewById(R.id.txtPreciuni);
            txtsubtotal = itemView.findViewById(R.id.txtsubtotal);
        }
    }
}
