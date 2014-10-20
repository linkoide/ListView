package com.izv.android.coches;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by abraham on 8/10/14.
 */
public class AdaptadorLista extends ArrayAdapter {


    private Context contexto;
    private int recurso;
    private static LayoutInflater i;
    private ArrayList<Coche> lista;

    public AdaptadorLista(Context contexto, int recurso, ArrayList<Coche> lista) {
        super(contexto, recurso, lista);
        this.contexto = contexto;
        this.recurso = recurso;
        this.lista = lista;
        this.i = (LayoutInflater) contexto.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        public TextView tv_texto_nombre , tv_fecha_produccion , tv_texto_modelo;
        public ImageView imagen_lista;
        public int posicion;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.v("LOG",""+lista.size());
        ViewHolder vh = null;
        if(convertView == null){
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tv_texto_nombre =(TextView)convertView.findViewById(R.id.tv_texto_nombre);
            vh.tv_texto_modelo =(TextView)convertView.findViewById(R.id.tv_texto_modelo);
            vh.tv_fecha_produccion =(TextView)convertView.findViewById(R.id.tv_texto_year);
            vh.imagen_lista = (ImageView)convertView.findViewById(R.id.imagen_lista);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        vh.posicion = position;
        vh.tv_texto_nombre.setText(lista.get(position).getMarca());
        vh.tv_texto_modelo.setText(lista.get(position).getModelo());
        vh.tv_fecha_produccion.setText(lista.get(position).getYear()+"");
        if(lista.get(position).getEditado()) {
            Log.v(convertView.getTag().toString(), "");
            Bitmap imagen = lista.get(position).getImagen();
            vh.imagen_lista.setImageBitmap(imagen);
        }
        return convertView;
    }

}
