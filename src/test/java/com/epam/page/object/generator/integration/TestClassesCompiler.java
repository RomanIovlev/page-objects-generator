package com.epam.page.object.generator.integration;

import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.integration.data.CompilationResult;
import java.io.File;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

class TestClassesCompiler {
    private PageObjectsGenerator pog;

    TestClassesCompiler(PageObjectsGenerator pog) {
        this.pog = pog;
    }

    CompilationResult compileClasses(String expectedPath,
                                     String manualPath,
                                     String expectedFullName,
                                     String manualFullName) throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = compiler
            .getStandardFileManager(null, null, null);
        String[] options = new String[]{"-d", "target/test-classes"};
        File[] files = new File[]{
            new File(expectedPath),
            new File(manualPath)
        };
        pog.generatePageObjects();

        JavaCompiler.CompilationTask compilationTask = compiler.getTask(
            null,
            null,
            null,
            Arrays.asList(options),
            null,
            standardJavaFileManager.getJavaFileObjects(files));
        boolean compileSuccess = compilationTask.call();

        CustomClassLoader classLoader = new CustomClassLoader();
        Class testClass = classLoader.loadClass(expectedFullName);
        Class manualClass = classLoader.loadClass(manualFullName);

        return new CompilationResult(manualClass, testClass, compileSuccess);
    }
}
