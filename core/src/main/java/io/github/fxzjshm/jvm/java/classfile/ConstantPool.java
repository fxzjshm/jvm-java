package io.github.fxzjshm.jvm.java.classfile;

import java.io.DataInputStream;
import java.io.IOException;

import static io.github.fxzjshm.jvm.java.classfile.ConstantPool.ConstantInfo.*;

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
        for (int i = 1; i < cpCount; i++) {
            ConstantInfo ci = new ConstantInfo();
            ci.tag = reader.readUint8();
            ConstantComplexInfo cinfo = new ConstantComplexInfo();
            switch (ci.tag) {
                case CONSTANT_Integer:
                    ci.info = reader.readInt32();
                    break;
                case CONSTANT_Float:
                    ci.info = Float.intBitsToFloat(reader.readInt32());
                    break;
                case CONSTANT_Long:
                    ci.info = reader.readInt64();
                    i++;
                    break;
                case CONSTANT_Double:
                    ci.info = Double.longBitsToDouble(reader.readInt64());
                    i++;
                    break;
                case CONSTANT_Utf8:
                    ci.info = new DataInputStream(reader).readUTF();
                    break;
                case CONSTANT_String:
                case CONSTANT_Class:
                    ci.info/*constant pool index*/ = reader.readUint16();
                    break;
                case CONSTANT_Fieldref:
                case CONSTANT_Methodref:
                case CONSTANT_InterfaceMethodref:
                    cinfo.classIndex = reader.readUint16();
                    cinfo.nameAndTypeIndex = reader.readUint16();
                    ci.info = cinfo;
                    break;
                case CONSTANT_NameAndType:
                    cinfo.nameIndex = reader.readUint16();
                    cinfo.descriptorIndex = reader.readUint16();
                    ci.info = cinfo;
                    break;
                case CONSTANT_MethodType:
                    cinfo.descriptorIndex = reader.readUint16();
                    ci.info = cinfo;
                    break;
                case CONSTANT_MethodHandle:
                    cinfo.referenceKind = reader.readUint8();
                    cinfo.refernceIndex = reader.readUint16();
                    ci.info = cinfo;
                    break;
                case CONSTANT_InvokeDynamic:
                    cinfo.bootstrapMethodAttrIndex = reader.readUint16();
                    cinfo.nameAndTypeIndex = reader.readUint16();
                    ci.info = cinfo;
                    break;
                default:
                    throw new ClassFormatError("Unknown constant pool tag:" + ci.tag);
            }
            cp[i] = ci;
        }
        this.infos = cp;
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
    public static class ConstantComplexInfo {

        protected int /* constant pool index */ classIndex, nameIndex, nameAndTypeIndex, descriptorIndex, referenceKind, refernceIndex, bootstrapMethodAttrIndex;
    }
}
