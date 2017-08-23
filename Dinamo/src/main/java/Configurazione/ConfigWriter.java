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
        
        /*Viene creato il JsonArray della lista di tabelle da escludere*/
        JsonArray listaDaEscludere = new JsonArray();
        
        /*Viene aggiunto un JsonObject come linea guida*/
        JsonObject tabellaDaEscludere = new JsonObject();
        
        /*VIene aggiunto al JsonObject la proprietà TABLE_NAME*/
        tabellaDaEscludere.addProperty(ConfigName.TABLE_NAME, "");
        
        /*Viene aggiunto il JsonObject al JsonArray*/
        listaDaEscludere.add(tabellaDaEscludere);
        
        /*VIene aggiunta la proprietà IGNORE_TABLES*/
        modelConfig.add(ConfigName.IGNORE_TABLES, listaDaEscludere);
        
        
        return modelConfig;
    }
}
