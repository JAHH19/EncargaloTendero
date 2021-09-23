package com.example.encargalo.Items;

public class itemHistorial {
    private String idpedido,nombrecli,importe,hora,fecha,estado;

    public itemHistorial(String idped,String nomb,String impo,String hor,String fec,String est){
        this.idpedido = idped;
        this.nombrecli = nomb;
        this.importe = impo;
        this.hora = hor;
        this.fecha = fec;
        this.estado = est;

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

    public String getestado() {
        return estado;
    }
}

