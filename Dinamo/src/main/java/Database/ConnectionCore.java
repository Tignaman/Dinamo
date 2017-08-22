package Database;

import java.sql.Connection;
import java.sql.SQLException;

/*Classe che viene utilizzata esclusivamente per gestire la connessione*/
public class ConnectionCore
{
    /*Oggetto di tipo connection*/
    private Connection c = null;

    /*Ritorna la connessione*/
    public Connection getConnection()
    {
        return c;
    }
    
    /*Apre la connessione chiamando la funzione fetchConnection ed impostando l'autocommit a false*/
    public void openConnection() throws SQLException
    {
        c = fetchConnection();
        c.setAutoCommit(false);
    }
    
    //Funzioni per ricavare una connessione
    private Connection fetchConnection() throws SQLException
    {
        return getConnection();
    }
    
    /*Funzione che chiude la connessione*/
    public void closeConnection()
    {
        try
        {
            if(c != null && !c.isClosed())
            {
                c.close();
            }
            
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /*Funzione per eseguire il commit*/
    public void doCommit() throws SQLException
    {
        if(c != null && !c.isClosed())
        {
            c.commit();
        }
        
    }
    
    /*Funzione per eseguire il rollback*/
    public void doRollback() 
    {
        try
        {
            if(c != null && !c.isClosed())
            {
                c.rollback();
            }
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

}
