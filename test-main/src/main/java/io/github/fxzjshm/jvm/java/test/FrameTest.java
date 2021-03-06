package io.github.fxzjshm.jvm.java.test;

import java.io.IOException;
import java.util.Objects;

import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.runtime.EmulatedFrame;
import io.github.fxzjshm.jvm.java.runtime.EmulatedThread;
import io.github.fxzjshm.jvm.java.runtime.VirtualMachine;
import io.github.fxzjshm.jvm.java.runtime.data.EmulatedMethod;


public class FrameTest {

    // @Before
    public void init() throws IOException {
        if (ClassFileTest.classMap.isEmpty()) {
            ClassFileTest cft = new ClassFileTest();
            cft.parseClass();
        }
    }

    // @Test
    public void testExecGauss() throws Throwable {
        ClassFile cf = ClassFileTest.classMap.get("Gauss");
        VirtualMachine vm = new VirtualMachine();
        if (cf != null) {
            for (MemberInfo methodInfo : cf.methods) {
                if (Objects.equals(methodInfo.name, "Gauss.main")) {
                    EmulatedMethod method = new EmulatedMethod(methodInfo, null);
                    EmulatedFrame frame = new EmulatedFrame(new EmulatedThread(vm), method);
                    while (frame.reader.available() != 0) frame.exec();
                    TestHelper.assertEqual(5050, frame.localVars.get(1));
                }
            }
        }
    }

}