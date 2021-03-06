package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.Map;

import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.loader.ExternalClassLoader;

public class ExternalClass extends VClass {

    @SuppressWarnings("rawtypes")
	public Class jClass; // TODO impl

    public ExternalClass(ExternalClassLoader loader, @SuppressWarnings("rawtypes") Class clazz) {
        accessFlags = clazz.getModifiers();
        this.loader = loader;
        // TODO wrap other information

        name = jClass.getName();
        packageName = jClass.getPackage().getName();

        jClass = clazz;
    }

    @Override
    public boolean isSubClassOf(VClass target) { // TODO check this
        Map<String, VClass> classMap = loader.vm.reflect.classMap;
        for (@SuppressWarnings("rawtypes")
		Class clazz = jClass.getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            if (classMap.get(clazz.getName()) == target)
                return true; // require each class is mapped when it is loaded
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean isAssignableFrom(VClass other) {
        if (other instanceof ExternalClass)
            return jClass.isAssignableFrom(((ExternalClass) other).jClass);// Quick way
        else return super.isAssignableFrom(other);// Should be enough. See below.
    }

    // Methods in api/Class.java should be enough since we have wrapped all classes.
    // TODO Correct me if I am wrong.
    /*
    public boolean isImplements(Class iface) {
        // assume that no external class extends/implements emulated class
        // if not, external ClassLoader would have complained
        if (!(iface instanceof ExternalClass)) return false;
        java.lang.Class jclass = ((ExternalClass) iface).jClass;
        for (java.lang.Class clazz : jClass.getInterfaces()) {
            if (clazz == jclass) return true;
        }
        return false;
    }

    public boolean isSubInterfaceOf(Class iface) {
        // same as above
        if (!(iface instanceof ExternalClass)) return false;
        java.lang.Class jclass = ((ExternalClass) iface).jClass;
        for (java.lang.Class clazz : jclass.) {

        }
        return;
    }
    */
}
