package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Filme implements Serializable{
    private int duracao,tempoProducao,ano;
    private String idFilme,idioma,classificacao,sinopse,nome;
    private Double faturamento,orcamento,restante_orcamento;
    
    private ArrayList<Personagem> personagens;
    private Diretor diretor;
    private ArrayList<Genero> generos;
    private ArrayList<Estudio> estudios;

    public void setIdFilme(String idFilme) {
        this.idFilme = idFilme;
        
    }
    private ArrayList<Contrato> contratos;

    public Filme(int duracao, int tempoProducao, int ano, String idFilme, String idioma, String classificacao, String sinopse, double faturamento, double orcamento, ArrayList<Personagem> personagens, Diretor diretor, ArrayList<Genero> generos, ArrayList<Estudio> estudios) {
        this.duracao = duracao;
        this.tempoProducao = tempoProducao;
        this.ano = ano;
        this.idFilme = idFilme;
        this.idioma = idioma;
        this.classificacao = classificacao;
        this.sinopse = sinopse;
        this.faturamento = faturamento;
        this.orcamento = orcamento;
        this.personagens = personagens;
        this.diretor = diretor;
        this.generos = generos;
        this.estudios = estudios;
        contratos = new ArrayList();
        restante_orcamento = orcamento;
    }
    
    public Filme(){
        personagens = new ArrayList();
        generos = new ArrayList();
        estudios = new ArrayList();
        contratos = new ArrayList();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
     /*
    return 1: adicionado com sucesso
    return -1:ja existe tal personagem
    return 0: orcamento insuficiente 
    */
    
    
    public int addPersonagem(Personagem personagem){
        
        if(personagem.getCache()>restante_orcamento){
            return 0;
        }
        
        for(int i =0;i<personagens.size();i++){
            if(personagem.getNome().equals(personagens.get(i).getNome())){
                return -1;
            }
        }
        restante_orcamento -= personagem.getCache();
        personagens.add(personagem);
        return 1;
    }
    
    
    /*
    return 1: removido com sucesso
    return 0: não existe tal personagem
    return -1: é o unico personagem do filme(deve haver pelo menos um)
    */
    
    
    public boolean removePersonagem(String nome){
        int index = 0;
        for(Personagem p: personagens){
            if(p.getNome().equals(nome)){
                
                
                restante_orcamento += p.getCache();
                personagens.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }
    
    /* addContrato
     retorno 0: já existe um contrato de mesmo ID
             -1: não há orcamento suficiente para realizar contrato
             1: contrato adicionado com sucesso
    */
    public int addContrato(Contrato contrato){
        for(int i = 0;i<contratos.size();i++){
            if(contrato.getIdContrato().equals(contratos.get(i).getIdContrato())){
                return 0;
            }
        }
        if(restante_orcamento<contrato.getValor()){
            return -1;
        }
        restante_orcamento-=contrato.getValor();
        
        contratos.add(contrato);
        return 1;
    }
    
    public boolean removeContrato(String id){
        int index = 0;
        for(Contrato c: contratos){
            if(c.getIdContrato().equals(id)){
                restante_orcamento += c.getValor();
                contratos.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }
    
    
    
    public boolean addGenero(Genero genero){
        for(int i = 0;i<generos.size();i++){
            if(genero.getIdGenero().equals(genero.getIdGenero())){
                return false;
            }
        }
        generos.add(genero);
        return true;
    }
    
    /*
    
    return true: removido com sucesso
    return false: não existe tal genero no filme
    
    
    */
    public boolean removeGenero(String id){
        int i = 0;
        for(Genero g: generos){
            if(g.getIdGenero().equals(id)){
                
                generos.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }
    
    public boolean addEstudio(Estudio estudio){
        for(Estudio e: estudios){
            if(e.getIdestudio().equals(estudio.getIdestudio())){
                return false;
            }
        }
        estudios.add(estudio);
        return true;
    }
    
    public int posicaoPersonagem(String nome){
        for(int i =0;i<personagens.size();i++){
            if(personagens.get(i).getNome().equals(nome)){
                return i;
            }
        }
        System.out.println("nn existe o personagem");
        return -1;
    }
    
    
    public boolean removeEstudio(String id){
        int i = 0;
        for(Estudio e: estudios){
            if(e.getIdestudio().equals(id)){
                
                estudios.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }
    
    
    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getTempoProducao() {
        return tempoProducao;
    }

    public void setTempoProducao(int tempoProducao) {
        this.tempoProducao = tempoProducao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public double getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(double faturamento) {
        this.faturamento = faturamento;
    }

    public double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(double orcamento) {
        if(this.restante_orcamento == null){
            if(this.orcamento == null){
                this.orcamento = orcamento;
            }
            restante_orcamento = orcamento;
            
        }else{
            restante_orcamento += (orcamento-this.orcamento);
        }
        this.orcamento = orcamento;
        
    }

    public Diretor getDiretor() {
        return diretor;
    }

    public void setDiretor(Diretor diretor) {
        this.diretor = diretor;
    }

    public String getIdFilme() {
        return idFilme;
    }

    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    public ArrayList<Genero> getGeneros() {
        return generos;
    }

    public ArrayList<Estudio> getEstudios() {
        return estudios;
    }

    public ArrayList<Contrato> getContratos() {
        return contratos;
    }

    public Double getRestante_orcamento() {
        return restante_orcamento;
    }

    public void setRestante_orcamento(double restante_orcamento) {
        this.restante_orcamento = restante_orcamento;
    }
    
    
    
}
