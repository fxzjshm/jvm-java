package io.github.fxzjshm.jvm.java.test;

import java.io.IOException;
import java.util.Objects;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.runtime.Frame;
import io.github.fxzjshm.jvm.java.runtime.Thread;
import io.github.fxzjshm.jvm.java.runtime.data.Method;


public class FrameTest {

    @Before
    public void init() throws IOException {
        if (ClassFileTest.classMap.isEmpty()){
            ClassFileTest cft=new ClassFileTest();
            cft.compileClass();
            cft.parseClass();
        }
    }

    @Ignore
    @Test
    public void testExec() throws Throwable {
        ClassFile cf = ClassFileTest.classMap.get("Gauss");
        if(cf != null) {
            for (MemberInfo methodInfo : cf.methods) {
                if (Objects.equals(methodInfo.name, "Gauss.main")) {
                    Method method = new Method(methodInfo, null);
                    Frame frame = new Frame(new Thread(), method);
                    while (frame.reader.available() != 0) frame.exec();
                    System.out.println();
                }
            }
        }
    }

}