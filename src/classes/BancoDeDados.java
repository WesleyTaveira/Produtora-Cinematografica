
package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ionaa
 */
public class BancoDeDados implements Serializable {

    private static final String filePath = "BancoDados\\bd.ser";
    

    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Filme> filmes;
    private ArrayList<Estudio> estudios;
    private ArrayList<Empresa> empresas;
    private ArrayList<Contrato> contratos;
    private ArrayList<Genero> generos;

    public BancoDeDados() {
        funcionarios = new ArrayList();
        filmes = new ArrayList();
        estudios = new ArrayList();
        empresas = new ArrayList();
        contratos = new ArrayList();
        generos = new ArrayList();
        generos.add(new Genero("drama","1"));
        generos.add(new Genero("comédia","2"));
        generos.add(new Genero("ação","3"));
        generos.add(new Genero("terror","4"));
        
        

    }

    public static String getFilePath() {
        return filePath;
    }

    public static BancoDeDados readBancoDeDados() {
        File fileBd = new File(BancoDeDados.filePath);
        BancoDeDados bd;
        try {
            FileInputStream fileIn = new FileInputStream(fileBd);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            bd = (BancoDeDados) objectIn.readObject();
            
            fileIn.close();
            objectIn.close();
            return bd;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static void writeBancoDeDados(BancoDeDados bd) {
        try {
            FileOutputStream fileOut = new FileOutputStream(BancoDeDados.filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(bd);
            objectOut.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("erro");
        }
    }
    
    


    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public ArrayList<Filme> getFilmes() {
        return filmes;
    }

    public ArrayList<Estudio> getEstudios() {
        return estudios;
    }

    public ArrayList<Empresa> getEmpresas() {
        return empresas;
    }

    public ArrayList<Contrato> getContratos() {
        return contratos;
    }

    public ArrayList<Genero> getGeneros() {
        return generos;
    }
    
    
}
