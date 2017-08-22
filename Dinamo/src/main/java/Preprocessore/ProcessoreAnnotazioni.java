package Preprocessore;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
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
        String path = System.getProperty("user.dir");
        System.out.println("path:" + path);
        return true;
    }
    
}
