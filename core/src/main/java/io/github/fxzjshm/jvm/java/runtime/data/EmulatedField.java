package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.Objects;

import io.github.fxzjshm.jvm.java.api.VField;
import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;

public class EmulatedField extends EmulatedMember implements VField {

	public final boolean isLongOrDouble;
    public int slotId, constValueIndex = -1;

    public EmulatedField(MemberInfo info, VClass clazz) {
    	super(info,clazz);
        isLongOrDouble = Objects.equals(info.descriptor, "L") || Objects.equals(info.descriptor, "D");
        // TODO Reflect.fieldMap.put(info.name, this);
    

}

	@Override
	public boolean isLongOrDouble() {
		return isLongOrDouble;
	}
}