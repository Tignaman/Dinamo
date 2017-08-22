/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ascompany.dinamo;

import Configurazione.ConfigName;
import Configurazione.ConfigWriter;
import Utility.ConfigHelper;
import static Utility.ConfigHelper.mandatoryDBConnectionParam;
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
    public static JsonObject DBConfigFile;
    
    public static Connection establishingConnection() throws Exception
    {
        /*Ricavato il percorso del file di configurazione per la connessione al database*/
        File file = new File(ConfigHelper.getPercorsoFileDBConnection());

        if(file.exists() && !file.isDirectory()) 
        { 
            DBConfigFile = Utility.convertFileToJson(file.getAbsolutePath());
            if(DBConfigFile != null)
            {
                System.out.println("Reading from database connection file");
                
                for (String param : mandatoryDBConnectionParam) 
                {
                   if(!DBConfigFile.has(param) || DBConfigFile.get(param).getAsString().equals(""))
                   {
                       throw new Exception("Attribute " + param + " not set or not found");
                   }
                }
                
                System.out.println("Establishing the connection to database");
                
                Class.forName(DBConfigFile.get(ConfigName.DRIVER).getAsString());
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
        else
        {
            /*Creazione della cartella*/
            file.getParentFile().mkdir();
            /*Creazione del file*/
            file.createNewFile();
            Utility.writeJsonToFs(ConfigWriter.DBConfigWriter(),ConfigHelper.getPercorsoFileDBConnection());
        }
        
        return null;
    }
}
