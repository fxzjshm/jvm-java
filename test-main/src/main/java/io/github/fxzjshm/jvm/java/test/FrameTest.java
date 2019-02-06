package io.github.fxzjshm.jvm.java.test;

import java.io.IOException;
import java.util.Objects;

import io.github.fxzjshm.jvm.java.api.VMethod;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.runtime.EmulatedFrame;
import io.github.fxzjshm.jvm.java.runtime.Thread;
import io.github.fxzjshm.jvm.java.runtime.VM;
import io.github.fxzjshm.jvm.java.runtime.data.EmulatedMethod;


public class FrameTest {

    // @Before
    public void init() throws IOException {
        if (ClassFileTest.classMap.isEmpty()){
            ClassFileTest cft=new ClassFileTest();
            cft.compileClass();
            cft.parseClass();
        }
    }

    // @Test
    public void testExecGauss() throws Throwable {
        ClassFile cf = ClassFileTest.classMap.get("Gauss");
        VM vm = new VM();
        if(cf != null) {
            for (MemberInfo methodInfo : cf.methods) {
                if (Objects.equals(methodInfo.name, "Gauss.main")) {
                	EmulatedMethod method = new EmulatedMethod(methodInfo, null);
                    EmulatedFrame frame = new EmulatedFrame(new Thread(vm), method);
                    while (frame.reader.available() != 0) frame.exec();
                    TestHelper.assertEqual(5050,frame.localVars.get(1));
                }
            }
        }
    }

}