package com.example.prueba;

public class CardServices {
    private final String categoria;
    private final int imagen;
    private final String precio;
    private final String tiempo;
    private final String titulo;

    public CardServices(int imagen, String titulo, String categoria, String precio, String tiempo) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.categoria = categoria;
        this.precio = precio;
        this.tiempo = tiempo;
    }

    public int getImagen() {
        return this.imagen;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public String getPrecio() {
        return this.precio;
    }

    public String getTiempo() {
        return this.tiempo;
    }
}
