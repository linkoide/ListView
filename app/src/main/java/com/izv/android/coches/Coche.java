package com.izv.android.coches;

import android.graphics.Bitmap;

/**
 * Created by abraham on 8/10/14.
 */
public class Coche implements Comparable {

    private String marca , modelo;
    private int year;
    private Bitmap imagen;
    private boolean editado = false;


    public Coche(String marca , String modelo , int year){
        this.marca = marca;
        this.modelo = modelo;
        this.year = year;
        editado = false;
    }

    public Coche(){}

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
        this.editado = true;
    }

    public boolean getEditado() {
        return this.editado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
