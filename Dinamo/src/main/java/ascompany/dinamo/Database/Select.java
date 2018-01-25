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
     * Funzione che restituisce la query per ricavare la lista delle tabelle per mysql
     * 
     * @param <T> 
     * @param l 
     */
    public static <T> QueryCore getTablesName_mysql(List<T> l)
    {
        String query = "SELECT table_name from information_schema.tables where table_Schema = ?";
        return new QueryCore(query,l);
    }
    
    /**
     * Funzione che restituisce la query per icavare la lista delle tabelle per sqlserver
     *
     * @param <T>
     * @param l
     * @param database
     */
    public static <T> QueryCore getTablesName_sqlServer(List<T> l, String database)
    {
        String query = "SELECT table_name FROM "+database+".INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
        return new QueryCore(query,l);
    }
    
    /**
     * Funzione che restituisce la query per ricavare i metadati di una tabella
     * 
     * @param <T> Tipo degli oggetti della lista dei prepared statement
     * @param l lista dei prepared statement
     * @return QueryCore
     */
    public static <T> QueryCore getMetadataFromTable_mysql(List<T> l)
    {
        String query = 
                "select COLUMNS.COLUMN_KEY,COLUMNS.COLUMN_NAME,COLUMNS.IS_NULLABLE,COLUMNS.DATA_TYPE,COLUMNS.COLUMN_TYPE,COLUMNS.EXTRA,KEY_COLUMN_USAGE.REFERENCED_TABLE_NAME, KEY_COLUMN_USAGE.REFERENCED_COLUMN_NAME "
                + "from INFORMATION_SCHEMA.COLUMNS LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
                + "ON (COLUMNS.COLUMN_NAME = KEY_COLUMN_USAGE.COLUMN_NAME AND COLUMNS.TABLE_NAME = KEY_COLUMN_USAGE.TABLE_NAME) "
                + "where COLUMNS.TABLE_SCHEMA = ? and COLUMNS.TABLE_NAME = ? "
                + "order by KEY_COLUMN_USAGE.REFERENCED_TABLE_NAME DESC";
        return new QueryCore(query,l);
    }
    
    public static <T> QueryCore getMetadataFromTable_sqlserver(List<T> l)
    {
        String query =
        "select extra = '', column_name = col.column_name, data_type = col.data_type, is_nullable = col.IS_NULLABLE, referenced_table_name = fk.ReferenceTableName, referenced_column_name = fk.ReferenceColumnName, column_key = k.CONSTRAINT_TYPE " +
        " FROM information_schema.tables tbl " +
        " INNER JOIN information_schema.columns col " +
        " ON col.table_name = tbl.table_name " +
        " AND col.table_schema = tbl.table_schema " +
        " LEFT JOIN sys.extended_properties prop  " +
        " ON prop.major_id = object_id(tbl.table_schema + '.' + tbl.table_name) " +
        " AND prop.minor_id = 0 " +
        " AND prop.name = 'MS_Description' " +
                
        " LEFT JOIN "+ 
        "( " +
        "   SELECT f.name AS ForeignKey, OBJECT_NAME(f.parent_object_id) AS TableName, COL_NAME(fc.parent_object_id, fc.parent_column_id) AS ColumnName, OBJECT_NAME (f.referenced_object_id) AS ReferenceTableName, COL_NAME(fc.referenced_object_id, fc.referenced_column_id) AS ReferenceColumnName " +
        "   FROM sys.foreign_keys AS f " +
        "   INNER JOIN sys.foreign_key_columns AS fc " +
        "   ON f.OBJECT_ID = fc.constraint_object_id " +
        "   WHERE OBJECT_NAME(f.parent_object_id) = ?" +
        ") as fk ON (fk.ColumnName = col.column_name) " +
                
        "LEFT JOIN " +
        "( " +
        "  select k.type as CONSTRAINT_TYPE, c.name as COLUMN_NAME " +
        "  from sys.key_constraints as k " +
        "  join sys.tables as t on t.object_id = k.parent_object_id " +
        "  join sys.schemas as s on s.schema_id = t.schema_id\n" +
        "  join sys.index_columns as ic on ic.object_id = t.object_id and ic.index_id = k.unique_index_id " +
        "  join sys.columns as c on c.object_id = t.object_id and c.column_id = ic.column_id " +
        "  where t.name = ? " +
        ") as k ON k.COLUMN_NAME = col.column_name " +
                
        "WHERE tbl.table_type = 'base table' and tbl.table_name = ? and tbl.TABLE_CATALOG = ? ";
        
        return new QueryCore(query,l);
    }
    
    
    

}
