package io.github.fxzjshm.jvm.java.classfile;

import io.github.fxzjshm.jvm.java.classfile.ConstantPool.ConstantInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttrbuteInfos;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttrbuteInfos.AttributeInfo;
import java.io.IOException;

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
    public ConstantInfo[] cp;
    /**
     * Access flags.
     */
    public int accessFlags;
    /**
     * Constant pool index.
     */
    protected int thisClass, superClass, interfaces[];
    public MemberInfo[] fields, methods;
    public AttributeInfo[] attributes;

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
        cp = ConstantPool.constantPool(reader);

        // Access flags
        accessFlags = reader.readUint16();

        // Class indexes
        thisClass = reader.readUint16();
        superClass = reader.readUint16();
        interfaces = reader.readUint16s();

        // Read members
        fields = loadMembers();
        methods = loadMembers();

        //Attrbute info
        attributes = AttrbuteInfos.attributeInfos(reader, cp);
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
            MemberInfo member = new MemberInfo();
            member.cp = cp;
            member.accessFlags = reader.readUint16();
            member.nameIndex = reader.readUint16();
            member.descriptorIndex = reader.readUint16();
            member.attributes = AttrbuteInfos.attributeInfos(reader, cp);
            members[i] = member;
        }
        return members;
    }
}
