package com.epam.page.object.generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.nio.file.Paths;

public class JavaFileWriter {
	private String outputDir;

	public JavaFileWriter(String outputDir) {
		this.outputDir = outputDir;
	}

	public void write(String packageName, TypeSpec siteClass) throws IOException {
		JavaFile javaFile = JavaFile.builder(packageName + ".site", siteClass)
				.build();
		javaFile.writeTo(Paths.get(outputDir));
	}
}
