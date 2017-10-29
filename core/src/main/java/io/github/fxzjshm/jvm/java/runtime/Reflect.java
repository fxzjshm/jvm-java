package io.github.fxzjshm.jvm.java.runtime;

import java.util.Hashtable;
import java.util.Map;

import io.github.fxzjshm.jvm.java.runtime.data.Class;
import io.github.fxzjshm.jvm.java.runtime.data.Field;
import io.github.fxzjshm.jvm.java.runtime.data.Method;

public class Reflect {
    public static Map<String, Class> classMap = new Hashtable<>();
    public static Map<String, Field> fieldMap = new Hashtable<>();
    public static Map<String, Method> methodMap = new Hashtable<>();
}
