package com.example.encargalo.Adapatadores;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.encargalo.R;

        import java.util.ArrayList;

public class Adapter_productomasvendido extends RecyclerView.Adapter<Adapter_productomasvendido.ViewHolderDatos> {
    private ArrayList<String> datos1,datos2,datos3;

    public Adapter_productomasvendido(ArrayList<String> adatos1, ArrayList<String> adatos2, ArrayList<String> adatos3){
        this.datos1 = adatos1;
        this.datos2 = adatos2;
        this.datos3 = adatos3;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_productosmasvendidos,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_productomasvendido.ViewHolderDatos holder, int position) {
        holder.ponerDatos(datos1.get(position),datos2.get(position),datos3.get(position));
    }

    @Override
    public int getItemCount() {
        return datos1.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView txt1,txt2,txt3;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.item_mvc_cantidad);
            txt2 = itemView.findViewById(R.id.item_mvc_nombre);
            txt3 = itemView.findViewById(R.id.item_mvc_categoria);
        }
        public void ponerDatos(String a1,String a2,String a3){
            txt1.setText(a1);
            txt2.setText(a2);
            txt3.setText(a3);
        }
    }
}