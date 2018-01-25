package ascompany.dinamo.Database;

import ascompany.dinamo.Configurazione.ConfigHelper;
import ascompany.dinamo.Configurazione.ConfigName;
import ascompany.dinamo.GestoreDB;
import ascompany.dinamo.Utility.Utility;
import ascompany.sinfonia.Core.QueryCore;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author marcocastano
 */
public class QuerySwitcher
{
    public static QueryCore getDatabaseTable() throws IOException
    {
        File fileConnection = new File(ConfigHelper.getPercorsoFileDBConnection());
        JsonObject dbConnection = Utility.convertFileToJson(fileConnection.getAbsolutePath());
        String dpType = dbConnection.get(ConfigName.TYPE_DATABASE).getAsString().toLowerCase();
        
        QueryCore qc = Select.getTablesName_mysql(new ArrayList<>(Arrays.asList(GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString())));
        if(dpType.equals("sqlserver"))
        {
            qc = Select.getTablesName_sqlServer(new ArrayList<>(),GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString());
        }
        else if(dpType.equals("oracle"))
        {
            
        }
        
        return qc;
    }
    
    public static QueryCore getMetadataTable(String nomeTab) throws IOException
    {
        File fileConnection = new File(ConfigHelper.getPercorsoFileDBConnection());
        JsonObject dbConnection = Utility.convertFileToJson(fileConnection.getAbsolutePath());
        String dpType = dbConnection.get(ConfigName.TYPE_DATABASE).getAsString().toLowerCase();
        
        QueryCore qc = Select.getMetadataFromTable_mysql(new ArrayList<>(Arrays.asList(GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString(),nomeTab)));
        if(dpType.equals("sqlserver"))
        {
            qc = Select.getMetadataFromTable_sqlserver(new ArrayList<>(Arrays.asList(nomeTab,nomeTab,nomeTab,GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString())));
        }
        else if(dpType.equals("oracle"))
        {
            
        }
        return qc;
    }
}
