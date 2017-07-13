package io.github.fxzjshm.jvm.java.classfile;

import org.junit.Ignore;

public class Test {
    @Ignore
    @org.junit.Test
    public void genBytecodeTemplate() {
        for (int i = 0; i <= 255; i++) {
            System.out.println("case 0x" + Integer.toHexString(i) + ":\n//TODO impl\nbreak;");
        }
    }
}
