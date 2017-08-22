/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.List;

/**
 *
 * @author n.dipietro
 */
public class Select
{
    public static <T> QueryCore getTablesName(List<T> l)
    {
        String query = "SELECT table_name from information_schema.tables where table_Schema = ?";
        return new QueryCore(query,l);
    }
}
