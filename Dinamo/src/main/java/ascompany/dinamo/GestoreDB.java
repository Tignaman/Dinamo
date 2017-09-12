package ascompany.dinamo;

import ascompany.dinamo.Configurazione.ConfigWriter;
import ascompany.dinamo.Configurazione.ConfigHelper;
import static ascompany.dinamo.Configurazione.ConfigHelper.mandatoryDBConnectionParam;
import ascompany.dinamo.Utility.Utility;
import ascompany.sinfonia.Core.ConnectionCore;
import com.google.gson.JsonObject;
import java.io.File;
import java.sql.Connection;

/**
 *
 * @author n.dipietro
 */
public class GestoreDB
{
    /**
     * JsonObject del file di configurazione
     */
    public static JsonObject DBConfigFile;
    
    /**
     * Funzione che serve ad instaurare la connessione col database
     * 
     * @return connessione
     * @throws Exception 
     */
    public static Connection establishingConnection() throws Exception
    {   
        File file = new File(ConfigHelper.getPercorsoFileDBConnection());
        if(file.exists() && !file.isDirectory()) 
        { 
            DBConfigFile = Utility.convertFileToJson(file.getAbsolutePath());
            if(DBConfigFile != null)
            {
                for (String param : mandatoryDBConnectionParam) 
                {
                   if(!DBConfigFile.has(param) || DBConfigFile.get(param).getAsString().equals(""))
                   {
                       throw new Exception("Attribute " + param + " not set or not found");
                   }
                }
                
                System.out.println("Establishing the connection to database");
                
                ConnectionCore cCore = new ConnectionCore().openConnection(file.getAbsolutePath());
                
                return cCore.getConnection();
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
            Utility.writeJsonToFs(ConfigWriter.DBConfigWriter(),ConfigHelper.getPercorsoFileDBConnection());
        }
        return null;
    }
}
