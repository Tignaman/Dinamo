package Classi;

import java.util.ArrayList;

/**
 * Classe che corrisponde al campo di una classe
 * 
 * @author n.dipietro
 */
public class Field
{
    /**
     * Lista delle annotazioni presenti per ogni campo
     */
    ArrayList<String> annotazione;
    
    /**
     * Modificatore del campo
     */
    String modificatore;
    
    /**
     * Lista delle chiavi del campo
     */
    ArrayList<String> key;
    
    /**
     * Tipo del campo
     */
    String tipo;
    
    /**
     * Nome del campo
     */
    String nome;

    /**
     * 
     * @param annotazione Lista delle annotazioni di una classe
     * @param modificatore Modificatore del campo
     * @param key Lista delle chiavi del campo
     * @param tipo Tipo del campo
     * @param nome Nome del campo
     */
    public Field(ArrayList<String> annotazione, String modificatore, ArrayList<String> key, String tipo, String nome)
    {
        this.annotazione = annotazione;
        this.modificatore = modificatore;
        this.key = key;
        this.tipo = tipo;
        this.nome = nome;
    }

    /**
     * 
     * @return Stringa delle annotazioni partendo dalla lista delle annotazioni
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
    
    /**
     * 
     * @param annotazione 
     */
    public void setAnnotazione(ArrayList<String> annotazione)
    {
        this.annotazione = annotazione;
    }

    /**
     * 
     * @return Stringa del modificatore
     */
    public String getModificatore()
    {
        return modificatore;
    }

    /**
     * 
     * @param modificatore 
     */
    public void setModificatore(String modificatore)
    {
        this.modificatore = modificatore;
    }
    
    /**
     * 
     * @return la stringa delle chiavi a partire dalla lista di chiavi
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

    /**
     * 
     * @param key 
     */
    public void setKey(ArrayList<String> key)
    {
        this.key = key;
    }

    /**
     * 
     * @return il tipo del campo
     */
    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    
    /**
     * 
     * @return il nome del campo
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * 
     * @param nome 
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

}
