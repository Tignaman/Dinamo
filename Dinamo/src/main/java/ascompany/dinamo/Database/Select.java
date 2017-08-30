package ascompany.dinamo.Database;

import ascompany.sinfonia.Core.QueryCore;
import java.util.List;

/**
 *
 * @author n.dipietro
 */
public class Select
{
    /**
     * Funzione che restituisce la query per ricavare la lista delle tabelle
     * 
     * @param <T> 
     * @param l 
     */
    public static <T> QueryCore getTablesName(List<T> l)
    {
        String query = "SELECT table_name from information_schema.tables where table_Schema = ?";
        return new QueryCore(query,l);
    }
    
    /**
     * Funzione che restituisce la query per ricavare i metadati di una tabella
     * 
     * @param <T> Tipo degli oggetti della lista dei prepared statement
     * @param l lista dei prepared statement
     * @return QueryCore
     */
    public static <T> QueryCore getMetadataFromTable(List<T> l)
    {
        String query = 
                "select COLUMNS.COLUMN_KEY,COLUMNS.COLUMN_NAME,COLUMNS.IS_NULLABLE,COLUMNS.DATA_TYPE,COLUMNS.COLUMN_TYPE,COLUMNS.EXTRA,KEY_COLUMN_USAGE.REFERENCED_TABLE_NAME, KEY_COLUMN_USAGE.REFERENCED_COLUMN_NAME "
                + "from INFORMATION_SCHEMA.COLUMNS LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
                + "ON (COLUMNS.COLUMN_NAME = KEY_COLUMN_USAGE.COLUMN_NAME AND COLUMNS.TABLE_NAME = KEY_COLUMN_USAGE.TABLE_NAME) "
                + "where COLUMNS.TABLE_SCHEMA = ? and COLUMNS.TABLE_NAME = ? "
                + "order by KEY_COLUMN_USAGE.REFERENCED_TABLE_NAME DESC";
        return new QueryCore(query,l);
    }
    
    
    

}
