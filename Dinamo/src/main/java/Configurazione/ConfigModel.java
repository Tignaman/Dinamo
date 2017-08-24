package Configurazione;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author n.dipietro
 */
public class ConfigModel
{
    /**
     * Modificato public
     */
    public static final String PUBLIC = "public";
    
    /**
     * Modifica protected
     */
    public static final String PROTECTED = "protected";
    
    /**
     * Modificatore private
     */
    public static final String PRIVATE = "private";
    
    /**
     * Parola chiave CLASS
     */
    public static final String CLASS = "class";
    
    /**
     * Parola chiave FINAL
     */
    public static final String FINAL = "final";
    
    /**
     * Parola chiave STATIC
     */
    public static final String STATIC = "static";
    
    /**
     * Parola chiave VOID
     */
    public static final String VOID = "void";
    
    /**
     * Parola chiave GET
     */
    public static final String GET = "get";
    
    /**
     * Parola chiave SET
     */
    public static final String SET = "set";
    
    /**
     * Parola chiave RETURN
     */
    public static final String RETURN = "return";
    
    /**
     * Parola chiave THIS
     */
    public static final String THIS = "this";
    
    /**
     * Parola chiave IMPORT
     */
    public static final String IMPORT = "import";
    
    /**
     * Hashmap utilizzato per mappare i tipi
     */
    public static final HashMap<String,String> TYPE_MAPPING = new HashMap<String,String>()
    {{
        put("int", "int");
        put("varchar", "String");
        put("tinyint", "boolean");
        put("text" , "String");
        put("char","char");
        put("float", "float");
        put("double" , "double");
        put("date" , "Date");
        put("datetime" , "Date");
        put("timestamp", "Date");
    }};
    
    /**
     * ArrayList utilizzato per capire quali sono i tipi di base che non richiedono un import
     */
    public static final ArrayList<String> BASE_TYPE = new ArrayList<>(Arrays.asList
    (
        "int",
        "float",
        "double",
        "String",
        "char",
        "boolean"
    ));
    
}
