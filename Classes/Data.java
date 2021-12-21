package com.company;

import java.io.Serializable;

class Data implements Serializable {
    private final int dia;
    private final int mes;
    private final int ano;

    public Data(int dia, int mes, int ano){
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }
}
