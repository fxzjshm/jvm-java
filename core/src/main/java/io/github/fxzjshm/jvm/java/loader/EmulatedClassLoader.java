package io.github.fxzjshm.jvm.java.loader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import io.github.fxzjshm.jvm.java.api.VClassLoader;
import io.github.fxzjshm.jvm.java.api.VClasspath;
import io.github.fxzjshm.jvm.java.api.VField;
import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.classfile.ClassFile;
import io.github.fxzjshm.jvm.java.runtime.VM;
import io.github.fxzjshm.jvm.java.runtime.data.EmulatedClass;
import io.github.fxzjshm.jvm.java.runtime.data.EmulatedField;

class EmulatedClassLoader extends VClassLoader {
	public Map<String, VClass> map = new Hashtable<>();
	public VClasspath classpath;

	public EmulatedClassLoader(VM vm, VClasspath classpath) {
		this.vm = vm;
		this.classpath = classpath;
	}

	@Override
	public VClass loadClass(String name) throws IOException {
		if (map.containsKey(name))
			return map.get(name);
		else {
			byte[] data = loadNonArrayClass(name);
			EmulatedClass clazz = defineClass(data);
			link(clazz);
			return clazz;
		}
	}

	private static void link(EmulatedClass clazz) {
		// TODO verify(clazz);
		prepare(clazz);
	}

	private static void prepare(EmulatedClass clazz) {
		calcInstanceFieldSlotIds(clazz);
		calcStaticFieldSlotIds(clazz);
		allocAndInitStaticVars(clazz);
	}

	private static void allocAndInitStaticVars(EmulatedClass clazz) {
		clazz.staticVars = new Object[clazz.staticSlotCount];
		for (VField field : clazz.fields) {
			EmulatedField eField = (EmulatedField) field;
			if (((eField.info.accessFlags & Bitmask.ACC_STATIC) != 0)
					&& ((eField.info.accessFlags & Bitmask.ACC_FINAL) != 0)) {
				// initStaticFinalVar(clazz, field);
				int cpIndex = eField.constValueIndex;
				if (cpIndex > 0) {
					clazz.staticVars[eField.slotId] = clazz.rtcp.consts[eField.constValueIndex];
				}
			}
		}
	}

	private static void calcStaticFieldSlotIds(EmulatedClass clazz) {
		int slotId = 0;
		for (VField field : clazz.fields) {
			EmulatedField eField = (EmulatedField) field;
			if ((eField.info.accessFlags & Bitmask.ACC_STATIC) != 0) {
				eField.slotId = slotId;
				slotId += (field.isLongOrDouble()) ? 2 : 1;
			}
		}
		clazz.staticSlotCount = slotId;
	}

	private static void calcInstanceFieldSlotIds(EmulatedClass clazz) {
		int slotId = (clazz.superClass != null) ? (((EmulatedClass) (clazz.superClass)).instanceSlotCount) : 0;
		for (VField field : clazz.fields) {
			EmulatedField eField = (EmulatedField) field;
			if ((eField.info.accessFlags & Bitmask.ACC_STATIC) == 0) {
				eField.slotId = slotId;
				slotId += (field.isLongOrDouble()) ? 2 : 1;
			}
		}
		clazz.instanceSlotCount = slotId;
	}

	private EmulatedClass defineClass(byte[] data) throws IOException {
		EmulatedClass clazz = new EmulatedClass(new ClassFile(new ByteArrayReader(data)), this);
		resolveSuperClass(clazz);
		resolveInterfaces(clazz);
		map.put(clazz.classFile.name, clazz);
		return clazz;
	}

	private static void resolveInterfaces(EmulatedClass clazz) throws IOException {
		int n = clazz.interfaces.length;
		if (n > 0) {
			clazz.interfaces = new VClass[n];
			for (int i = 0; i < n; i++) {
				clazz.interfaces[i] = clazz.loader.loadClass(clazz.classFile.interfaceNames[i]);
			}
		}
	}

	private static void resolveSuperClass(EmulatedClass clazz) throws IOException {
		if (!Objects.equals(clazz.classFile.name, "java/lang/Object")) {
			clazz.superClass = clazz.loader.loadClass(clazz.classFile.superClassName);
		}
	}

	private byte[] loadNonArrayClass(String name) {
		return classpath.readClass(name);
	}
}
