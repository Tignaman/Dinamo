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
    /*
    *
    *    Modificatore public
    *
    */
    public static final String PUBLIC = "public";
    
    /*
    *
    *    Modificatore protected
    *
    */
    public static final String PROTECTED = "protected";
    
    /*
    *
    *    Modificatore private
    *
    */
    public static final String PRIVATE = "private";
    
    /*
    *
    *    Parola chiave class
    *
    */
    public static final String CLASS = "class";
    
    /*
    *
    *    Parola chiave final
    *
    */
    public static final String FINAL = "final";
    
    /*
    *
    *    Parola chiave static
    *
    */
    public static final String STATIC = "static";
    
    /*
    *
    *    Parola chiave void
    *
    */
    public static final String VOID = "void";
    
    /*
    *
    *    Parola chiave get
    *
    */
    public static final String GET = "get";
    
    /*
    *
    *    Parola chiave set
    *
    */
    public static final String SET = "set";
    
    /*
    *
    *    Parola chiave return
    *
    */
    public static final String RETURN = "return";
    
    /*
    *
    *    Parola chiave this
    *
    */
    public static final String THIS = "this";
    
    /*
    *
    *    Parola chiave import
    *
    */
    public static final String IMPORT = "import";
    
    
    /*
    *
    *    Hashmap utilizzato per mappare i tipi
    *
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
