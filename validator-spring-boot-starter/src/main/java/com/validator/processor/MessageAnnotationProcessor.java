package com.validator.processor;

import com.google.auto.service.AutoService;
import com.validator.annotation.FieldNameRoot;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

@SupportedAnnotationTypes("com.validator.annotation.FieldNameRoot")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class MessageAnnotationProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "init MessageAnnotationProcessor end");
    }

    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "process MessageAnnotationProcessor start");

        for (Element element : roundEnv.getElementsAnnotatedWith(FieldNameRoot.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                error(element, "Only classes can be annotated with @%s",
                        FieldNameRoot.class.getSimpleName());
            }
        }

        //FileObject source;
        Path path;
        try {
            //source = getSource();
            path = createIdentifyResource("test.json");
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Fail to create resource: test.json.");
            return false;
        }

        try (PrintWriter writer = new PrintWriter(path.toFile(), StandardCharsets.UTF_8)) {
            writer.println("{");
            writer.println("}");
        } catch (IOException | IllegalStateException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Fail to write to resource: test.json.");
            return true;
        }

        return true;
    }

    private void error(Element element, String message, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(message, args),
                element);
    }

    private synchronized FileObject getSource() throws IOException {
        try {
            FileObject fileObject = filer.getResource(StandardLocation.CLASS_OUTPUT, "", "test.json");
            return fileObject;
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            return filer.createResource(StandardLocation.CLASS_OUTPUT, "", "test.json");
        }
    }

    public Path createIdentifyResource(String file) throws IOException {
        try {
            FileObject fileObject = processingEnv.getFiler().getResource(StandardLocation.SOURCE_OUTPUT,
                    "", file);
            return new File(fileObject.toUri()).toPath();
        } catch (IOException e) {
            FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT,
                    "", file);
            return new File(fileObject.toUri()).toPath();
        }
    }
}
