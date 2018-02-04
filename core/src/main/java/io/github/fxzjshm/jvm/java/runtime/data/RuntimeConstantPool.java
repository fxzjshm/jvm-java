package io.github.fxzjshm.jvm.java.runtime.data;

import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool;
import io.github.fxzjshm.jvm.java.classfile.cp.MemberRefInfo;
import io.github.fxzjshm.jvm.java.runtime.ref.InterfaceMethodRef;
import io.github.fxzjshm.jvm.java.runtime.ref.ClassRef;
import io.github.fxzjshm.jvm.java.runtime.ref.FieldRef;
import io.github.fxzjshm.jvm.java.runtime.ref.MethodRef;

import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.*;

public class RuntimeConstantPool {
    public Class clazz;
    public Object[] consts;

    public RuntimeConstantPool(Class clazz, ConstantPool cp) {
        this.clazz = clazz;
        consts = new Object[cp.infos.length];
        for (int i = 0; i < cp.infos.length; i++) {
            Object o;
            switch (cp.infos[i].tag) {
                case CONSTANT_Long:
                case CONSTANT_Double:
                    i++;
                case CONSTANT_Integer:
                case CONSTANT_Float:
                case CONSTANT_Utf8:
                    o = cp.infos[i].info;
                    break;
                case CONSTANT_String:
                    o = cp.infos[(int) cp.infos[i].info].info;
                    break;
                case CONSTANT_Class:
                    o = new ClassRef(this);
                    break;
                case CONSTANT_Fieldref:
                    o = new FieldRef(this, (MemberRefInfo) cp.infos[i].info);
                    break;
                case CONSTANT_Methodref:
                    o = new MethodRef(this, (MemberRefInfo) cp.infos[i].info);
                    break;
                case CONSTANT_InterfaceMethodref:
                    o = new InterfaceMethodRef();
                    break;
                /*
                TODO:
                case CONSTANT_NameAndType:
                case CONSTANT_MethodType:
                case CONSTANT_MethodHandle:
                case CONSTANT_InvokeDynamic:
                */
                default:
                    throw new ClassFormatError("Unknown constant pool tag:" + cp.infos[i].tag);
            }
            consts[i] = o;
        }
    }
}
