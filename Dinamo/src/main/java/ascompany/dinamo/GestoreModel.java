package ascompany.dinamo;

import Configurazione.ConfigName;
import Configurazione.ConfigWriter;
import Database.DatabaseUtility;
import Database.QueryCore;
import Database.Select;
import Utility.ConfigHelper;
import Utility.Utility;
import com.google.gson.JsonArray;
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

    public static void GeneraModel(Connection connection) throws Exception
    {

        /*Ricavato il percorso del file di configurazione per la connessione al database*/
        File file = new File(ConfigHelper.getPercorsoFileConfigModel());

        if (file.exists() && !file.isDirectory())
        {
            if (GestoreDB.DBConfigFile != null)
            {
                QueryCore qc = Select.getTablesName(new ArrayList<>(Arrays.asList(GestoreDB.DBConfigFile.get(ConfigName.DATABASE).getAsString())));
                JsonArray listaTabelle = (JsonArray) qc.init(connection)
                        .buildQuery()
                        .executionQ()
                        .mapping(new DatabaseUtility().rsToJson)
                        .destroy()
                        .getResponse();

                System.out.println(listaTabelle);
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
            Utility.writeJsonToFs(ConfigWriter.ModelConfigWriter(),ConfigHelper.getPercorsoFileConfigModel());
        }

    }
}
