/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ionaa
 */
public class Estudio implements Serializable {
    private String localizacao, idestudio, nome;
    private ArrayList<Filme> filmes;
    
    

    public Estudio( String nome, String idestudio,String localizacao) {
        this.localizacao = localizacao;
        this.idestudio = idestudio;
        this.nome = nome;
        filmes = new ArrayList();
    }

    public ArrayList<Filme> getFilmes() {
        return filmes;
    }
    
    public boolean addFilme(Filme filme){
        for(Filme f: filmes){
            if(f.getIdFilme().equals(filme.getIdFilme())){
                return false;
            }
        }
        filmes.add(filme);
        return true;
    }
    
    public boolean removeFilme(String id){
        int i = 0;
        for(Filme f: filmes){
            if(f.getIdFilme().equals(id)){
                filmes.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }
    
    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getIdestudio() {
        return idestudio;
    }

    public void setIdestudio(String idestudio) {
        this.idestudio = idestudio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}