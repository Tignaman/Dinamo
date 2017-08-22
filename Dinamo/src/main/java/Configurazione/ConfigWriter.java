package Configurazione;

import com.google.gson.JsonObject;

/**
 *
 * @author n.dipietro
 */
public class ConfigWriter
{
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
    
    public static JsonObject ModelConfigWriter()
    {
        JsonObject dbConfig = new JsonObject();
        
        
        return dbConfig;
    }
}
