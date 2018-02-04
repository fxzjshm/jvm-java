package io.github.fxzjshm.jvm.java.runtime;

import java.util.Hashtable;
import java.util.Map;

import io.github.fxzjshm.jvm.java.api.Class;
import io.github.fxzjshm.jvm.java.runtime.data.Field;
import io.github.fxzjshm.jvm.java.runtime.data.Method;

public class Reflect {
    public Map<String, Class> classMap = new Hashtable<>();
    public Map<String, Field> fieldMap = new Hashtable<>();
    public Map<String, Method> methodMap = new Hashtable<>();
}
