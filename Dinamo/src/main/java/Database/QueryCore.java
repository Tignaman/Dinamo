package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;


public class QueryCore<T,K,V>
{
    /*Stringa utilizzata per settare la query da eseguire*/
    private String query;
    
    /*lista dei parametri che vengono utilizzati per rimpizziare i "?" (Ricordare di passarli in ordine)*/
    private List<T> l;
    
    /*Prepared statement utilizzato per effettuare la query*/
    private PreparedStatement prepStm;
    
    /*ResultSet della query eseguita*/
    private ResultSet rs;
    
    /*Alla fine risulta essere o il JsonElement o la model*/
    private K response;
    
    /*Key rappresenta l'id inserito dopo aver effettuato una query*/
    private int key;
    
    /*C rappresenta la classe con la quale verrà mappato il resultset*/
    private Class c;
    
    /*Costruttore con query e la lista dei parametri da settare in ordine*/
    public QueryCore(String query,List<T> l)
    {
        this.query = query;
        this.l = l;
    }
    
    /*Costruttore solo con query dove non bisogna settare nessun parametro*/
    public QueryCore(String query)
    {
        this.query = query;
    }
    
    /*La funzione di init viene utilizzata per inizializzare il prepared statement data una connessione come parametro*/
    public QueryCore init(Connection c) throws SQLException
    {
        prepStm = c.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        return this;
    }
    
    /*La funzione build query viene utilizzata per rimpiazzare i "?" con i parametri della lista */
    public QueryCore buildQuery() throws SQLException
    {
        if(l != null)
        {
            for(int i=0;i<l.size();i++)
            {
                prepStm.setObject(i+1,l.get(i));
            }
            /*for(T obj : l)
            {
                prepStm.setObject(l.indexOf(obj) + 1, obj);
            }*/
        }
        return this;
    }
    
    public QueryCore to(Class c)
    {
        this.c = c;
        return this;
    }
    
    /*Il metodo execution viene utilizzato per eseguire la query di select*/
    public QueryCore executionQ() throws SQLException
    {
        rs = prepStm.executeQuery();
        return this;
    }
    
    /*Il metodo execution viene utilizzato per eseguire la query di update*/
    public QueryCore executionU() throws SQLException
    {
        prepStm.executeUpdate();
        ResultSet rsu = prepStm.getGeneratedKeys();
        if(rsu.next())
        {
            key = rsu.getInt(1);
        }
        return this;
    }
    
    /*La funzione mapping viene utilizzata per mappare il resultset in JsonElement o model*/
    public QueryCore mapping(Function<T,K> map)
    {
        response =  map.apply((T) rs);
        return this;
    }
    
    /*La funzione mapping viene utilizzata per mappare il resultset in JsonElement o model*/
    public QueryCore mapping(BiFunction<T,V,K> map)
    {
        response =  map.apply((T) rs,(V) c);
        return this;
    }
    
    /*Il metodo destroy viene utilizzato per chiudere il resultset e il prepared statement se ancora non sono stati chiusi*/
    public QueryCore destroy()
    {
        try
        {
            if(rs != null && !rs.isClosed())
            {
                rs.close();
            }
            if(prepStm != null && !prepStm.isClosed())
            {
                prepStm.close();
            }
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return this;
    }
    
    /*GETTER AND SETTER*/
    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public List<T> getL()
    {
        return l;
    }

    public void setL(List<T> l)
    {
        this.l = l;
    }

    public PreparedStatement getPrepStm()
    {
        return prepStm;
    }

    public void setPrepStm(PreparedStatement prepStm)
    {
        this.prepStm = prepStm;
    }

    public ResultSet getRs()
    {
        return rs;
    }

    public void setRs(ResultSet rs)
    {
        this.rs = rs;
    }

    public K getResponse()
    {
        return response;
    }

    public void setResponse(K response)
    {
        this.response = response;
    }

    public int getKey()
    {
        return key;
    }

    public void setKey(int key)
    {
        this.key = key;
    }
    
    
    
    
    
}
