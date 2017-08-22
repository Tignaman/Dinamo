package Utility;

import Configurazione.ConfigName;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author n.dipietro
 */
public class ConfigHelper
{
    public static ArrayList<String> mandatoryDBConnectionParam = new ArrayList<>(Arrays.asList
    (
       ConfigName.IP,ConfigName.PORT,ConfigName.DRIVER,ConfigName.DATABASE,ConfigName.USERNAME     
    ));
    
    /*Variabile che contiene il basepath*/
    public static String basePath = "";

    /*Funzione che ritorna il percorso del file di configurazione della connessione al database*/
    public static String getPercorsoFileDBConnection() throws IOException
    {
        return ConfigHelper.basePath+ "/" + ConfigName.PKG + "/" + ConfigName.FILE_DB_CONNECTION ;
    }
    
    /*Funzione che ritorna il percorso del file di configurazione della connessione al database*/
    public static String getPercorsoFileConfigModel() throws IOException
    {
        return ConfigHelper.basePath+ "/" + ConfigName.PKG + "/" + ConfigName.CONFIG_MODEL ;
    }
    
    
}
