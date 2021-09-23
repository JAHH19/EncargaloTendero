package com.example.encargalo.Items;

public class itemdetallepedido {
    private String descpro,cantidad,preciouni,subtotal;

    public itemdetallepedido(String descpro, String cantidad, String preciouni, String subtotal) {
        this.descpro = descpro;
        this.cantidad = cantidad;
        this.preciouni = preciouni;
        this.subtotal = subtotal;
    }

    public String getDescpro() {
        return descpro;
    }

    public void setDescpro(String descpro) {
        this.descpro = descpro;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPreciouni() {
        return preciouni;
    }

    public void setPreciouni(String preciouni) {
        this.preciouni = preciouni;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
