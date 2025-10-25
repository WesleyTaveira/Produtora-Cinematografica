package classes;

import java.io.Serializable;
import java.util.ArrayList;


public class Empresa implements Serializable {
    private String nome,cnpj;
    private ArrayList<Contrato> contratos;

    public Empresa(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
        contratos = new ArrayList();
    }
    
    public Empresa(String cnpj){
        this.cnpj =cnpj;
        this.contratos = new ArrayList();
    }
    
    public boolean addContrato(Contrato contrato){
        for(int i = 0;i<contratos.size();i++){
            if(contrato.getIdContrato().equals(contratos.get(i).getIdContrato())){
                return false;
            }
        }
        contratos.add(contrato);
        return true;
    }
    
    public boolean removeContrato(String id){
        for(int i = 0;i<contratos.size();i++){
            if(contratos.get(i).getIdContrato().equals(id)){
                contratos.remove(i);
                return true;
            }
        }
        return false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public ArrayList<Contrato> getContratos() {
        return contratos;
    }
    
}
