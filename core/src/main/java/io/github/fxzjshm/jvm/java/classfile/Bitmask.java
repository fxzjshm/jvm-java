package io.github.fxzjshm.jvm.java.classfile;

import java.util.Objects;

import io.github.fxzjshm.jvm.java.api.VClass;

/**
 * Bit masks for reading access flags. e.g isPublic = accessFlags | ACC_PUBLIC;
 *
 * @author fxzjshm
 */
public abstract class Bitmask {

    public static final int ACC_PUBLIC = 0x0001,
            ACC_PRIVATE = 0x0002,
            ACC_PROTECTED = 0x0004,
            ACC_STATIC = 0x0008,
            ACC_FINAL = 0x0010,
            ACC_SUPER = 0x0020,
            ACC_VOLATILE = 0x0040,
            ACC_TRANSIENT = 0x0080,
            ACC_NATIVE = 0x0100,
            ACC_INTERFACE = 0x0200,
            ACC_ABSTRACT = 0x0400,
            ACC_STRICTFP = 0x0800,
            ACC_SYNTHETIC = 0x1000,
            ACC_ANNOTAYION = 0x2000,
            ACC_ENUM = 0x4000;

    /**
     * @param target {@link VClass} of the object to be accessed.
     */
    public static boolean isAccessibleTo(VClass origin, VClass target, int targetAccessFlag) {
        if ((targetAccessFlag & ACC_PUBLIC) != 0) return true;
        else if ((targetAccessFlag & ACC_PROTECTED) != 0) {
            return origin.isSubClassOf(target) || (Objects.equals(origin.packageName, target.packageName));
        } else if ((targetAccessFlag & ACC_PRIVATE) != 0) return origin == target;
        else /* (default) */
            return Objects.equals(origin.packageName, target.packageName);
    }

}
