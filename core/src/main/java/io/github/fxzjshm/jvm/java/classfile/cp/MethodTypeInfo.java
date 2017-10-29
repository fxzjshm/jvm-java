package io.github.fxzjshm.jvm.java.classfile.cp;

public class MethodTypeInfo extends ConstantPool.ConstantComplexInfo{
    public int descriptorIndex;
    public String descriptor;

    @Override
    public void cache(ConstantPool cp) {
        descriptor = (String) (cp.infos[descriptorIndex].info);
    }
}
