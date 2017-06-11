package io.github.fxzjshm.jvm.java.classfile;

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
     * Versions.
     */
    public int major, minor;
    public ConstantPool cp;
    /**
     * Access flags.
     */
    public int accessFlags;
    protected int /*Constant pool index*/ thisClass, superClass, interfaces[];
    public MemberInfo[] members;

    public ClassFile(ClassReader reader) throws IOException {
        // Magic number
        if (reader.readInt32() != MAGIC) {
            throw new ClassFormatError("Magic number is wrong!");
        }

        // Minor and major
        minor = reader.readUint16();
        major = reader.readUint16();
        checkVersion(major, minor);

        // Constant pool
        cp = new ConstantPool(reader);

        // Access flags
        accessFlags = reader.readUint16();

        // Class indexes
        thisClass = reader.readUint16();
        superClass = reader.readUint16();
        interfaces = reader.readUint16s();

        // Read members
        int memberCount = reader.readUint16();
        members = new MemberInfo[memberCount];
        for (int i = 0; i < memberCount; i++) {
            MemberInfo member = new MemberInfo();

            members[i] = member;

        }
    }

    public static void checkVersion(int major, int minor) {
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
}
