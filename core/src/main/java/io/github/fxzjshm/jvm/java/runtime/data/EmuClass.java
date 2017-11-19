package io.github.fxzjshm.jvm.java.runtime.data;

import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.loader.ClassLoader;

public class EmuClass extends Class {

    public EmuClass(ClassFile cf, ClassLoader cl) {
        super(cf, cl);
    }

}
