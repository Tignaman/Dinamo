package Configurazione;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * @author n.dipietro
 */
public class ConfigWriter
{
    /*
    * Funzione che viene utilizzata per inizializzare il file di configurazione della connessione al database
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
    
    /*
    * Funzione che viene utilizzata per inizializzare il file di configurazione delle Model
    */
    public static JsonObject ModelConfigWriter()
    {
        /*Creato JsonObject generale*/
        JsonObject modelConfig = new JsonObject();
        
        /*Viene aggiunta la proprietà MODEL_PATH*/
        modelConfig.addProperty(ConfigName.MODEL_PATH,"");
        
        
        /***********************************************************************************************************************************************/
        
        
        /*Viene creato il JsonArray della lista di tabelle da escludere*/
        JsonArray listaTabelleDaEscludere = new JsonArray();
        
        /*Viene aggiunto un JsonObject come linea guida*/
        JsonObject tabellaDaEscludere = new JsonObject();
        
        /*VIene aggiunto al JsonObject la proprietà TABLE_NAME*/
        tabellaDaEscludere.addProperty(ConfigName.TABLE_NAME, "");
        
        /*Viene aggiunto il JsonObject al JsonArray*/
        listaTabelleDaEscludere.add(tabellaDaEscludere);
        
        /*Viene aggiunta la proprietà IGNORE_TABLES*/
        modelConfig.add(ConfigName.IGNORE_TABLES, listaTabelleDaEscludere);
        
        
        /***********************************************************************************************************************************************/
        
        
        /*Viene creato il JsonArray della lista di annotazione da escludere*/
        JsonArray listaAnnotazioniDaEscludere = new JsonArray();
        
        /*Viene aggiunto un JsonObject come linea guida*/
        JsonObject annotazioneDaEscludere = new JsonObject();
        
        /*Viene aggiunto al JsonObjhect la prorietà ANNOTATION_NAME*/
        annotazioneDaEscludere.addProperty(ConfigName.ANNOTATION_NAME, "");
        
        /*Viene aggiunto il JsonObject al JsonArray*/
        listaAnnotazioniDaEscludere.add(annotazioneDaEscludere);
        
        /*Viene aggiunta la proprietà IGNORE_ANNOTATIONS*/
        modelConfig.add(ConfigName.IGNORE_ANNOTATIONS, listaAnnotazioniDaEscludere);
        
        /*Viene creato il JsonArray della lista dei dettagli delle tabelle*/ 
        JsonArray tableSpefication = new JsonArray();
        
        /*Viene aggiunto un JsonObject come linea guida*/
        JsonObject tabella = new JsonObject();
        
        /*Aggiungiamo la proprietà TABLE_NAME all'oggetto tabella*/
        tabella.addProperty(ConfigName.TABLE_NAME,"");
        
        /*Creiamo l'array di mapping*/
        JsonArray listaMapping = new JsonArray();
        
        /*Creiamo l'oggetto di mapping*/
        JsonObject mapping = new JsonObject();
        
        /*Aggiungiamo a questo la proprietà ATTRIBUTE_NAME*/
        mapping.addProperty(ConfigName.ATTRIBUTE_NAME,"");
        
        /*Aggiungiamo a questo la proprietà TYPE*/
        mapping.addProperty(ConfigName.TYPE, "");
        
        /*Aggiungiamo a questo la proprietà PACKAGE*/
        mapping.addProperty(ConfigName.PACKAGE, "");
        
        JsonArray listaCustomAnnotations = new JsonArray();
        JsonObject annotation = new JsonObject();
        annotation.addProperty(ConfigName.ANNOTATION_NAME,"");
        annotation.addProperty(ConfigName.PACKAGE,"");
        listaCustomAnnotations.add(annotation);
        mapping.add(ConfigName.CUSTOM_ANNOTATION,listaCustomAnnotations);
        
        
        /*Aggiungiamo il mapping alla lista dei mapping*/
        listaMapping.add(mapping);
        
        
        /*Aggiungiamo la lista all'oggetto tabella*/
        tabella.add(ConfigName.MAPPING,listaMapping);
        
        /*Aggiungiamo tabella all'oggetto table specification*/
        tableSpefication.add(tabella);
        
        /*Viene aggiunta la proprietà TABLE_SPECIFICATION*/
        modelConfig.add(ConfigName.TABLE_SPECIFICATION, tableSpefication);
        
        return modelConfig;
    }
}
