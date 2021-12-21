package com.company;

import java.io.*;
import java.util.*;

/***
 *Classe que gera os objetos usados para representar os grupos.
 */
class Grupo implements Serializable{
    private final String nome;
    private final String acronimo;
    private MembroEfetivo responsavel;
    private ArrayList<Investigador> membros;

    public Grupo(String nome, String acronimo, MembroEfetivo responsavel, ArrayList<Investigador> membros) {
        this.nome = nome;
        this.acronimo = acronimo;
        this.responsavel = responsavel;
        this.membros = membros;
    }

    /***
     * Adiciona um investigador à lista de membros do grupo.
     * @param investigador Ponteiro para o investigador a adicionar.
     */
    public void adicionarInvestigador(Investigador investigador){ this.membros.add(investigador); }

    public ArrayList<Investigador> getMembros(){ return membros; }
    public String getNome(){ return nome; }
    public String getAcronimo(){ return acronimo; }

    public void setResponsavel(MembroEfetivo responsavel){ this.responsavel = responsavel; }
}
