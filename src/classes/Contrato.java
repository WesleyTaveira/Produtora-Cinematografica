package classes;

import java.io.Serializable;
import java.util.Date;

public class Contrato implements Serializable {
    private String idContrato,servico;
    private Date dataInicio,dataTermino;
    private double valor;
    private Filme filme;
    private Empresa empresa;

    public Contrato(String idContrato, String servico, Date dataInicio, Date dataTermino, double valor, Filme filme, Empresa empresa) {
        this.idContrato = idContrato;
        this.servico = servico;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.valor = valor;
        this.filme = filme;
        this.empresa = empresa;
        
        
        
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public double getValor() {
        return valor;
    }

    public boolean setValor(double valor) {
        double resto_filme = filme.getRestante_orcamento();
        resto_filme += this.valor;
        resto_filme -= valor;
        if(resto_filme<0){
            return false;
        }
        
        filme.setRestante_orcamento(resto_filme);
        this.valor = valor;
        return true;
    }

    public String getIdContrato() {
        return idContrato;
    }

    public String getServico() {
        return servico;
    }

    public Filme getFilme() {
        return filme;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    
    
    
    
    

    

    
    
}
