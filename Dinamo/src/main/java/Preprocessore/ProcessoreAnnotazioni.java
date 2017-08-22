package Preprocessore;

import Annotazioni.DinamoBootstrap;
import Utility.ConfigHelper;
import static ascompany.dinamo.GestoreDB.establishingConnection;
import static ascompany.dinamo.GestoreModel.GeneraModel;
import java.sql.Connection;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 *
 * @author marcocastano
 */
@SupportedAnnotationTypes("Annotazioni.DinamoBootstrap")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ProcessoreAnnotazioni extends AbstractProcessor
{
    public ProcessoreAnnotazioni() 
    {
        super();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        try
        {
            /*Controlliamo se l'annotazione è presente soltanto una volta nel codice*/
            if(roundEnv.getElementsAnnotatedWith(DinamoBootstrap.class).size() <= 1)
            {
                /*Per ogni annotazione trovata prendiamo il percorso dove verrà creato il package DINAMO*/
                for (Element elem : roundEnv.getElementsAnnotatedWith(DinamoBootstrap.class)) 
                {
                    //Ricaviamo l'elemento
                    DinamoBootstrap dinamoBootstrap = elem.getAnnotation(DinamoBootstrap.class);
                    
                    /*Settiamo il base Path*/
                    ConfigHelper.basePath = dinamoBootstrap.path();
                }
                
                /*Esegue la connessione col database*/
                Connection connection = establishingConnection();
                if(connection != null)
                {
                    GeneraModel(connection);
                }
                else
                {
                    throw new Exception("Error establishing the connection with the database");
                }
                
                
            }
            /*ALtrimento lanciamo l'eccezione*/
            else
            {
                throw new Exception("Multiple DinamoBoostrap annotation found.");
            }
            
            
            
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        return true;
        
    }
    
}
