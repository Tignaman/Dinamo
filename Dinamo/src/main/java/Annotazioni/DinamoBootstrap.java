package Annotazioni;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author marcocastano
 */
    
@Retention(RetentionPolicy.SOURCE)
public @interface DinamoBootstrap
{
    /*
    * Percorso alla quale verranno creati i file di configurazione
    */
    String path();
}
