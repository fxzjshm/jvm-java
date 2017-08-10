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
    public void testReadUInt8() {
        Assert.assertEquals(193, new ByteArrayReader(new byte[]{-63}).readUint8());
    }

    @Test
    public void testReadInt8() {
        Assert.assertEquals(-63, new ByteArrayReader(new byte[]{-63}).readInt8());
    }

    @Test
    public void testReadInt16() {
        Assert.assertEquals(-2333, new ByteArrayReader(new byte[]{-10,-29}).readInt16());
    }

    @Test
    public void testMain() throws IOException {
        Main.main(null);
    }
}
