package io.github.fxzjshm.jvm.java.classfile.cp;

import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_Class;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_Double;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_Fieldref;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_Float;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_Integer;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_InterfaceMethodref;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_InvokeDynamic;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_Long;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_MethodHandle;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_MethodType;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_Methodref;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_NameAndType;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_String;
import static io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool.ConstantInfo.CONSTANT_Utf8;

import java.io.DataInputStream;
import java.io.IOException;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;

/**
 * A constant pool that stores constant information in class files.
 *
 * @author fxzjshm
 */
public class ConstantPool {
    public ConstantInfo[] infos;

    public ConstantPool(ConstantInfo[] infos) {
        this.infos = infos;
    }

    public ConstantPool(ByteArrayReader reader) throws IOException {
        int cpCount = reader.readUint16();
        ConstantInfo[] cp = new ConstantInfo[cpCount];
        boolean i2 = false;
        for (int i = 1; i < cpCount; i++) {
            ConstantInfo ci = new ConstantInfo();
            ci.tag = reader.readUint8();
            switch (ci.tag) {
                case CONSTANT_Integer:
                    ci.info = reader.readInt32();
                    break;
                case CONSTANT_Float:
                    ci.info = Float.intBitsToFloat(reader.readInt32());
                    break;
                case CONSTANT_Long:
                    ci.info = reader.readInt64();
                    i2 = true;
                    break;
                case CONSTANT_Double:
                    ci.info = Double.longBitsToDouble(reader.readInt64());
                    i2 = true;
                    break;
                case CONSTANT_Utf8:
                    ci.info = new DataInputStream(reader).readUTF();
                    break;
                case CONSTANT_String:
                case CONSTANT_Class:
                    Utf8RefInfo utf8RefInfo = new Utf8RefInfo();
                    utf8RefInfo.cpIndex /* constant pool index */ = reader.readUint16();
                    ci.info = utf8RefInfo;
                    break;
                case CONSTANT_Fieldref:
                case CONSTANT_Methodref:
                case CONSTANT_InterfaceMethodref:
                    MemberRefInfo memberRefInfo = new MemberRefInfo();
                    memberRefInfo.classIndex = reader.readUint16();
                    memberRefInfo.nameAndTypeIndex = reader.readUint16();
                    ci.info = memberRefInfo;
                    break;
                case CONSTANT_NameAndType:
                    NameAndTypeRefInfo nameAndTypeRefInfo = new NameAndTypeRefInfo();
                    nameAndTypeRefInfo.nameIndex = reader.readUint16();
                    nameAndTypeRefInfo.descriptorIndex = reader.readUint16();
                    ci.info = nameAndTypeRefInfo;
                    break;
                case CONSTANT_MethodType:
                    MethodTypeInfo methodTypeInfo = new MethodTypeInfo();
                    methodTypeInfo.descriptorIndex = reader.readUint16();
                    ci.info = methodTypeInfo;
                    break;
                case CONSTANT_MethodHandle:
                    MethodHandleInfo methodHandle = new MethodHandleInfo();
                    methodHandle.referenceKind = reader.readUint8();
                    methodHandle.referenceIndex = reader.readUint16();
                    ci.info = methodHandle;
                    break;
                case CONSTANT_InvokeDynamic:
                    InvokeDynamicInfo invokeDynamicInfo = new InvokeDynamicInfo();
                    invokeDynamicInfo.bootstrapMethodAttrIndex = reader.readUint16();
                    invokeDynamicInfo.nameAndTypeIndex = reader.readUint16();
                    ci.info = invokeDynamicInfo;
                    break;
                default:
                    throw new ClassFormatError("Unknown constant pool tag:" + ci.tag);
            }
            cp[i] = ci;
            if(i2) {
                i++;
                i2 = false;
            }
        }
        this.infos = cp;
        for (int i = 1; i < cpCount; i++)
            if (cp[i] != null && cp[i].info instanceof ConstantComplexInfo)
                ((ConstantComplexInfo) cp[i].info).cache(this);
    }

    public static class ConstantInfo {

        public static final int CONSTANT_Utf8 = 1,
                CONSTANT_Integer = 3,
                CONSTANT_Float = 4,
                CONSTANT_Long = 5,
                CONSTANT_Double = 6,
                CONSTANT_Class = 7,
                CONSTANT_String = 8,
                CONSTANT_Fieldref = 9,
                CONSTANT_Methodref = 10,
                CONSTANT_InterfaceMethodref = 11,
                CONSTANT_NameAndType = 12,
                CONSTANT_MethodHandle = 15,
                CONSTANT_MethodType = 16,
                CONSTANT_InvokeDynamic = 18;
        public int tag;
        public Object info;
    }

    /**
     * Contains some complex costant info. Some of the fields may be null, according to {@link ConstantInfo#tag}.
     */
    public static abstract class ConstantComplexInfo {

        // int /* constant pool index */ classIndex, nameIndex, nameAndTypeIndex, descriptorIndex, referenceKind, referenceIndex, bootstrapMethodAttrIndex;

        public abstract void cache(ConstantPool cp);
    }
}
