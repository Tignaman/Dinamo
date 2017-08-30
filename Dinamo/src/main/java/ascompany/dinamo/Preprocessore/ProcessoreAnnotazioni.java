package ascompany.dinamo.Preprocessore;

import ascompany.dinamo.Annotazioni.DinamoBootstrap;
import ascompany.dinamo.Configurazione.ConfigHelper;
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
 * @author m.castano
 */
@SupportedAnnotationTypes("ascompany.dinamo.Annotazioni.DinamoBootstrap")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ProcessoreAnnotazioni extends AbstractProcessor
{
    /**
     * Costruttore
     */
    public ProcessoreAnnotazioni() 
    {
        super();
    }
    
    /**
     * 
     * @param annotations
     * @param roundEnv
     * @return 
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        try
        {
            if(roundEnv.getElementsAnnotatedWith(DinamoBootstrap.class).size() <= 1)
            {
                for (Element elem : roundEnv.getElementsAnnotatedWith(DinamoBootstrap.class)) 
                {
                    DinamoBootstrap dinamoBootstrap = elem.getAnnotation(DinamoBootstrap.class);
                    ConfigHelper.basePath = dinamoBootstrap.path();
                }
                Connection connection = establishingConnection();
                GeneraModel(connection);
            }
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
