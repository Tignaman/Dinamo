package Annotazioni;

/**
 *
 * @author n.dipietro
 */
public @interface ForeignKey
{
    /*
    * Tabella referenziata dalla foreign key
    */
    String table();
    
    /*
    * Colonna referenziata dalla foregin key
    */
    String column();
}
