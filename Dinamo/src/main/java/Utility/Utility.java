package Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author n.dipietro
 */
public class Utility
{
    public static void writeJsonToFs(JsonObject j, String path) throws IOException
    {
        
        FileWriter fileWriter = null;
        try
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
            String jsonString = gson.toJson(j);

            fileWriter = new FileWriter(path);

            fileWriter.write(jsonString);
        }
        finally
        {
            if(fileWriter != null)
            {
                fileWriter.close();
            }
            
        }
        
    }
    
    /*Funzione che viene utilizzata per trasformare un file json in un JsonObject, passando come parametro il percorso*/
    public static JsonObject convertFileToJson(String filename)
    {   
        try
        {            
            /*JsonParser che utilizza un file reader per leggere dal file*/
            BufferedReader br = new BufferedReader(new FileReader(filename));     
            if (br.readLine() == null) 
            {
                return null;
            }

            return new JsonParser().parse(new FileReader(filename)).getAsJsonObject();
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        
        
    }
}
