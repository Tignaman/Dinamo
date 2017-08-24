package Classi;

import Classi.Field;
import Configurazione.ConfigHelper;
import Configurazione.ConfigModel;
import Configurazione.ConfigName;
import Preprocessore.ProcessoreAnnotazioni;
import Utility.Utility;
import static Utility.Utility.creaFileJava;
import static Utility.Utility.isClassPresent;
import static Utility.Utility.isStringPresent;
import static ascompany.dinamo.GestoreModel.DBConfigFile;
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
    
    public String packageString = "";
    public String importString = "";
    public String accessModeString = "";
    public String withKeyString = "";
    public String returningString = "";
    public String classNameString = "";
    public String parametersString = "";
    public String getterString = "";
    public String setterString = "";
    
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
        this.packageString = "package " + pkg +";";
        addDependencies();
        return this;
    }
    
    /*
    *Funzione che permette di specificare gli import
    */
    public void addDependencies()
    {
        this.importString += ConfigModel.IMPORT +" "+ ConfigName.ANNOTAZIONI+".*;" + newLine(1) ;
        this.importString += ConfigModel.IMPORT +" "+ ConfigName.JAVA_SQL_DATE+";" + newLine(1) ;
    }
    
    /*
    *Funzione che permette di specificare il modificatore d'accesso
    */
    public ModelHelper withAccessMode(String mode)
    {
        this.accessModeString += mode + " ";
        return this;
    }
    
    /*
    *Funzione che permette di specificare le chiavi
    */
    public ModelHelper withKey(String ...key)
    {
        for(String k : key)
        {
            this.withKeyString += k + " ";
        }       
        return this;
    }
    
    /*
    *Funzione che permette di specificare il tipo di ritorno
    */
    public ModelHelper returning(String type)
    {
        this.returningString += type + " ";
        return this;
    }
    
    /*
    *Funzione che permette di specificare il nome della classe
    */
    public ModelHelper className(String name)
    {
        this.nomeTabella = name;
        this.classNameString += name + newLine(1) + openBracket();
        return this;
    }
    
    /*
    *Funzione che permette di aggiungere un campo e che riempie i getter e i setter
    */
    public ModelHelper addParamater(Field f)
    {
        this.parametersString += tab(1) +
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
        this.getterString += this.getter;
        return this;
    }
    
    /*
    *Funzione che permette di aggiungere i setter
    */
    public ModelHelper addSetter()
    {
        this.setterString += this.setter;
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
        this.classString = 
        this.packageString + newLine(2) +
        this.importString + newLine(1) +
                
        this.accessModeString + this.withKeyString + this.returningString + this.classNameString +
        
        this.parametersString +
        this.getterString +
        this.setterString +
                
        this.classString + newLine(1) + closeBracket();
        
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
     public ModelHelper addParameters(JsonArray metadataTabella) throws Exception
    {
        /*Viene preso il JsonArray nella quale sono specificate le annotazioni da dover ignorare*/
        JsonArray tableSpefication = DBConfigFile.get(ConfigName.TABLE_SPECIFICATION).getAsJsonArray();
        
        /*Viene creato l'oggetto che conterrà il JsonMapping*/
        JsonArray mappingTable = new JsonArray();
        
        for(JsonElement table : tableSpefication)
        {
            if(table.getAsJsonObject().get(ConfigName.TABLE_NAME).getAsString().toLowerCase().equals(this.nomeTabella.toLowerCase()))
            {
                mappingTable = table.getAsJsonObject().get(ConfigName.MAPPING).getAsJsonArray();
            }
        }
        
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
                String tipo = ConfigModel.TYPE_MAPPING.get(jo.get("data_type").getAsString());
                String customPackage = "";
                JsonArray customAnnotation = new JsonArray();
                
                for(JsonElement jmap : mappingTable)
                {
                    if(jmap.getAsJsonObject().get(ConfigName.ATTRIBUTE_NAME).getAsString().toLowerCase().equals(jo.get("column_name").getAsString().toLowerCase()))
                    {
                        tipo = jmap.getAsJsonObject().get(ConfigName.TYPE).getAsString();
                        customPackage = jmap.getAsJsonObject().get(ConfigName.PACKAGE).getAsString();
                        customAnnotation = jmap.getAsJsonObject().get(ConfigName.CUSTOM_ANNOTATION).getAsJsonArray();
                    }
                }
                
                boolean isBaseType = false;
                for(String baseType : ConfigModel.BASE_TYPE)
                {
                    if(tipo.equals(baseType))
                    {
                        isBaseType = true;
                    }
                }
                if(isBaseType == false)
                {
                    this.importString += ConfigModel.IMPORT +" "+ customPackage +"."+ tipo +";" + newLine(1) ;
                }
                
                addParamater(new Field(addAnnotation(jo,customAnnotation),ConfigModel.PUBLIC, null, tipo ,jo.get("column_name").getAsString()));            
                campiCreati.add(jo.get("column_name").getAsString());
            }
        }
        return this;
    }
     
     /*
     *Metodo che dal JsonObject di Metadati interpreta le annotazioni che vanno aggiunte
     */
     public ArrayList<String> addAnnotation(JsonObject jo,JsonArray customAnnotation)
     {
        /*Viene preso il JsonArray nella quale sono specificate le annotazioni da dover ignorare*/
        JsonArray ignoreAnnotations = DBConfigFile.get(ConfigName.IGNORE_ANNOTATIONS).getAsJsonArray();
        
        ArrayList<String> listaAnnotazioni = new ArrayList<>();
        
        if(jo.get("column_key").getAsString().equals("PRI"))
        {
            if(!isStringPresent(ignoreAnnotations,ConfigName.ANNOTATION_NAME,"PrimaryKey"))
            {
                listaAnnotazioni.add("@PrimaryKey");
            }
        }
        if(jo.get("column_key").getAsString().equals("UNI"))
        {
            if(!isStringPresent(ignoreAnnotations,ConfigName.ANNOTATION_NAME,"Unique"))
            {
                listaAnnotazioni.add("@Unique");
            }
        }
        if(jo.get("is_nullable").getAsString().equals("NO"))
        {
            if(!isStringPresent(ignoreAnnotations,ConfigName.ANNOTATION_NAME,"NotNullable"))
            {
                listaAnnotazioni.add("@NotNullable");
            }
        }
        if(jo.get("extra").getAsString().equals("auto_increment"))
        {
            if(!isStringPresent(ignoreAnnotations,ConfigName.ANNOTATION_NAME,"AutoIncrement"))
            {
                listaAnnotazioni.add("@AutoIncrement");
            }
        }
        if(!jo.get("referenced_table_name").getAsString().equals("null"))
        {
            if(!isStringPresent(ignoreAnnotations,ConfigName.ANNOTATION_NAME,"ForeignKey"))
            {
                listaAnnotazioni.add("@ForeignKey(table=\""+jo.get("referenced_table_name").getAsString()+"\",column=\""+ jo.get("referenced_column_name").getAsString() +"\")");
            }
        }
        
        for(JsonElement je : customAnnotation)
        {
            listaAnnotazioni.add("@"+je.getAsJsonObject().get(ConfigName.ANNOTATION_NAME).getAsString());
            this.importString += ConfigModel.IMPORT +" "+ je.getAsJsonObject().get(ConfigName.PACKAGE).getAsString() +"."+ je.getAsJsonObject().get(ConfigName.ANNOTATION_NAME).getAsString() +";" + newLine(1) ;   
        }
        
        return listaAnnotazioni;
     }
    
}



