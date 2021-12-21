package com.company;

import java.io.*;
import java.util.*;

/***
 * Super classe das classes que geram os objetos usados para representar as publicacoes.
 */
abstract class Publicacao implements Serializable{
    protected ArrayList<Investigador> autores;
    protected String titulo;
    protected ArrayList<String> palavrasChave;
    protected int ano;
    protected int dimensao;
    protected String resumo;

    public Publicacao(ArrayList<Investigador> autores, String titulo, ArrayList<String> palavrasChave, int ano, int dimensao, String resumo){
        this.autores = autores;
        this.titulo = titulo;
        this.palavrasChave = palavrasChave;
        this.ano = ano;
        this.dimensao = dimensao;
        this.resumo = resumo;
    }

    /***
     * Adiciona um investigador aos autores da publicacao
     * @param investigador Ponteiro para o investigador a adicionar.
     */
    public void adicionarAutor(Investigador investigador){
        this.autores.add(investigador);
    }
    /***
     * Verifica o tipo de impacto da publicacao.
     * @return Devolve A, B ou C dependendo da dimensao e do tipo de publicacao.
     */
    abstract public String impacto();
    /***
     * Funcao para evitar o use de instaceof e getClass(), que foram desaconselhados pelo professor.
     * @return devolve uma string dependendo do tipo da classe.
     */
    abstract public String classe();

    abstract public String toString();

    public void setPalavrasChave(String[] palavras){ palavrasChave.addAll(Arrays.asList(palavras)); }
    public void setResumo(String resumo){ this.resumo = resumo; }

    public int getAno(){ return ano; }
    public ArrayList<Investigador> getAutores(){ return autores; }
}

/***
 * Classe que gera os objetos usados para representar as publicacoes do tipo Publicacao.
 */
class Conferencia extends Publicacao{
    private String nome;
    private Data data;
    private String localizacao;

    public Conferencia(ArrayList<Investigador> autores, String titulo, ArrayList<String> palavrasChave, int ano, int dimensao, String resumo, String nome, Data data, String localizacao){
        super(autores, titulo, palavrasChave, ano, dimensao, resumo);
        this.nome = nome;
        this.data = data;
        this.localizacao = localizacao;
    }

    public String impacto(){
        if (this.dimensao >= 500)
            return "A";
        else if (this.dimensao < 200)
            return "C";
        else
            return "B";
    }
    public String classe(){ return "conferencia"; }

    public String toString(){
        return "Publicacao de " + this.ano + ", do tipo Conferencia, com impacto " + this.impacto() + ".";
    }

    public void setNome(String nome){ this.nome = nome; }
    public void setData(Data data){ this.data = data; }
    public void setLocalizacao(String localizacao){ this.localizacao = localizacao; }
}

class Revista extends Publicacao{
    private String nome;
    private Data data;
    private int numero;

    public Revista(ArrayList<Investigador> autores, String titulo, ArrayList<String> palavrasChave, int ano, int dimensao, String resumo, String nome, Data data, int numero){
        super(autores, titulo, palavrasChave, ano, dimensao, resumo);
        this.nome = nome;
        this.data = data;
        this.numero = numero;
    }

    public String impacto(){
        if (this.dimensao >= 1000)
            return "A";
        else if (this.dimensao < 500)
            return "C";
        else
            return "B";
    }
    public String classe(){ return "revista"; }

    public String toString(){
        return "Publicacao de " + this.ano + ", do tipo Revista, com impacto " + this.impacto() + ".";
    }

    public void setNome(String nome){ this.nome = nome; }
    public void setData(Data data){ this.data = data; }
    public void setNumero(int numero){ this.numero = numero; }
}

class Livro extends Publicacao{
    protected String editora;
    protected String ISBN;

    public Livro(ArrayList<Investigador> autores, String titulo, ArrayList<String> palavrasChave, int ano, int dimensao, String resumo, String editora, String ISBN){
        super(autores, titulo, palavrasChave, ano, dimensao, resumo);
        this.editora = editora;
        this.ISBN = ISBN;
    }

    public String impacto(){
        if (this.dimensao >= 10000)
            return "A";
        else if (this.dimensao < 5000)
            return "C";
        else
            return "B";
    }
    public String classe(){ return "livro"; }

    public String toString(){
        return "Publicacao de " + this.ano + ", do tipo Livro, com impacto " + this.impacto() + ".";
    }

    public void setEditora(String editora){ this.editora = editora; }
    public void setISBN(String ISBN){ this.ISBN = ISBN; }
}

class Capitulo extends Livro{
    private String nome;
    private int paginaInicio;
    private int paginaFinal;

    public Capitulo(ArrayList<Investigador> autores, String titulo, ArrayList<String> palavrasChave, int ano, int dimensao, String resumo, String editora, String ISBN, String nome, int paginaInicio, int paginaFinal){
        super(autores, titulo, palavrasChave, ano, dimensao, resumo, editora, ISBN);
        this.nome = nome;
        this.paginaInicio = paginaInicio;
        this.paginaFinal = paginaFinal;
    }

    public String impacto(){
        if (this.dimensao >= 10000)
            return "A";
        else if (this.dimensao < 5000)
            return "C";
        else
            return "B";
    }
    public String classe(){ return "capitulo"; }

    public String toString(){
        return "Publicacao de " + this.ano + ", do tipo Capítulo, com impacto " + this.impacto() + ".";
    }

    public void setNome(String nome){ this.nome = nome; }
    public void setPaginaInicio(int paginaInicio){ this.paginaInicio = paginaInicio; }
    public void setPaginaFinal(int paginaFinal){ this.paginaFinal = paginaFinal; }
}

class LivroConferencia extends Livro{
    private String nome;
    private int artigos;

    public LivroConferencia(ArrayList<Investigador> autores, String titulo, ArrayList<String> palavrasChave, int ano, int dimensao, String resumo, String editora, String ISBN, String nome, int artigos){
        super(autores, titulo, palavrasChave, ano, dimensao, resumo, editora, ISBN);
        this.nome = nome;
        this.artigos = artigos;
    }

    public String impacto(){
        if (this.dimensao >= 7500)
            return "A";
        else if (this.dimensao < 2500)
            return "C";
        else
            return "B";
    }
    public String classe(){ return "livroconferencia"; }

    public String toString(){
        return "Publicacao de " + this.ano + ", do tipo Livro de Conferência, com impacto " + this.impacto() + ".";
    }

    public void setNome(String nome){ this.nome = nome; }
    public void setArtigos(int artigos){ this.artigos = artigos; }
}
