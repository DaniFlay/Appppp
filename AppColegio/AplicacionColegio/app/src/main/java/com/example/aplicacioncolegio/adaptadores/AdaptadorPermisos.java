package com.example.aplicacioncolegio.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacioncolegio.R;
import com.example.aplicacioncolegio.encapsuladores.EncapsuladorPermisos;

import java.util.List;

public class AdaptadorPermisos extends RecyclerView.Adapter<AdaptadorPermisos.ViewHolder> {

    Context context;
    List<?> permisos;
    int layout_id;
    View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }

    public AdaptadorPermisos(Context context, List<?> permisos, int layout_id) {
        this.context = context;
        this.permisos = permisos;
        this.layout_id= layout_id;
    }



    @NonNull
    @Override
    public AdaptadorPermisos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacion,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPermisos.ViewHolder holder, int position) {
        EncapsuladorPermisos e= (EncapsuladorPermisos) permisos.get(position);
        holder.representacionElementos(e);
    }

    @Override
    public int getItemCount() {
        return permisos.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView razon, estado;
        public ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            razon= itemView.findViewById(R.id.razon);
            estado= itemView.findViewById(R.id.estado);
            imagen= itemView.findViewById(R.id.imagenPermiso);
        }
        public void representacionElementos(EncapsuladorPermisos e){
            razon.setText(e.getRazon());
            estado.setText(e.getEstado());
            imagen.setImageResource(e.getImagen());
        }
    }


}
