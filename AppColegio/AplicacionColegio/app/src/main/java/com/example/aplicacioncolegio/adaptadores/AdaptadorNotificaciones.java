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
import com.example.aplicacioncolegio.encapsuladores.EncapsuladorNotificaciones;


import java.util.List;

public class AdaptadorNotificaciones extends RecyclerView.Adapter<AdaptadorNotificaciones.ViewHolder> {
    Context context;
    List<?> lista;
    int layout_id;
    View.OnClickListener onClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener= onClickListener;
    }

    public AdaptadorNotificaciones(Context context, List<?> lista, int layout_id) {
        this.context = context;
        this.lista = lista;
        this.layout_id = layout_id;
    }

    @NonNull
    @Override
    public AdaptadorNotificaciones.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacion,parent,false);
        elemento.setOnClickListener(onClickListener);
        return new ViewHolder(elemento);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorNotificaciones.ViewHolder holder, int position) {
        EncapsuladorNotificaciones e= (EncapsuladorNotificaciones) lista.get(position);
        holder.representacionElementos(e);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre, fecha;
        public ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombre);
            fecha= itemView.findViewById(R.id.fecha);
            imagen= itemView.findViewById(R.id.imagenNotificacion);
        }
        public void representacionElementos(@NonNull EncapsuladorNotificaciones e){
            nombre.setText(e.getNombre());
            fecha.setText(e.getNombre());
            imagen.setImageResource(e.getImagen());
        }
    }

}
