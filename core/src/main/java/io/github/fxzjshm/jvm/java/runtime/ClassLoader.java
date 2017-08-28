package io.github.fxzjshm.jvm.java.runtime;

import java.util.Hashtable;
import java.util.Map;

public class ClassLoader {
    public Map<String,Class> map=new Hashtable<>();
    public Classpath classpath;

    public ClassLoader(Classpath classpath){
        this.classpath=classpath;
    }

    public Class loadClass(String name){
        if(map.containsKey(name))return map.get(name);
        else return loadNonArrayClass(name);
    }

    public Class loadNonArrayClass(String name){
        // TODO
        return null;
    }
}
