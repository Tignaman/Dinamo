package ascompany.dinamo;

import Configurazione.ConfigModel;
import Configurazione.ConfigName;
import Configurazione.ConfigWriter;
import Database.DatabaseUtility;
import Database.QueryCore;
import Database.Select;
import Configurazione.ConfigHelper;
import static Configurazione.ConfigHelper.mandatoryModelConfigParam;
import Classi.ModelHelper;
import Utility.Utility;
import static Utility.Utility.capitalizeFirstLetter;
import static Utility.Utility.deleteDir;
import static Utility.Utility.getStringAfterLastChar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author n.dipietro
 */
public class GestoreModel
{
    /*
    * File di configurazione
    */
    public static JsonObject DBConfigFile;
     
    /*
    * Metodo che viene utilizzato per generare le model
    */
    public static void GeneraModel(Connection connection) throws Exception
    {
        /*Ricavato il percorso del file di configurazione per la connessione al database*/
        File file = new File(ConfigHelper.getPercorsoFileConfigModel());

        /*Viene controllato se il file esiste*/
        if (file.exists() && !file.isDirectory() && connection != null)
        {
            /*
            *Se il file di configurazione è diverso da null
            */
            if (GestoreDB.DBConfigFile != null)
            {
                /*
                * Viene convertito il file in un JsonObject
                */
                DBConfigFile = Utility.convertFileToJson(file.getAbsolutePath());
                
                /*
                * Vengono controllati tutte le key obbligatorie
                */
                for (String param : mandatoryModelConfigParam) 
                {
                   if(!DBConfigFile.has(param) || DBConfigFile.get(param).getAsString().equals(""))
                   {
                       throw new Exception("Attribute " + param + " not set or not found");
                   }
                }
                
                /*
                * Viene settato il basePackage per quanto riguarda il percorso dove andranno create le Model
                */
                ConfigHelper.basePackage = DBConfigFile.get(ConfigName.MODEL_PATH).getAsString();
                 
                /*Viene ricavato il percorso del file*/
                file = new File(ConfigHelper.getPercorsoModel());
                
                /*Se esiste viene svuotato*/
                if(file.exists())
                {
                    deleteDir(file);
                }
                
                /*Viene ricreata la cartella*/
                file.mkdir();
                
                /*Ricavata la query che serve a prendere le tabelle dal database*/
                QueryCore qc = Select.getTablesName(new ArrayList<>(Arrays.asList(GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString())));
                JsonArray listaTabelle = (JsonArray) qc.init(connection)
                        .buildQuery()
                        .executionQ()
                        .mapping(new DatabaseUtility().rsToJson)
                        .destroy()
                        .getResponse();

                /*Viene preso il JsonArray nella quale sono specificate le classi da dover ignorare*/
                JsonArray ignoreTables = DBConfigFile.get(ConfigName.IGNORE_TABLES).getAsJsonArray();
                
                /*Per ogni tabella viene controllata se non è presente in quelle da dover ignorare. In tal caso viene creata.*/
                for(JsonElement je : listaTabelle)
                {
                    boolean found = false;
                    for(JsonElement ignoredTable : ignoreTables)
                    {
                        if(je.getAsJsonObject().get("table_name").getAsString().toLowerCase().equals(ignoredTable.getAsJsonObject().get(ConfigName.TABLE_NAME).getAsString().toLowerCase()))
                        {
                            found = true;
                        }
                    }
                    if(!found)
                    {
                        creaModel(je.getAsJsonObject().get("table_name").getAsString(),connection);
                    }
                }
            }
            else
            {
                throw new Exception("Database connection file not found.");
            }
        }
        /*Altrimenti viene creato il file di configurazione*/
        else
        {
            /*Creazione della cartella*/
            file.getParentFile().mkdirs();
            
            /*Creazione del file*/
            file.createNewFile();
            
            /*Viene scritto il JsonObject nel File di configurazione al path indicato*/
            Utility.writeJsonToFs(ConfigWriter.ModelConfigWriter(),ConfigHelper.getPercorsoFileConfigModel());
        }
    }
    
    /*Funzione che viene utilizzata per creare il codice sorgente della Model*/
    public static void creaModel(String nomeTab, Connection connection) throws Exception
    {
        /*Query per ricavare i metadati di una tabella*/
        QueryCore qc = Select.getMetadataFromTable(new ArrayList<>(Arrays.asList(GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString(), nomeTab)));
        JsonArray metadataTabella = (JsonArray) qc.init(connection)
                .buildQuery()
                .executionQ()
                .mapping(new DatabaseUtility().rsToJson)
                .destroy()
                .getResponse();
        
        /*Generazione del codice sorgente della Model*/
        new ModelHelper()
                .toPackage(getStringAfterLastChar(ConfigHelper.basePackage,"\\") +"."+ ConfigName.PKG +"."+ ConfigName.MODEL_DIR)
                .withAccessMode(ConfigModel.PUBLIC)
                .withKey(ConfigModel.CLASS)
                .className(capitalizeFirstLetter(nomeTab.toLowerCase()))
                .addParameters(metadataTabella)
                .addGetterAndSetter()
                .terminate();
    }
    
    
}
