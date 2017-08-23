package ascompany.dinamo;

import Configurazione.ConfigName;
import Configurazione.ConfigWriter;
import Configurazione.ConfigHelper;
import static Configurazione.ConfigHelper.mandatoryDBConnectionParam;
import Utility.Utility;
import com.google.gson.JsonObject;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author n.dipietro
 */
public class GestoreDB
{
    /*
    * JsonObject del file di configurazione
    */
    public static JsonObject DBConfigFile;
    
    /*
    * Funzione che serve ad instaurare la connessione col database
    */
    public static Connection establishingConnection() throws Exception
    {
        /*Ricavato il percorso del file di configurazione per la connessione al database*/
        File file = new File(ConfigHelper.getPercorsoFileDBConnection());
        
        /*Controllo se il file esiste e non è una directory*/
        if(file.exists() && !file.isDirectory()) 
        { 
            /*Il file viene convertito in Json e viene controllato se è diverso da null*/
            DBConfigFile = Utility.convertFileToJson(file.getAbsolutePath());
            if(DBConfigFile != null)
            {
                System.out.println("Reading from database connection file");
                
                /*Vengono controllate le chiavi obbligatorie*/
                for (String param : mandatoryDBConnectionParam) 
                {
                   if(!DBConfigFile.has(param) || DBConfigFile.get(param).getAsString().equals(""))
                   {
                       throw new Exception("Attribute " + param + " not set or not found");
                   }
                }
                
                System.out.println("Establishing the connection to database");
                
                /*Viene ricavato il driver per la connessione*/
                Class.forName(DBConfigFile.get(ConfigName.DRIVER).getAsString());
                
                /*Viene effettuata la connessione con i parametri ricavati dal file di configurazione*/
                Connection connection = DriverManager.getConnection
                (
                    (
                        DBConfigFile.get(ConfigName.IP).getAsString() + ":" + 
                        DBConfigFile.get(ConfigName.PORT).getAsString() + 
                        ConfigName.PREFIX_TIMEZONE + 
                        DBConfigFile.get(ConfigName.TIMEZONE).getAsString()
                    ),    
                    DBConfigFile.get(ConfigName.USERNAME).getAsString(),
                    DBConfigFile.get(ConfigName.PASSWORD).getAsString()
                );
                
                return connection;
                
            }
            else
            {
                throw new Exception("Database connection file not found.");
            }
        }
        /*Se il file non esiste viene creato*/
        else
        {
            /*Creazione della cartella*/
            file.getParentFile().mkdirs();
            
            /*Creazione del file*/
            file.createNewFile();
            
            /*Viene scritto il JsonObject su filesystem al path indicato*/
            Utility.writeJsonToFs(ConfigWriter.DBConfigWriter(),ConfigHelper.getPercorsoFileDBConnection());
        }
        
        return null;
    }
}
