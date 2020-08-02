package io.github.fxzjshm.jvm.java.runtime;

import java.util.HashMap;
import java.util.Map;

import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.api.VField;
import io.github.fxzjshm.jvm.java.api.VMethod;

public class Reflect {
    public Map<String, VClass> classMap = new HashMap<>();
    public Map<String, VField> fieldMap = new HashMap<>();
    public Map<String, VMethod> methodMap = new HashMap<>();
}
