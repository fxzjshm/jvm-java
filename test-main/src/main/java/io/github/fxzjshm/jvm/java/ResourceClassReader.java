package io.github.fxzjshm.jvm.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourceClassReader {

    public static ResourceClassReader INSTANCE = new ResourceClassReader();

    public static String baseDir = "core/src/test/resources";

    public static Set<FileHandle> searchFile(FilenameFilter filter, FileHandle dir) {
        Gdx.app.log("ResourceClassReader", "Searching " + ((SuffixFilter) filter).suffix + " file in: " + dir.path());
        Set<FileHandle> set = new HashSet<>();
        FileHandle[] files = dir.list(filter);
        if (files != null) {
            Collections.addAll(set, files);
        }
        FileHandle[] directories = dir.list((current, name) -> Gdx.files.internal(current + "/" + name).isDirectory());
        if (directories != null) {
            for (FileHandle dir0 : directories) {
                set.addAll(searchFile(filter, dir0));
            }
        }
        return set;
    }

    public Map<String, byte[]> readClass() {
        /*
        if (Gdx.app.getType().equals(Application.ApplicationType.WebGL)) {
            try {
                Gdx.app.debug("","");
                Class cLoader = ClassReflection.forName("io.github.fxzjshm.jvm.java.test.gwt.client.GwtResourceClassReader");
                Gdx.app.debug("","");
                Method mReadClass = ClassReflection.getDeclaredMethod(cLoader, "readClass");
                return (Map<String, byte[]>) mReadClass.invoke(ClassReflection.getDeclaredField(cLoader, "INSTANCE").get(null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            */
        Set<FileHandle> classes = searchFile(new SuffixFilter("class"), Gdx.files.internal(baseDir));
        Set<FileHandle> clazzes = searchFile(new SuffixFilter("bytecode"), Gdx.files.internal(baseDir));
        classes.addAll(clazzes);
        Map<String, byte[]> classBytesMap = new HashMap<>();
        if (classes != null) {
            for (FileHandle classFile : classes) {
                classBytesMap.put(classFile.name(), classFile.readBytes());
            }
        }
        return classBytesMap;
        // }
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
