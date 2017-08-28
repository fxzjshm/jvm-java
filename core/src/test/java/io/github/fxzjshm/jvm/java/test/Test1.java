package io.github.fxzjshm.jvm.java.test;

import org.junit.*;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.Main;
import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

public class Test1 {

    @Ignore
    @Test
    public void genBytecodeTemplate() {
        for (int i = 0; i <= 255; i++) {
            System.out.println("case 0x" + Integer.toHexString(i) + ":\n//TODO impl\nbreak;");
        }
    }

    @Test
    public void testMain() throws IOException {
        Main.main(null);
    }
}
