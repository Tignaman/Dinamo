package ascompany.dinamo.Classi;

import static ascompany.cosmo.utility.Utility.removeLastChar;
import ascompany.dinamo.Configurazione.ConfigHelper;
import ascompany.dinamo.Configurazione.ConfigModel;
import ascompany.dinamo.Configurazione.ConfigName;
import ascompany.dinamo.Utility.Utility;
import static ascompany.dinamo.Utility.Utility.creaFileJava;
import static ascompany.dinamo.Utility.Utility.isStringPresent;
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
    /**
     * Nome della tabella corrispondente alla classe
     */
    public String nomeTabella = "";
    
    /**
     * Codice sorgente della classe
     */
    public String classString = "";
    
    /**
     * Codice sorgente del package
     */
    public String packageString = "";
    
    /**
     * Codice sorgente degli import
     */
    public String importString = "";
    
    /**
     * Codice sorgente del modificatore d'accesso
     */
    public String accessModeString = "";
    
    /**
     * Codice sorgente delle chiavi
     */
    public String withKeyString = "";
    
    /**
     * Codice sorgente del tipo di ritorno
     */
    public String returningString = "";
    
    /**
     * Codice sorgente del nome della classe
     */
    public String classNameString = "";
    
    /**
     * Codice sorgente della lista dei camp
     */
    public String parametersString = "";
    
    /**
     * Codice sorgente dei getter
     */
    public String getterString = "";
    
    /**
     * Codice sorgente dei setter
     */
    public String setterString = "";
    
    /*
    * Codice sorgente dei getter
    */
    public String getter = "";
    
    /*
    * Codice sorgente dei setter
    */
    public String setter = "";
    
    /**
     * Codice sorgente delle interfacce
     */
    public String interfaces = "";
    
    /**
     * Codice sorgente dell'extends
     */
    public String extending = "";
        
    /**
     * 
     * @param pkg package della classe
     * @return Istanza di classe
     */
    public ModelHelper toPackage(String pkg)
    {
        this.packageString = "package " + pkg +";";
        addDependencies();
        return this;
    }
    
    /**
     * Funzione che permette di specificare gli import
     */
    public void addDependencies()
    {
        this.importString += ConfigModel.IMPORT +" "+ ConfigName.BASE_PACKAGE +"."+ ConfigName.ANNOTAZIONI+".*;" + newLine(1) ;
        this.importString += ConfigModel.IMPORT +" "+ ConfigName.JAVA_SQL_DATE +";"+ newLine(1) ;
    }

    /**
     * Funzione che permette di aggiungere una singola dipendenza
     */
    public ModelHelper addDependency(ArrayList<String> listPkg)
    {
        for(String p : listPkg)
        {
            this.importString += ConfigModel.IMPORT +" "+ p +";" + newLine(1);
        }
        return this;
        
    }
    
    /**
     * 
     * @param mode modificatore d'accesso della classe
     * @return Istanza di classe
     */
    public ModelHelper withAccessMode(String mode)
    {
        this.accessModeString += mode + " ";
        return this;
    }
    
    /**
     * 
     * @param key lista di chiavi
     * @return Istanza di classe
     */
    public ModelHelper withKey(String ...key)
    {
        for(String k : key)
        {
            this.withKeyString += k + " ";
        }       
        return this;
    }
    
    /**
     * 
     * @param type tipo di ritorno
     * @return Istanza di classe
     */
    public ModelHelper returning(String type)
    {
        this.returningString += type + " ";
        return this;
    }
    
    public ModelHelper extending(String extending)
    {
        this.extending = extending;
        return this;
    }
    
    /**
     * 
     * @param interfaces stringa delle interfacce
     * @return 
     */
    public ModelHelper implementing(String interfaces)
    {
        this.interfaces = interfaces;
        return this;
    }
    
    /**
     * 
     * @param name nome della classe
     * @return Istanza di classe
     */
    public ModelHelper className(String name)
    {
        this.nomeTabella = name;
        this.classNameString += 
                name + 
                (extending.length()  > 0? " extends " + extending : "") +
                (interfaces.length() > 0? " implements " + removeLastChar(interfaces) : "") + 
                newLine(1) + 
                openBracket();
        return this;
    }
    
    
    
    /**
     * 
     * @param f Field che deve essere aggiunto alla classe
     * @return Istanza di classe
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
    
    /**
     * 
     * @return Istanza di classe
     */
    public ModelHelper addGetter()
    {
        this.getterString += this.getter;
        return this;
    }
    
    /**
     * 
     * @return Istanza di classe
     */
    public ModelHelper addSetter()
    {
        this.setterString += this.setter;
        return this;
    }
    
    /**
     * 
     * @return Istanza di classe
     */
    public ModelHelper addGetterAndSetter()
    {
        addGetter();
        addSetter();
        return this;
    }
    
    /**
     * 
     * @return Istanza di classe
     * @throws Exception 
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
    
    /**
     * 
     * @param f Field di cui si deve creare il get
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
    
    /**
     * 
     * @param f Field di cui si deve creare il set
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
    
    /**
     * 
     * @return parentesi aperta
     */
    public String openBracket()
    {
        return "{\n";
    }
    
    /**
     * 
     * @return parentesi chiusa
     */
    public String closeBracket()
    {
        return "}\n";
    }
    
    /**
     * 
     * @param n numero di nuove linee
     * @return nuova linea
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
    
    /**
     * 
     * @param n numero di tab
     * @return tab
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
    
    /**
     * 
     * @param metadataTabella Metadati di una tabella da cui vengono ricavate le informazioni
     * @return Istanza di classe
     * @throws Exception 
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
                if(tipo == null)
                {
                    throw new Exception("Type: " + jo.get("data_type").getAsString() + " not present");
                    
                }
                
                String customPackage = "";
                JsonArray customAnnotation = new JsonArray();
                
                for(JsonElement jmap : mappingTable)
                {
                    if(jmap.getAsJsonObject().get(ConfigName.ATTRIBUTE_NAME).getAsString().toLowerCase().equals(jo.get("column_name").getAsString().toLowerCase()))
                    {
                        if(jmap.getAsJsonObject().get(ConfigName.TYPE).getAsString().trim().length() > 0)
                        {
                            tipo = jmap.getAsJsonObject().get(ConfigName.TYPE).getAsString();
                        }
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
                if(isBaseType == false && !customPackage.equals(""))
                {
                    this.importString += ConfigModel.IMPORT +" "+ customPackage +"."+ tipo +";" + newLine(1);
                }
                
                
                
                addParamater(new Field(addAnnotation(jo,customAnnotation),ConfigModel.PUBLIC, null, tipo ,jo.get("column_name").getAsString()));            
                campiCreati.add(jo.get("column_name").getAsString());
            }
        }
        return this;
    }
     
     /**
      * 
      * @param jo JsonObject da cui vengono ricavati le informazioni
      * @param customAnnotation JsonArray di ulteriori annotazioni che vengono aggiunte
      * @return 
      */
     public ArrayList<String> addAnnotation(JsonObject jo,JsonArray customAnnotation)
     {
        JsonArray ignoreAnnotations = DBConfigFile.get(ConfigName.IGNORE_ANNOTATIONS).getAsJsonArray();
        ArrayList<String> listaAnnotazioni = new ArrayList<>();
        
        if(jo.get("column_key").getAsString().equals("PRI") || jo.get("column_key").getAsString().equals("PK"))
        {
            if(!isStringPresent(ignoreAnnotations,ConfigName.ANNOTATION_NAME,"PrimaryKey"))
            {
                listaAnnotazioni.add("@PrimaryKey");
            }
        }
        if(jo.get("column_key").getAsString().equals("UNI") || jo.get("column_key").getAsString().equals("UQ"))
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
            if(!je.getAsJsonObject().get(ConfigName.ANNOTATION_NAME).getAsString().equals(""))
            {
                listaAnnotazioni.add("@"+je.getAsJsonObject().get(ConfigName.ANNOTATION_NAME).getAsString());
                this.importString += ConfigModel.IMPORT +" "+ je.getAsJsonObject().get(ConfigName.PACKAGE).getAsString() +"."+ je.getAsJsonObject().get(ConfigName.ANNOTATION_NAME).getAsString() +";" + newLine(1) ;   
            }
            
        }
        return listaAnnotazioni;
     }
    
}



