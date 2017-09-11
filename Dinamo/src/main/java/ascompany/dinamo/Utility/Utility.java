package ascompany.dinamo.Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
    /**
     * Funzione che scrive un JsonObject su un file
     * 
     * @param j jsonObject da scrivere su file system
     * @param path percorso dove bisogna scrivere il JsonObject
     * @throws IOException 
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
    
    /**
     * Funzione che viene utilizzata per trasformare un file json in un JsonObject, passando come parametro il percorso
     * 
     * @param filename path del file che bisogna convertire in JsonObject
     * @return 
     */
    public static JsonObject convertFileToJson(String filename)
    {   
        try
        {            
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
    
    /**
     * Funzione che elimina una cartella col suo contenuto
     * 
     * @param file file da dover cancellare
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
    
    /**
     * Funzione che ricava una stringa dopo un carattere specificato
     * 
     * @param s Stringa 
     * @param c Carattere
     * @return stringa restante dopo l'ultimo carattere
     */
    public static String getStringAfterLastChar(String s, String c)
    {
         return s.substring(s.lastIndexOf(c) + 1);
    }
    
    /**
     * Funzione che Capitalizza la prima lettera di una parola
     * 
     * @param input stringa di cui bisgona capitalizzare la prima lettera
     * @return stringa capitalizzata
     */
    public static String capitalizeFirstLetter(String input)
    {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    
    
    
    /**
     * Funzione utilizzata per controllare se in un JsonArray è presente una strigna
     * 
     * @param ja JsonArray nella quale si cerca la stringa
     * @param p proprietà ricercata
     * @param check stringa da dover chechare
     * @return se la stringa è stata trovata o meno
     */
    public static boolean isStringPresent(JsonArray ja,String p, String check)
    {
        for(JsonElement je : ja)
        {
            if(je.getAsJsonObject().get(p).getAsString().equals(check))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param className nome della classe
     * @return se la classe è stata trovata o meno
     */
    public static boolean isClassPresent(String className) 
    {
        try  
        {
            Class.forName(className);
            return true;
        }  
        catch (ClassNotFoundException e) 
        {
            return false;
        }
    }
    
    /**
     * Funzione utilizzata per creare il file .java
     * 
     * @param nomeClasse nome del file da dover creare
     * @param source stringa contenente il codice sorgente
     * @param path percorso di creazioen
     * @throws Exception 
     */
    public static void creaFileJava(String nomeClasse, String source, String path) throws Exception
    {
        File root = new File(path);
        File sourceFile = new File(root, "/"+nomeClasse+".java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
    }
}
