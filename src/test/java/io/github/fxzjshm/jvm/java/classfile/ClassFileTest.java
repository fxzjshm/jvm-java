package io.github.fxzjshm.jvm.java.classfile;

import java.io.*;
import java.nio.file.Files;
import org.junit.Test;

/**
 * Test parsing class file.
 *
 * @author fxzjshm
 */
public class ClassFileTest {

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
    
    File dir = new File("src/test/resources");

    @Test
    public void parseClass() throws IOException {
        File[] srcs = dir.listFiles(new SuffixFilter("java"));
        for (File src : srcs) {
            System.out.println(src.getName());
            String content = new String(Files.readAllBytes(src.toPath()));
        }
    }

}
