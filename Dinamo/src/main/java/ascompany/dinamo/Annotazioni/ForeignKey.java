package ascompany.dinamo.Annotazioni;

/**
 * Intefaccia che indica se un campo è un foreign key
 * 
 * @author n.dipietro
 */
public @interface ForeignKey
{
    /**
     * 
     * @return tabella referenziata dalla foreign key
     */
    String table();
    
    /**
     * 
     * @return colonna referenziata dalla foreign key
     */
    String column();
}
