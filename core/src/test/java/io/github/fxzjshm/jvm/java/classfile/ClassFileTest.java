package io.github.fxzjshm.jvm.java.classfile;

import org.eclipse.jdt.core.compiler.batch.BatchCompiler;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Test parsing class file.
 *
 * @author fxzjshm
 */
public class ClassFileTest {

    static File dir = new File("src/test/resources");

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
        Set<File> srcs = searchFile(new SuffixFilter("java"), dir);
        for (File src : srcs) {
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
                assert cf.reader.available() == 0;
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
