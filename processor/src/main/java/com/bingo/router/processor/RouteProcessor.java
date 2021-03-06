package com.bingo.router.processor;


import com.bingo.router.annotations.PathClass;
import com.bingo.router.annotations.Route;
import com.bingo.router.annotations.model.Const;
import com.bingo.router.annotations.model.Loader;
import com.bingo.router.annotations.model.RouteInfo;
import com.bingo.router.annotations.model.Utils;
import com.bingo.router.processor.base.BaseProcessor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.bingo.router.annotations.Route"})
@SupportedOptions({"moduleName"})
public class RouteProcessor extends BaseProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> routes = roundEnvironment.getElementsAnnotatedWith(Route.class);

        MethodSpec.Builder builder = MethodSpec.methodBuilder(Const.METHOD_LODE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, RouteInfo.class), "routers");
        int pos = 0;

        for (Element element : routes) {
            if (element instanceof TypeElement && element.getKind() == ElementKind.CLASS) {
                TypeElement e = (TypeElement) element;
                mMessager.printMessage(Diagnostic.Kind.WARNING, e.getQualifiedName());
                int type = 0;
                if (isActivity(e)) {
                    type = RouteInfo.TYPE_ACTIVITY;
                } else if (isFragment(e)) {
                    type = RouteInfo.TYPE_FRAGMENT;
                }
                String className = e.getQualifiedName().toString();
                Route route = e.getAnnotation(Route.class);
                String path = route.value();
                if (path.isEmpty()) {
                    try {
                        //查看相关内容
                        //https://www.cnblogs.com/fuckingaway/p/6703021.html
                        //https://area-51.blog/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
                        route.pathClass();
                    } catch (MirroredTypeException mte) {
                        TypeMirror value = mte.getTypeMirror();
                        String valueClass = value.toString();
                        TypeElement typeElement = mElementUtils.getTypeElement(valueClass);
                        path = Utils.pathByPathClass(typeElement.getAnnotation(PathClass.class));
                    }
                }
                builder.addStatement("$T route$L =new $T(" + type + ",$S,$T.class)", TypeName.get(RouteInfo.class), pos, TypeName.get(RouteInfo.class), path, ClassName.get(e));

                if (route.interceptors().length != 0) {
                    StringBuilder interceptorKeys = new StringBuilder("new String[]{");
                    for (int i = 0; i < route.interceptors().length; i++) {
                        interceptorKeys.append("\"" + route.interceptors()[i] + "\"");
                        if (i != route.interceptors().length - 1) {
                            interceptorKeys.append(",");
                        }
                    }
                    interceptorKeys.append("}");

                    builder.addStatement("route$L.setInterceptorKeys($L)", pos, interceptorKeys.toString());
                }

                builder.addStatement("routers.put($S,route$L)", path, pos);
                pos++;

            }

        }

        TypeSpec typeSpec = TypeSpec.classBuilder(Const.ROUTER_LOADER_CLASS_NAME)
                .addSuperinterface(ParameterizedTypeName.get(Loader.class, RouteInfo.class))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(Const.LOADER_PKG + Const.DOT + getModuleName(), typeSpec).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
