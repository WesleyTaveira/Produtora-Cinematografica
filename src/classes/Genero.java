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
public class Genero implements Serializable {
    private String nomeGenero, idgenero;

    public Genero(String nomeGenero, String idgenero) {
        this.nomeGenero = nomeGenero;
        this.idgenero = idgenero;
    }
    
    
    //Métodos especias

    public String getNomeGenero() {
        return nomeGenero;
    }

    public void setNomeGenero(String nomeGenero) {
        this.nomeGenero = nomeGenero;
    }

    public String getIdGenero() {
        return idgenero;
    }

    public void setIdgenero(String idgenero) {
        this.idgenero = idgenero;
    }
    
    
}