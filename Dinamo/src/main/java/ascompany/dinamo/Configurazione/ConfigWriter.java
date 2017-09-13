package ascompany.dinamo.Configurazione;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * @author n.dipietro
 */
public class ConfigWriter
{
    /**
     * 
     * @return JsonObject del file di configurazione della connessione al database
     */
    public static JsonObject DBConfigWriter()
    {
        JsonObject dbConfig = new JsonObject();
        dbConfig.addProperty(ConfigName.DRIVER, "");
        dbConfig.addProperty(ConfigName.IP, "");
        dbConfig.addProperty(ConfigName.PORT, "");
        dbConfig.addProperty(ConfigName.USERNAME, "");
        dbConfig.addProperty(ConfigName.PASSWORD, "");
        dbConfig.addProperty(ConfigName.DATABASE, "");
        dbConfig.addProperty(ConfigName.TIMEZONE, "");
        return dbConfig;
    }
    
    /**
     * 
     * @return JsonObject del file di configurazioned delle Model
     */
    public static JsonObject ModelConfigWriter()
    {
        JsonObject modelConfig = new JsonObject();
        modelConfig.addProperty(ConfigName.MODEL_PACKAGE,"");
        
        
        /***********************************************************************************************************************************************/
        
        
        JsonArray listaTabelleDaEscludere = new JsonArray();
        JsonObject tabellaDaEscludere = new JsonObject();
        tabellaDaEscludere.addProperty(ConfigName.TABLE_NAME, "");
        listaTabelleDaEscludere.add(tabellaDaEscludere);
        modelConfig.add(ConfigName.IGNORE_TABLES, listaTabelleDaEscludere);
        
        
        /***********************************************************************************************************************************************/
        
        
        JsonArray listaAnnotazioniDaEscludere = new JsonArray();
        JsonObject annotazioneDaEscludere = new JsonObject();
        annotazioneDaEscludere.addProperty(ConfigName.ANNOTATION_NAME, "");
        listaAnnotazioniDaEscludere.add(annotazioneDaEscludere);
        modelConfig.add(ConfigName.IGNORE_ANNOTATIONS, listaAnnotazioniDaEscludere);
        JsonArray tableSpefication = new JsonArray();
        JsonObject tabella = new JsonObject();
        tabella.addProperty(ConfigName.TABLE_NAME,"");
        JsonArray listaMapping = new JsonArray();
        JsonObject mapping = new JsonObject();
        mapping.addProperty(ConfigName.ATTRIBUTE_NAME,"");
        mapping.addProperty(ConfigName.TYPE, "");
        mapping.addProperty(ConfigName.PACKAGE, "");
        
        JsonArray listaCustomAnnotations = new JsonArray();
        JsonObject annotation = new JsonObject();
        annotation.addProperty(ConfigName.ANNOTATION_NAME,"");
        annotation.addProperty(ConfigName.PACKAGE,"");
        listaCustomAnnotations.add(annotation);
        mapping.add(ConfigName.CUSTOM_ANNOTATION,listaCustomAnnotations);
        
        listaMapping.add(mapping);
        
        JsonObject extending = new JsonObject();
        extending.addProperty(ConfigName.CLASS, "");
        extending.addProperty(ConfigName.PACKAGE, "");
        
        tabella.add(ConfigName.EXTEND, extending);
       
        JsonArray listaCustomInterfaces = new JsonArray();
        JsonObject interfaces = new JsonObject();
        interfaces.addProperty(ConfigName.INTERFACE_NAME, "");
        interfaces.addProperty(ConfigName.PACKAGE,"");
        listaCustomInterfaces.add(interfaces);
        tabella.add(ConfigName.CUSTOM_INTERFACE,listaCustomInterfaces);
        
        tabella.add(ConfigName.MAPPING,listaMapping);
        
        tableSpefication.add(tabella);
        modelConfig.add(ConfigName.TABLE_SPECIFICATION, tableSpefication);
        return modelConfig;
    }
}
