package com.company;

import java.io.*;
import java.util.*;

abstract class Investigador implements Serializable {
    protected String nome;
    protected String email;
    protected Grupo grupo;

    public Investigador(String nome, String email, Grupo grupo){
        this.nome = nome;
        this.email = email;
        this.grupo = grupo;
    }

    /***
     * Funcao para evitar o use de instaceof e getClass(), que foram desaconselhados pelo professor.
     * @return devolve uma string dependendo do tipo da classe.
     */
    abstract public String classe();

    public String getNome(){ return nome; }
    public String getEmail(){ return email; }
    public Grupo getGrupo(){ return grupo; }
}

class Estudante extends Investigador{
    private String tese;
    private Data conclusao;
    private MembroEfetivo orientador;

    public Estudante(String nome, String email, Grupo grupo, String tese, Data conclusao, MembroEfetivo orientador){
        super(nome, email, grupo);
        this.tese = tese;
        this.conclusao = conclusao;
        this.orientador = orientador;
    }

    public String classe(){ return "estudante"; }
}

class MembroEfetivo extends Investigador{
    private int gabinete;
    private int telefone;

    public MembroEfetivo(String nome, String email, Grupo grupo, int gabinete, int telefone){
        super(nome, email, grupo);
        this.gabinete = gabinete;
        this.telefone = telefone;
    }

    public String classe(){ return "membroefetivo"; }

    public int getTelefone() { return telefone; }
}