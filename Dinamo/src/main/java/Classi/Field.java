package Classi;

import Utility.ModelHelper;
import java.util.ArrayList;

/**
 *
 * @author n.dipietro
 */
public class Field
{
    /*
    * Lista delle annotazioni presenti per ogni campo
    */
    ArrayList<String> annotazione;
    
    /*
    * Modificatore del campo
    */
    String modificatore;
    
    /*
    * Lista delle chiavi del campo
    */
    ArrayList<String> key;
    
    /*
    * Tipo del campo
    */
    String tipo;
    
    /*
    * Nome del campo
    */
    String nome;

    /*
    * Costruttore
    */
    public Field(ArrayList<String> annotazione, String modificatore, ArrayList<String> key, String tipo, String nome)
    {
        this.annotazione = annotazione;
        this.modificatore = modificatore;
        this.key = key;
        this.tipo = tipo;
        this.nome = nome;
    }

    //GETTER AND SETTER
    
    /*
    * Metodo che ritorna la stringa a partire dalla lista di annotazioni
    */
    public String getAnnotazione()
    {
        String annotazioni = "";
        if(this.annotazione != null)
        {
            for(String k : this.annotazione)
            {
                annotazioni += k + " " + new ModelHelper().newLine(1) + new ModelHelper().tab(1);
            }
        }
        return annotazioni;
        
    }

    public void setAnnotazione(ArrayList<String> annotazione)
    {
        this.annotazione = annotazione;
    }

    public String getModificatore()
    {
        return modificatore;
    }

    public void setModificatore(String modificatore)
    {
        this.modificatore = modificatore;
    }
    
    /*
    * Metodo che restituisce le chiavi a partire dalla lista di chiavi
    */
    public String getKey()
    {
        String chiavi = "";
        if(this.key != null)
        {
            for(String k : this.key)
            {
                chiavi += k + " ";
            }
        }
            
        return chiavi;
    }

    public void setKey(ArrayList<String> key)
    {
        this.key = key;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

}
