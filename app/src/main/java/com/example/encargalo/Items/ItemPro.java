package com.example.encargalo.Items;

public class ItemPro {
    private String idpro,nombrepro,precio,promocion;

    public ItemPro(String id_pro,String nom_pro, String precio_pro, String promocion_pro){
        this.idpro = id_pro;
        this.nombrepro = nom_pro;
        this.precio = precio_pro;
        this.promocion = promocion_pro;
    }

    public String getIdpro() {
        return idpro;
    }

    public void setIdpro(String idpro) {
        this.idpro = idpro;
    }

    public String getNombrepro() {
        return nombrepro;
    }

    public void setNombrepro(String nombrepro) {
        this.nombrepro = nombrepro;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPromocion() {
        return promocion;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }
}
