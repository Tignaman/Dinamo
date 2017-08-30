package ascompany.dinamo.Annotazioni;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author marcocastano
 */
    
@Retention(RetentionPolicy.SOURCE)
public @interface DinamoBootstrap
{
    /**
     * 
     * @return percorso dove verranno creati i file di configurazione
     */
    String path();
}
