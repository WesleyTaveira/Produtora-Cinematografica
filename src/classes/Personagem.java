/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.io.Serializable;

/**
 *
 * @author ionaa
 */
public class Personagem implements Serializable {
    private int idadePersonagem;
    private String nomePersonagem, sexoPersonagem, descricao;
    private double cache;
    private Ator ator;
    private Filme filme;
    
    

    public Personagem(int idadePersonagem, String nomePersonagem, String sexoPersonagem, String descricao, double cache,Ator ator, Filme filme) {
        this.idadePersonagem = idadePersonagem;
        this.nomePersonagem = nomePersonagem;
        this.sexoPersonagem = sexoPersonagem;
        this.descricao = descricao;
        this.cache = cache;
        this.ator = ator;
        this.filme = filme;
    }

    public Ator getAtor() {
        return ator;
    }

    public void setAtor(Ator ator) {
        this.ator = ator;
    }
    
    

    
    
    
    
    //Métodos especias

    public int getIdade() {
        return idadePersonagem;
    }

    public void setIdade(int idadePersonagem) {
        this.idadePersonagem = idadePersonagem;
    }

    public String getNome() {
        return nomePersonagem;
    }

    public void setNome(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }

    public String getSexo() {
        return sexoPersonagem;
    }

    public void setSexo(String sexoPersonagem) {
        this.sexoPersonagem = sexoPersonagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getCache() {
        return cache;
    }

    public void setCache(double cache) {
        filme.setRestante_orcamento(filme.getRestante_orcamento() + this.cache);
        this.cache = cache;
        filme.setRestante_orcamento(filme.getRestante_orcamento() - this.cache);
        
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    
    
    
    
}

