package io.github.fxzjshm.jvm.java.test;

public class Test1 {

    @Ignore
    @Test
    public void genBytecodeTemplate() {
        for (int i = 0; i <= 255; i++) {
            System.out.println("case 0x" + Integer.toHexString(i) + ":\n//TODO impl\nbreak;");
        }
    }
}
