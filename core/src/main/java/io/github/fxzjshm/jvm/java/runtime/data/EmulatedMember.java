package io.github.fxzjshm.jvm.java.runtime.data;

import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.api.VMember;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;

public class EmulatedMember implements VMember {
	public MemberInfo info;
	public VClass clazz;

	public EmulatedMember(MemberInfo info, VClass clazz) {
		this.info = info;
		this.clazz = clazz;
	}

	@Override
	public VClass clazz() {
		return clazz;
	}

	@Override
	public int accessFlags() {
		return info.accessFlags;
	}

	@Override
	public String name() {
		return info.name;
	}

	@Override
	public String descriptor() {
		return info.descriptor;
	}
}
