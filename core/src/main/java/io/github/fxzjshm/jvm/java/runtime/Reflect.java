package io.github.fxzjshm.jvm.java.runtime;

import java.util.Hashtable;
import java.util.Map;

import io.github.fxzjshm.jvm.java.api.VField;
import io.github.fxzjshm.jvm.java.api.VMethod;
import io.github.fxzjshm.jvm.java.api.VClass;

public class Reflect {
    public Map<String, VClass> classMap = new Hashtable<>();
    public Map<String, VField> fieldMap = new Hashtable<>();
    public Map<String, VMethod> methodMap = new Hashtable<>();
}
