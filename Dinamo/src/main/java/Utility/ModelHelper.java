package Utility;

import Classi.Field;
import Configurazione.ConfigHelper;
import Configurazione.ConfigModel;
import Configurazione.ConfigName;
import static Utility.Utility.creaFileJava;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 *
 * @author n.dipietro
 */
public class ModelHelper
{
    /*
    * Nome della tabella corrispondente alla classe
    */
    public String nomeTabella = "";
    
    /*
    * Codice sorgente della classe
    */
    public String classString = "";
 
    /*
    * Codice sorgente dei getter
    */
    public String getter = "";
    
    /*
    * Codice sorgente dei setter
    */
    public String setter = "";
        
    /*
    *Funzione che permette di specificare un package
    */
    public ModelHelper toPackage(String pkg)
    {
        this.classString = "package " + pkg +";\n\n";
        addDependencies();
        return this;
    }
    
    /*
    *Funzione che permette di specificare gli import
    */
    public void addDependencies()
    {
        this.classString += ConfigModel.IMPORT +" "+ ConfigName.ANNOTAZIONI+".*;" + newLine(1) ;
        this.classString += ConfigModel.IMPORT +" "+ ConfigName.JAVA_SQL_DATE+";" + newLine(2) ;
    }
    
    /*
    *Funzione che permette di specificare il modificatore d'accesso
    */
    public ModelHelper withAccessMode(String mode)
    {
        this.classString += mode + " ";
        return this;
    }
    
    /*
    *Funzione che permette di specificare le chiavi
    */
    public ModelHelper withKey(String ...key)
    {
        for(String k : key)
        {
            this.classString += k + " ";
        }       
        return this;
    }
    
    /*
    *Funzione che permette di specificare il tipo di ritorno
    */
    public ModelHelper returning(String type)
    {
        this.classString += type + " ";
        return this;
    }
    
    /*
    *Funzione che permette di specificare il nome della classe
    */
    public ModelHelper className(String name)
    {
        this.nomeTabella = name;
        this.classString += name + newLine(1) + openBracket();
        return this;
    }
    
    /*
    *Funzione che permette di aggiungere un campo e che riempie i getter e i setter
    */
    public ModelHelper addParamater(Field f)
    {
        this.classString += tab(1) +
                (f.getAnnotazione() != null ? f.getAnnotazione() +"" : "") + 
                (f.getModificatore() != null ? f.getModificatore() : "") + 
                (f.getKey() != null ? f.getKey() +" " : "") + 
                (f.getTipo() != null ? f.getTipo() +" " : "") + 
                (f.getNome() != null ? f.getNome() : "") +  
                ";\n\n";
        
        this.createGetter(f);
        this.createSetter(f);
      
        return this;
    }
    
    /*
    *Funzione che permette di aggiungere i getter
    */
    public ModelHelper addGetter()
    {
        this.classString += this.getter;
        return this;
    }
    
    /*
    *Funzione che permette di aggiungere i setter
    */
    public ModelHelper addSetter()
    {
        this.classString += this.setter;
        return this;
    }
    
    /*
    *Funzione che permette di aggiungere i getter e i setter
    */
    public ModelHelper addGetterAndSetter()
    {
        addGetter();
        addSetter();
        return this;
    }
    
    /*
    *Funzione che permette di terminare il processo di creazione
    */
    public ModelHelper terminate() throws Exception
    {
        this.classString += newLine(1) + closeBracket();
        
        creaFileJava(nomeTabella, classString, ConfigHelper.getPercorsoModel());
        return this;
    }
    
    /*
    * Funzione che aggiunge i getter al sorgente
    */
    public void createGetter(Field f)
    {
        this.getter += 
        "\n"+ 
        tab(1) + ConfigModel.PUBLIC +" "+ 
        (f.getTipo() != null ? f.getTipo() +" " : "") + 
        ConfigModel.GET+Utility.capitalizeFirstLetter(f.getNome().toLowerCase()) +"()" +
        newLine(1) + tab(1) + openBracket() +
        tab(2) + ConfigModel.RETURN +" "+ ConfigModel.THIS +"."+ f.getNome() +";"+
        newLine(1) + tab(1) + closeBracket(); 
    }
    
    /*
    * Funzione che aggiunge i setter al sorgente
    */
    public void createSetter(Field f)
    {
        this.setter += 
        "\n"+
        tab(1) + ConfigModel.PUBLIC +" "+ 
        ConfigModel.VOID +" "+ 
        ConfigModel.SET+Utility.capitalizeFirstLetter(f.getNome().toLowerCase()) +"("+ f.getTipo() +" "+ f.getNome() +")" +
        newLine(1) + tab(1) + openBracket()+
        tab(2) + ConfigModel.THIS +"."+ f.getNome() +" = "+ f.getNome()+";"+
        newLine(1) +  tab(1) + closeBracket(); 
    }
    
    /*
    * Funzione che aggiunge parentesi graffa aperta
    */
    public String openBracket()
    {
        return "{\n";
    }
    
    /*
    * Funzione che aggiunge parentesi graffa chiusa
    */
    public String closeBracket()
    {
        return "}\n";
    }
    
    /*
    * Funzione che aggiunge una nuova linea 
    */
    public String newLine(int n)
    {
        String s = "";
        for(int i = 0; i < n; i++)
        {
            s += "\n";
        }
        return s;
    }
    
    /*
    * Funzione che aggiunge una tabulazione
    */
    public String tab(int n)
    {
        String s = "";
        for(int i = 0; i < n; i++)
        {
            s += "    ";
        }
        return s;
    }
    
    
    //METODI GENERALI 
    
    /*
    * Metodo che data una lista di metadati ottenuta direttamente dalla query sfrutta il metodo "AddParameter" per aggiungere tutti i campi
    */
     public ModelHelper addParameters(JsonArray metadataTabella)
    {
        ArrayList<String> campiCreati = new ArrayList<>();

        for(JsonElement je : metadataTabella)
        {
            boolean found = false;
            JsonObject jo = je.getAsJsonObject();
            for(String s: campiCreati)
            {
                if(s.toLowerCase().equals(jo.get("column_name").getAsString().toLowerCase()))
                {
                    found = true;
                }
            }
            if(!found)
            {
                addParamater(new Field(addAnnotation(jo),ConfigModel.PUBLIC, null, ConfigModel.TYPE_MAPPING.get(jo.get("data_type").getAsString()),jo.get("column_name").getAsString()));            
                campiCreati.add(jo.get("column_name").getAsString());
            }
        }
        return this;
    }
     
     /*
     *Metodo che dal JsonObject di Metadati interpreta le annotazioni che vanno aggiunte
     */
     public ArrayList<String> addAnnotation(JsonObject jo)
     {
        ArrayList<String> listaAnnotazioni = new ArrayList<>();
        
        if(jo.get("column_key").getAsString().equals("PRI"))
        {
            listaAnnotazioni.add("@PrimaryKey");
        }
        
        if(jo.get("column_key").getAsString().equals("UNI"))
        {
            listaAnnotazioni.add("@Unique");
        }
        
        if(jo.get("is_nullable").getAsString().equals("NO"))
        {
            listaAnnotazioni.add("@NotNullable");
        }
        if(jo.get("extra").getAsString().equals("auto_increment"))
        {
            listaAnnotazioni.add("@AutoIncrement");
        }
        if(!jo.get("referenced_table_name").getAsString().equals("null"))
        {
            listaAnnotazioni.add("@ForeignKey(table=\""+jo.get("referenced_table_name").getAsString()+"\",column=\""+ jo.get("referenced_column_name").getAsString() +"\")");
        }
        return listaAnnotazioni;
     }
    
}



