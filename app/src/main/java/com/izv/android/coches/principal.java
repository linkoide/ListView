package com.izv.android.coches;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;


public class principal extends Activity {

    private ArrayList<Coche> coches = new ArrayList<Coche>();
    private AdaptadorLista aal;
    private Button bt_imagen;
    private int SELECT_IMAGE = 1;
    private Coche coche_actual = new Coche();

    public principal() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
    }

    private void CrearCoches(){
        Coche c1 = new Coche("Citroen" , "C1" , 2005);
        Coche c2 = new Coche("Citroen" , "C2" , 2003);
        Coche c3 = new Coche("Citroen" , "C3" , 2007);
        Coche c4 = new Coche("Peugeot" , "408" , 2012);
        coches.add(c1);
        coches.add(c2);
        coches.add(c3);
        coches.add(c4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        CrearCoches();
        initComponents();
        return true;
    }

    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.eliminar){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //1. obtenemos el indice
            int index = info.position;

            //2. objeto view + patron viewholder
            Object o = info.targetView.getTag();
            AdaptadorLista.ViewHolder vh;
            vh = (AdaptadorLista.ViewHolder)o;

            coches.remove(index);
            aal.notifyDataSetChanged();

        }else if(id == R.id.anadir){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //1. obtenemos el indice
            int index = info.position;
            anadir();

            aal.notifyDataSetChanged();
        }else if(id == R.id.editar){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //1. obtenemos el indice
            int index = info.position;

            editar(index);

            aal.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);

    }

    private boolean anadir(){
        //datosv2.add("ultimo");
        //aal.notifyDataSetChanged();
        final AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Alta");

        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogoalta, null);
        bt_imagen = (Button)vista.findViewById(R.id.bt_imagen);
        bt_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                principal.this.startActivityForResult(intent, SELECT_IMAGE);
            }
        });
        alert.setView(vista);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EditText marca,modelo , year;
                marca = (EditText) vista.findViewById(R.id.et_marca);
                modelo = (EditText) vista.findViewById(R.id.et_modelo);
                year = (EditText) vista.findViewById(R.id.et_year);
                coche_actual.setMarca(marca.getText().toString());
                coche_actual.setModelo(modelo.getText().toString());
                coche_actual.setYear(Integer.parseInt(year.getText().toString()));
                //coche_actual = new Coche(marca.getText().toString() , modelo.getText().toString() , Integer.parseInt(year.getText().toString()));
                coches.add(coche_actual);
                aal.notifyDataSetChanged();
                tostada("Añadido un vehículo nuevo");
            }
        });
        alert.setNegativeButton(android.R.string.no ,null);
        alert.show();

        return true;
    }

    private boolean editar(final int position){
        Coche c = coches.get(position);

        final AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Editar");

        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogoalta, null);
        alert.setView(vista);

        final EditText marca , modelo , year;
        marca = (EditText) vista.findViewById(R.id.et_marca);
        modelo = (EditText) vista.findViewById(R.id.et_modelo);
        year = (EditText) vista.findViewById(R.id.et_year);
        marca.setText(c.getMarca());
        modelo.setText(c.getModelo());
        year.setText(Integer.toString(c.getYear()));

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                Coche c = new Coche(marca.getText().toString() , modelo.getText().toString(), Integer.parseInt(year.getText().toString()));
                coches.set(position, c);
               actualizar();
                tostada("Coche actualizado");
            }
        });
        alert.setNegativeButton(android.R.string.no ,null);
        alert.show();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    UTILIDADES
     */
    public void addCoche(Coche c){
        this.coches.add(c);
    }

    public boolean deleteCoche(Coche c){
        int longitud = this.coches.size();
        for (int i = 0; i <longitud; i++) {
            if(coches.get(i) == c) {
                coches.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> listadoMarcas(){
        int longitud = this.coches.size();
        ArrayList<String> marcas_permitidas = new ArrayList();
        for (int i = 0; i <longitud; i++) {
            if(i == 0){
                marcas_permitidas.add(this.coches.get(i).getMarca());
            }else{
                int j = 0;
                while(marcas_permitidas.get(j) != this.coches.get(i).getMarca()){
                    marcas_permitidas.add(this.coches.get(i).getMarca());
                }

            }
        }
        return marcas_permitidas;
    }

    private void initComponents(){
        aal = new AdaptadorLista(this, R.layout.detalle_lista, coches);
        final ListView ls = (ListView)findViewById(R.id.lista_marcas);
        ls.setAdapter(aal);
        registerForContextMenu(ls);
    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void actualizar(){
        Collections.sort(coches);
        aal.notifyDataSetChanged();
    }

    private boolean borrar(final int position){
        coches.remove(position);
        actualizar();
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

                if (resultCode == Activity.RESULT_OK && requestCode == SELECT_IMAGE) {
                    Uri selectedImage = data.getData();
                    String ruta = getPath(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeFile(ruta);
                    coche_actual.setImagen(bitmap);
                    //Modificar clase coche para guardar el bitmap..
                }
    }

    private String getPath(Uri uri) {
        String[] projection = { android.provider.MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
