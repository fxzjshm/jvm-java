package io.github.fxzjshm.jvm.java.test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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

    public static File dir = new File("../core/src/test/resources");
    public static Map<String, ClassFile> classMap = new Hashtable<>();

    public static Set<File> searchFile(FilenameFilter filter, File dir) {
        System.out.println("Searching " + ((SuffixFilter) filter).suffix + " file in: " + dir.getAbsolutePath());
        Set<File> set = new HashSet<>();
        File[] files = dir.listFiles(filter);
        if (files != null) {
            Collections.addAll(set, files);
        }
        File[] directories = dir.listFiles((current, name) -> new File(current, name).isDirectory());
        if (directories != null) {
            for (File dir0 : directories) {
                set.addAll(searchFile(filter, dir0));
            }
        }
        return set;
    }

    // @Test
    public void parseClass() throws IOException {
        Set<File> classes = searchFile(new SuffixFilter("class"), dir);
        Set<File> clazzes = searchFile(new SuffixFilter("bytecode"), dir);
        classes.addAll(clazzes);
        if (classes != null) {
            for (File classFile : classes) {
                System.out.println("Test parsing " + classFile.getName());
                ClassFile cf = new ClassFile(new ByteArrayReader(Files.readAllBytes(classFile.toPath())));
                TestHelper.assertEqual(0, cf.reader.available());
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
