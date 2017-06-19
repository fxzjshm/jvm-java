package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.ConstantPool.ConstantInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.MarkerAttribute.DeprecatedAttribute;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.MarkerAttribute.SyntheticAttribute;

public abstract class AttrbuteInfos {

    /**
     * Stores information of attribute.
     *
     * @author fxzjshm
     */
    public interface AttributeInfo {

        public void readInfo(ByteArrayReader reader);
    }

    public static AttributeInfo[] attributeInfos(ByteArrayReader reader, ConstantInfo[] cp) {
        int count = reader.readUint16();
        AttributeInfo[] attrs = new AttributeInfo[count];
        for (int i = 0; i < count; i++) {

            int attrNameIndex = reader.readUint16();
            String attrName = (String) cp[attrNameIndex].info;
            int attrLen = reader.readInt32();
            AttributeInfo attr = newAttributeInfo(attrName, attrLen, cp);
            attr.readInfo(reader);
            attrs[i] = attr;
        }
        return attrs;
    }

    @SuppressWarnings("deprecation")
    public static AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantInfo[] cp) {
        switch (attrName) {
            case "Code":
                CodeAttribute ca = new CodeAttribute();
                ca.cp = cp;
                return ca;
            case "ConstantValue":
                return new ConstantValueAttribute();
            case "Deprecated":
                return new DeprecatedAttribute();
            case "Exceptions":
                return new ExceptionsAttribute();
            case "LineNumberTable":
                return new LineNumberTableAttribute();
            case "LocalVariableTable":
                return new LocalVariableTableAttribute();
            case "SourceFile":
                SourceFileAttribute sfa = new SourceFileAttribute();
                sfa.cp = cp;
                return sfa;
            case "Synthetic":
                return new SyntheticAttribute();
            default:
                UnparsedAttribute ua = new UnparsedAttribute();
                ua.name = attrName;
                ua.length = attrLen;
                return ua;
        }
    }
}
