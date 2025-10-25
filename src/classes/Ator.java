/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.ArrayList;
import java.util.Date;


public class Ator extends Funcionario {
    
    private int tempoExperiencia;
    private String biografia;
    private ArrayList<Personagem> personagens;
    private ArrayList<Genero> generos;

    public Ator() {
    }

    public Ator(int tempoExperiencia, String biografia,String cpf, String nome, String senha, String email, String telefone, Date data_nascimento, String sexo, String nacionalidade) {
        super(cpf, nome, senha, email, telefone, data_nascimento, sexo, nacionalidade);
        this.tempoExperiencia = tempoExperiencia;
        this.biografia = biografia;
        this.personagens = personagens;
        this.generos = generos;
        this.personagens = new ArrayList();
    }

    

    public Ator(String cpf) {
        super(cpf);
        personagens = new ArrayList();
    }
    
    

    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    public ArrayList<Genero> getGeneros() {
        return generos;
    }
    
    public boolean removePersonagem(String nome, Filme filme){
        for(int i = 0;i<personagens.size();i++){
            if(personagens.get(i).getNome().equals(nome) && personagens.get(i).getFilme() == filme){
                personagens.remove(personagens.get(i));
                return true;
            }
        }
        return false;
    }

    
    public int getTempoExperiencia() {
        return tempoExperiencia;
    }

    public void setTempoExperiencia(int tempoExperiencia) {
        this.tempoExperiencia = tempoExperiencia;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
    
    
    
    public double totalReceber(){
        double soma = 0;
        for(Personagem p: personagens){
            soma += p.getCache();
        }
        return soma;
    }
    
    
    /*
    retorna -1 se não existe personagem
    */
    public double cachePersonagem(String nome){
        for(Personagem p: personagens){
            if(p.getNome().equals(nome)){
                return p.getCache();
            }
        }
        return -1;
    }
    
    
}
