package io.github.fxzjshm.jvm.java.test;

import org.eclipse.jdt.core.compiler.batch.BatchCompiler;
import org.eclipse.jdt.internal.compiler.impl.StringConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;

/**
 * Test parsing class file.
 *
 * @author fxzjshm
 */
public class ClassFileTest {

    public static File dir = new File("core/src/test/resources");
    public static Map<String, ClassFile> classMap = new Hashtable<>();

    public static Set<File> searchFile(FilenameFilter filter, File dir) {
        Set<File> set = new HashSet<>();
        File[] files = dir.listFiles(filter);
        if (files != null) {
            Collections.addAll(set, files);
        }
        File[] directories = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        if (directories != null) {
            for (File dir0 : directories) {
                set.addAll(searchFile(filter, dir0));
            }
        }
        return set;
    }

    @Before
    public void compileClass() throws IOException {
        Set<File> classes = searchFile(new SuffixFilter("class"), dir);
        Map<String, File> name2file = new Hashtable<>(classes.size());
        for (File classFile : classes) {
            name2file.put(classFile.getAbsolutePath(), classFile);
        }
        Set<File> srcs = searchFile(new SuffixFilter("java"), dir);
        for (File src : srcs) {
            String classLocation = src.getAbsolutePath().substring(0, src.getAbsolutePath().length() - "java".length()) + "class";
            if (name2file.containsKey(classLocation)) {
                if (name2file.get(classLocation).lastModified() > src.lastModified()){
                    System.out.println("Cached: " + src.getName());
                    continue;
                }
            }
            System.out.println("Compile: " + src.getName());
            BatchCompiler.compile("-classpath rt.jar -noExit -1.8 " + src.getAbsolutePath() + " -d " + dir.getAbsolutePath(),
                    new PrintWriter(System.out),
                    new PrintWriter(System.err), null);
        }

    }

    @Test
    public void parseClass() throws IOException {
        Set<File> classes = searchFile(new SuffixFilter("class"), dir);
        if (classes != null) {
            for (File classFile : classes) {
                ClassFile cf = new ClassFile(new ByteArrayReader(Files.readAllBytes(classFile.toPath())));
                Assert.assertEquals(0, cf.reader.available());
                classMap.put(cf.name, cf);
            }
        }
    }

    public static class SuffixFilter implements FilenameFilter {

        public String suffix;

        public SuffixFilter(String s) {
            suffix = s;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.substring(name.lastIndexOf('.') + 1).equalsIgnoreCase(suffix);
        }
    }
}
