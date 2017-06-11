package io.github.fxzjshm.jvm.java.classfile;

import static io.github.fxzjshm.jvm.java.classfile.ConstantPool.ConstantInfo.*;
import java.io.*;

/**
 * A constant pool that stores constant information in class files.
 *
 * @author fxzjshm
 */
public class ConstantPool {

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
    
    public static class ConstantMemberrefInfo{
    protected int /* constant pool index */ classIndex,nameAndTypeIndex;
}

    public ConstantInfo[] cp;

    public ConstantPool(ClassReader reader) throws IOException {
        int cpCount = reader.readUint16();
        cp = new ConstantInfo[cpCount];
        for (int i = 1; i < cpCount; i++) {
            ConstantInfo ci=new ConstantInfo();
            ci.tag=reader.readUint8();
            switch(ci.tag){
                	case CONSTANT_Integer:
		ci.info=reader.readInt32();
                break;
	case CONSTANT_Float:
		ci.info=Float.intBitsToFloat(reader.readInt32());
                break;
	case CONSTANT_Long:
		ci.info=reader.readInt64();
                break;
	case CONSTANT_Double:
		ci.info=Double.longBitsToDouble(reader.readInt64());
                break;
	case CONSTANT_Utf8:
            int length=reader.readUint16();
            byte[] bytes=reader.readBytes(length);
		ci.info=new DataInputStream(new ByteArrayInputStream(bytes)).readUTF();
                break;
	case CONSTANT_String:
	case CONSTANT_Class:
		ci.info/*constant pool index*/=reader.readUint16();
                break;
	case CONSTANT_Fieldref:
	case CONSTANT_Methodref:
        case CONSTANT_InterfaceMethodref:
		ConstantMemberrefInfo mInfo=new ConstantMemberrefInfo();
                mInfo.classIndex=reader.readUint16();
                mInfo.nameAndTypeIndex=reader.readUint16();
                ci.info=mInfo;
                break;
	case CONSTANT_NameAndType:
		return &ConstantNameAndTypeInfo{}
                break;
	case CONSTANT_MethodType:
		return &ConstantMethodTypeInfo{}
                break;
	case CONSTANT_MethodHandle:
		return &ConstantMethodHandleInfo{}
                break;
	case CONSTANT_InvokeDynamic:
		return &ConstantInvokeDynamicInfo{}
                break;
	default:
		throw new ClassFormatError("constant pool tag!");
	}
            }
        }
    }
