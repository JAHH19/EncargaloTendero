package com.example.encargalo.Adapatadores;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.encargalo.detalle_pedido;
import com.example.encargalo.Items.itemHistorial;
import com.example.encargalo.R;

import java.util.List;
public class AdapterHistorial extends RecyclerView.Adapter<AdapterHistorial.RecyclerHolder> {
    private List<itemHistorial> items;
    private List<itemHistorial> originalItems;

    public AdapterHistorial(List<itemHistorial> items) {
        this.items = items;
    }

    public AdapterHistorial.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhistorial,parent,false);
        return new AdapterHistorial.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterHistorial.RecyclerHolder holder, int position) {
        final String idpedi,nomb,fech,hora,impo,estad;
        final itemHistorial item = (itemHistorial) items.get(position);
        idpedi=item.getIdpedidocli();
        nomb=item.getnombrecli();
        fech=item.getfecha();
        hora=item.gethora();
        impo=item.getimporte();
        estad=item.getestado();
        holder.txnomb.setText(item.getnombrecli());
        holder.txfecha.setText(item.getfecha());
        holder.txhora.setText(item.gethora());
        holder.tximpor.setText(item.getimporte());
        holder.txesta.setText(item.getestado());
    }

    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView txnomb, txfecha, txhora, tximpor, txesta;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            txnomb = itemView.findViewById(R.id.txtnomcli);
            txfecha = itemView.findViewById(R.id.txtfecha);
            txhora = itemView.findViewById(R.id.txthora);
            tximpor = itemView.findViewById(R.id.tximporte);
            txesta = itemView.findViewById(R.id.txestado);
        }
    }

}
