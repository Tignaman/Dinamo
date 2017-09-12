package ascompany.dinamo;

import ascompany.dinamo.Configurazione.ConfigModel;
import ascompany.dinamo.Configurazione.ConfigName;
import ascompany.dinamo.Configurazione.ConfigWriter;
import ascompany.dinamo.Database.Select;
import ascompany.dinamo.Configurazione.ConfigHelper;
import static ascompany.dinamo.Configurazione.ConfigHelper.mandatoryModelConfigParam;
import ascompany.dinamo.Classi.ModelHelper;
import ascompany.dinamo.Utility.Utility;
import static ascompany.dinamo.Utility.Utility.capitalizeFirstLetter;
import static ascompany.dinamo.Utility.Utility.deleteDir;
import ascompany.sinfonia.Core.DatabaseUtility;
import ascompany.sinfonia.Core.QueryCore;
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
    /**
     * File di configurazione
     */
    public static JsonObject DBConfigFile;
     
    /**
     * Metodo che viene utilizzato per generare le model
     * 
     * @param connection connessione
     * @throws Exception 
     */
    public static void GeneraModel(Connection connection) throws Exception
    {
        File file = new File(ConfigHelper.getPercorsoFileConfigModel());
        if (file.exists() && !file.isDirectory() && connection != null)
        {
            if (GestoreDB.DBConfigFile != null)
            {
                DBConfigFile = Utility.convertFileToJson(file.getAbsolutePath());
                for (String param : mandatoryModelConfigParam) 
                {
                   if(!DBConfigFile.has(param) || DBConfigFile.get(param).getAsString().equals(""))
                   {
                       throw new Exception("Attribute " + param + " not set or not found");
                   }
                }
                
                ConfigHelper.basePackage = DBConfigFile.get(ConfigName.MODEL_PACKAGE).getAsString();
                file = new File(ConfigHelper.getPercorsoModel());
                if(file.exists())
                {
                    deleteDir(file);
                }
                file.mkdir();
                QueryCore qc = Select.getTablesName(new ArrayList<>(Arrays.asList(GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString())));
                JsonArray listaTabelle = (JsonArray) qc.init(connection)
                        .buildQuery()
                        .executionQ()
                        .mapping(new DatabaseUtility().rsToJson)
                        .destroy()
                        .getResponse();

                JsonArray ignoreTables = DBConfigFile.get(ConfigName.IGNORE_TABLES).getAsJsonArray();
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
        else
        {
            file.getParentFile().mkdirs();
            file.createNewFile();
            Utility.writeJsonToFs(ConfigWriter.ModelConfigWriter(),ConfigHelper.getPercorsoFileConfigModel());
        }
    }
    
    /**
     * Funzione che viene utilizzata per creare il codice sorgente della Model
     * 
     * @param nomeTab nome della model da dover creare
     * @param connection connessione
     * @throws Exception 
     */
    public static void creaModel(String nomeTab, Connection connection) throws Exception
    {
        QueryCore qc = Select.getMetadataFromTable(new ArrayList<>(Arrays.asList(GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString(), nomeTab)));
        JsonArray metadataTabella = (JsonArray) qc.init(connection)
                .buildQuery()
                .executionQ()
                .mapping(new DatabaseUtility().rsToJson)
                .destroy()
                .getResponse();
        
        new ModelHelper()
                .toPackage(ConfigHelper.basePackage.replace("/", "."))
                .withAccessMode(ConfigModel.PUBLIC)
                .withKey(ConfigModel.CLASS)
                .className(capitalizeFirstLetter(nomeTab.toLowerCase()))
                .addParameters(metadataTabella)
                .addGetterAndSetter()
                .terminate();
    }
    
    
}
