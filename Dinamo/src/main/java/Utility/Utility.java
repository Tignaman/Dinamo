package Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 *
 * @author n.dipietro
 */
public class Utility
{
    /*
    * Funzione che scrive un JsonObject su un file
    */
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
    
    /*
    * Funzione che elimina una cartella col suo contenuto
    */
    public static void deleteDir(File file) 
    {
        File[] contents = file.listFiles();
        if (contents != null) 
        {
            for (File f : contents) 
            {
                deleteDir(f);
            }
        }
        file.delete();
    }
    
    /*
    * Funzione che ricava una stringa dopo un carattere specificato
    */
    public static String getStringAfterLastChar(String s, String c)
    {
         return s.substring(s.lastIndexOf(c) + 1);
    }
    
    /*
    * Funzione che Capitalizza la prima lettera di una parola
    */
    public static String capitalizeFirstLetter(String input)
    {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    
    /*
    * Funzione utilizzata per creare il file .java
    */
    public static void creaFileJava(String nomeClasse, String source, String path) throws Exception
    {
        File root = new File(path);
        File sourceFile = new File(root, "/"+nomeClasse+".java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
    }
}
