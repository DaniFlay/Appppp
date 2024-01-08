package com.example.aplicacioncolegio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aplicacioncolegio.Permiso;
import com.example.aplicacioncolegio.R;

import java.util.List;

public class AdaptadorPermisos extends BaseAdapter {

    Context context;
    List<Permiso> permisos;

    public AdaptadorPermisos(Context context, List<Permiso> permisos) {
        this.context = context;
        this.permisos = permisos;
    }

    public AdaptadorPermisos() {
    }

    @Override
    public int getCount() {
        return permisos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView nombre;
        TextView estado;
        Permiso p= permisos.get(position);

        if(convertView==null)
            convertView= LayoutInflater.from(context).inflate(R.layout.permisos_list, null);
        nombre= convertView.findViewById(R.id.permiso_nombre);
        estado= convertView.findViewById(R.id.permiso_estado);

       // nombre.setText(p.getNombre());
        estado.setText(p.getEstado());

        return convertView;
    }
}
