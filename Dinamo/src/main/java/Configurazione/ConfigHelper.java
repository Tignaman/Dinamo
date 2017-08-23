package Configurazione;

import Configurazione.ConfigName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author n.dipietro
 */
public class ConfigHelper
{
    /*
    * ArrayList che contiene i campi obbligatori per quanto riguarda il file di configurazione della connessione al database
    */
    public static ArrayList<String> mandatoryDBConnectionParam = new ArrayList<>(Arrays.asList
    (
       ConfigName.IP,ConfigName.PORT,ConfigName.DRIVER,ConfigName.DATABASE,ConfigName.USERNAME     
    ));
    
    /*
    *ArrayList che contiene i campi obbligatori per quanto riguarda il file di configurazione delle Model
    */
    public static ArrayList<String> mandatoryModelConfigParam = new ArrayList<>(Arrays.asList
    (
        ConfigName.MODEL_PATH
    ));
    
    
    /*Variabile che contiene il basepath*/
    public static String basePath = "";
    
    /*Variabile che contiene il nome del package nella quale c'è l'annotazione*/
    public static String basePackage = "";

    /*Funzione che ritorna il percorso del file di configurazione della connessione al database*/
    public static String getPercorsoFileDBConnection() throws IOException
    {
        return ConfigHelper.basePath+ "/" + ConfigName.PKG + "/" + ConfigName.CONFIG_DIR + "/" + ConfigName.FILE_DB_CONNECTION ;
    }
    
    /*Funzione che ritorna il percorso del file di configurazione della connessione al database*/
    public static String getPercorsoFileConfigModel() throws IOException
    {
        return ConfigHelper.basePath+ "/" + ConfigName.PKG + "/" + ConfigName.CONFIG_DIR + "/" + ConfigName.CONFIG_MODEL ;
    }
    
    /*Funzione che ritorna il percorso dove deve essere salvata la model*/
    public static String getPercorsoModel() throws IOException
    {
        return ConfigHelper.basePackage+ "/" + ConfigName.PKG + "/" + ConfigName.MODEL_DIR ;
    }
    
    
    
    
}
