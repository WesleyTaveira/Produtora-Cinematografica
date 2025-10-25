/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ionaa
 */
public class Diretor extends Funcionario {
    
    private double percentual;
    private ArrayList<Filme> filmes;

    public Diretor() {
        this.filmes = new ArrayList();
    }

    public Diretor(double percentual) {
        this.percentual = percentual;
        filmes = new ArrayList();
    }

    public Diretor(String cpf) {
        super(cpf);
        this.filmes = new ArrayList();
    }

    public Diretor(double percentual, String cpf, String nome, String senha, String email, String telefone, Date data_nascimento, String sexo, String nacionalidade) {
        super(cpf, nome, senha, email, telefone, data_nascimento, sexo, nacionalidade);
        this.percentual = percentual;
        this. filmes = new ArrayList<>();
    }
    
    
    
    

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }
    
    public double totalReceber(){
        double soma = 0;
        for(Filme f: filmes){
            soma += f.getFaturamento()*(this.percentual) / 100;
        }
        return soma;
    }

    public ArrayList<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(ArrayList<Filme> filmes) {
        this.filmes = filmes;
    }
    
    
    
    
    /*
    retorna -1 se não existe filme
    */
    public double valorFilme(String id){
        for(Filme f: filmes){
            if(f.getIdFilme().equals(id)){
                return f.getFaturamento()*this.percentual;
            }
        }
        return -1;
    }
    
    
}