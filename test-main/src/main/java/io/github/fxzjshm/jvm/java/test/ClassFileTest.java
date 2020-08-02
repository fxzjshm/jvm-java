package io.github.fxzjshm.jvm.java.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.github.fxzjshm.jvm.java.ResourceClassReader;
import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;

import static io.github.fxzjshm.jvm.java.ResourceClassReader.baseDir;

/**
 * Test parsing class file.
 *
 * @author fxzjshm
 */
public class ClassFileTest {

    public static Map<String, byte[]> classBytesMap = new HashMap<>();
    public static Map<String, ClassFile> classMap = new HashMap<>();

    // @Test
    public void parseClass() throws IOException {
        if (Gdx.app.getType().equals(Application.ApplicationType.WebGL)) {
            baseDir = "./";
        }
        classBytesMap = ResourceClassReader.INSTANCE.readClass();
        for (Map.Entry<String, byte[]> entry : classBytesMap.entrySet()) {
            Gdx.app.debug("ClassFileTest", "Test parsing " + entry.getKey());
            ClassFile cf = new ClassFile(new ByteArrayReader(entry.getValue()));
            TestHelper.assertEqual(0, cf.reader.available());
            classMap.put(cf.name, cf);
        }
    }

}
