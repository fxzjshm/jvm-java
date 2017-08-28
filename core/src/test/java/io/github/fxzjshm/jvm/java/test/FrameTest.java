package io.github.fxzjshm.jvm.java.test;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.runtime.Frame;
import io.github.fxzjshm.jvm.java.runtime.Method;
import io.github.fxzjshm.jvm.java.runtime.Thread;


public class FrameTest {

    @Before
    public void init() throws IOException {
        if (ClassFileTest.classMap.isEmpty()){
            ClassFileTest cft=new ClassFileTest();
            cft.compileClass();
            cft.parseClass();
        }
    }

    @Test
    public void testExec() throws Throwable {
        ClassFile cf = ClassFileTest.classMap.get("Gauss");
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