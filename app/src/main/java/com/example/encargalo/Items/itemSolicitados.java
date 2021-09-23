package com.example.encargalo.Items;

public class itemSolicitados {
    private String idpedido,nombrecli,importe,hora,fecha;

    public itemSolicitados(String idped,String nomb,String impo,String hor,String fec){
        this.idpedido = idped;
        this.nombrecli = nomb;
        this.importe = impo;
        this.hora = hor;
        this.fecha = fec;
    }
    public String getIdpedidocli() {
        return idpedido;
    }

    public String getnombrecli() {
        return nombrecli;
    }

    public String getimporte() {
        return importe;
    }

    public String gethora() {
        return hora;
    }

    public String getfecha() {
        return fecha;
    }
}
