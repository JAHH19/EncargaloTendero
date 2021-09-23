package com.example.encargalo.Adapatadores;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.encargalo.detalle_pedido;
import com.example.encargalo.Items.itemSolicitados;
import com.example.encargalo.R;

import java.util.List;

public class AdapterSolicitados extends RecyclerView.Adapter<AdapterSolicitados.RecyclerHolder> {
    private List<itemSolicitados> items;
    private List<itemSolicitados> originalItems;

    public AdapterSolicitados(List<itemSolicitados> items) {
        this.items = items;
    }

    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsolicitudfrag,parent,false);
        return new RecyclerHolder(view);
    }

    public void onBindViewHolder(@NonNull final RecyclerHolder holder, int position) {
        final String idped,nom,fec,hor,imp;
        final itemSolicitados item = (itemSolicitados) items.get(position);
        idped=item.getIdpedidocli();
        nom=item.getnombrecli();
        fec=item.getfecha();
        hor=item.gethora();
        imp=item.getimporte();
        holder.txnom.setText(item.getnombrecli());
        holder.txfech.setText(item.getfecha());
        holder.txhor.setText(item.gethora());
        holder.tximpo.setText(item.getimporte());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), detalle_pedido.class);
                intent.putExtra("idpedido",idped);
                intent.putExtra("nombrecli",nom);
                intent.putExtra("fechaped",fec);
                intent.putExtra("horaped",hor);
                intent.putExtra("impoped",imp);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView txnom, txfech, txhor, tximpo;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            txnom = itemView.findViewById(R.id.txtnomcli);
            txfech = itemView.findViewById(R.id.txtfecha);
            txhor = itemView.findViewById(R.id.txthora);
            tximpo = itemView.findViewById(R.id.tximporte);
        }
    }
}