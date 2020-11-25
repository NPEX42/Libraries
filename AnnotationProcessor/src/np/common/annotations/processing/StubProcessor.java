package np.common.annotations.processing;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import com.google.auto.service.AutoService;

import np.common.annotations.Stub;
@AutoService(StubProcessor.class)
public class StubProcessor extends AbstractProcessor {

	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;
	
	@Override
	public synchronized void init(ProcessingEnvironment env){ 
		super.init(processingEnv);
	    typeUtils = processingEnv.getTypeUtils();
	    elementUtils = processingEnv.getElementUtils();
	    filer = processingEnv.getFiler();
	    messager = processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env) {
		// Itearate over all @Factory annotated elements
	    for (Element annotatedElement : env.getElementsAnnotatedWith(Stub.class)) {
	    	error(annotatedElement, "Stub Methods Are not implemented Yet!");
	    }
	    return true;
	}
	
	private void error(Element e, String msg, Object... args) {
	    messager.printMessage(
	    	Kind.ERROR, String.format(msg, args), e);
	  }

	@Override
	public Set<String> getSupportedAnnotationTypes() { return Set.of("np.common.annotations.Stub"); }

	@Override
	public SourceVersion getSupportedSourceVersion() { return SourceVersion.latestSupported(); }

}
