package io.github.fxzjshm.jvm.java.classfile;

import java.io.IOException;

import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos.AttributeInfo;
import io.github.fxzjshm.jvm.java.classfile.cp.ConstantPool;
import io.github.fxzjshm.jvm.java.classfile.cp.Utf8RefInfo;

/**
 * Stores .class file information.
 *
 * @author fxzjshm
 */
public class ClassFile {

    /**
     * Magic number, should be 0xCAFEBABE now.
     */
    public static final int MAGIC = 0xcafebabe;
    /**
     * Class reader for this class file.
     */
    public ByteArrayReader reader;
    /**
     * Versions.
     */
    public int major, minor;
    public ConstantPool cp;
    /**
     * Access flags.
     */
    public int accessFlags;
    public MemberInfo[] fields, methods;
    public AttributeInfo[] attributes;
    /**
     * Constant pool index.
     */
    public int thisClass, superClass, interfaces[];
    public String name, superClassName, interfaceNames[], packageName;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ClassFile(ByteArrayReader reader) throws IOException {
        this.reader = reader;

        // Magic number
        if (reader.readInt32() != MAGIC) {
            throw new ClassFormatError("Magic number is wrong!");
        }

        // Minor and major
        minor = reader.readUint16();
        major = reader.readUint16();
        checkVersion();

        // Constant pool
        cp = new ConstantPool(reader);

        // Access flags
        accessFlags = reader.readUint16();

        // Class indexes
        thisClass = reader.readUint16();
        superClass = reader.readUint16();
        interfaces = reader.readUint16s();

        // Read members
        fields = loadMembers();
        methods = loadMembers();

        // Attribute info
        attributes = AttributeInfos.attributeInfos(reader, cp);

        // Names
        name = ((Utf8RefInfo) (cp.infos[thisClass].info)).s;
        int divIndex = name.lastIndexOf("/");
        packageName = (divIndex > -1) ? (name.substring(0, divIndex)) : ("");

        superClassName = ((Utf8RefInfo) (cp.infos[superClass].info)).s;

        interfaceNames = new String[interfaces.length];
        for (int i = 0; i < interfaceNames.length; i++) {
            interfaceNames[i] = ((Utf8RefInfo) (cp.infos[interfaces[i]].info)).s;
        }
    }

    public void checkVersion() {
        if (major == 45) {
            return;
        }
        if (46 <= major && major <= 52) {
            if (minor == 0) {
                return;
            }
        }
        throw new UnsupportedClassVersionError();
    }

    public MemberInfo[] loadMembers() {
        int memberCount = reader.readUint16();
        MemberInfo[] members = new MemberInfo[memberCount];
        for (int i = 0; i < memberCount; i++) {
            MemberInfo member = new MemberInfo(
                    /*cp = */cp,
                    /*accessFlags = */reader.readUint16(),
                    /*nameIndex = */reader.readUint16(),
                    /*descriptorIndex = */reader.readUint16(),
                    /*attributes = */AttributeInfos.attributeInfos(reader, cp),
                    /*classFile = */this
            );
            members[i] = member;
        }
        return members;
    }
}
