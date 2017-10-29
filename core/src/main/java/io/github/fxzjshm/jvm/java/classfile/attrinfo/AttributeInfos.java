package io.github.fxzjshm.jvm.java.classfile.attrinfo;

import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.MarkerAttribute.DeprecatedAttribute;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.MarkerAttribute.SyntheticAttribute;

public abstract class AttributeInfos {

    public static AttributeInfo[] attributeInfos(ByteArrayReader reader, ConstantPool cp) {
        int count = reader.readUint16();
        AttributeInfo[] attrs = new AttributeInfo[count];
        for (int i = 0; i < count; i++) {

            int attrNameIndex = reader.readUint16();
            String attrName = (String) cp.infos[attrNameIndex].info;
            int attrLen = reader.readInt32();
            AttributeInfo attr = newAttributeInfo(attrName, attrLen, cp);
            attr.readInfo(reader);
            attrs[i] = attr;
        }
        return attrs;
    }

    @SuppressWarnings("deprecation")
    public static AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantPool cp) {
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

    /**
     * Stores information of attribute.
     *
     * @author fxzjshm
     */
    public interface AttributeInfo {
        void readInfo(ByteArrayReader reader);
    }
}
