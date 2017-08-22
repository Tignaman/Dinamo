package Database;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DatabaseUtility<T,K,V>
{
    /*Funzione di mmapping che viene utilizzata per convertire il resultset direttamente in json*/
    public Function<T,K> rsToJson = (x)->
    {
        JsonArray jsonArray = new JsonArray();
        try
        {
            ResultSet rs = (ResultSet) x;
            int total_rows = rs.getMetaData().getColumnCount(); 
            while (rs.next())
            {
                JsonObject obj = new JsonObject();
                for (int i = 0; i < total_rows; i++)
                {
                    obj.addProperty(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), ""+rs.getObject(i + 1));
                }
                jsonArray.add(obj);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return (K) jsonArray;
        
    };
    
    
    public BiFunction<T,V,K> rsToModel = (x,y)->
    {
        ResultSet rs = (ResultSet) x;
        Class c = (Class) y;
        
        List<T> outputList = null;
        try
        {
            // make sure resultset is not null
            if (rs != null)
            {
                // get the resultset metadata
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next())
                {
                    T instance = (T) c.newInstance();
                    for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++)
                    {
                        // getting the SQL column name
                        String columnName = rsmd.getColumnLabel(_iterator + 1);
                        // reading the value of the SQL column
                        Object columnValue = rs.getObject(_iterator + 1);

                        Field field = c.getDeclaredField(columnName);
                        field.setAccessible(true);
                        if(columnValue != null)
                        {
                            field.set(instance, columnValue);
                        }
                    }
                    if (outputList == null)
                    {
                        outputList = new ArrayList<T>();
                    }
                    outputList.add(instance);
                }
            } 
            else
            {
                return null;
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        } 
        return (K) outputList;
    };
    
}
