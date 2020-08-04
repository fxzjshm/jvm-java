package io.github.fxzjshm.jvm.java.runtime.data;

import java.util.LinkedList;

import io.github.fxzjshm.jvm.java.api.VClass;
import io.github.fxzjshm.jvm.java.api.VMethod;
import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.MemberInfo;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.AttributeInfos;
import io.github.fxzjshm.jvm.java.classfile.attrinfo.CodeAttribute;

public class EmulatedMethod extends EmulatedMember implements VMethod {

    public CodeAttribute codeAttribute;
    public int argSlotCount;
    public MethodDescriptor parsedDescriptor;

    public EmulatedMethod(MemberInfo info, VClass clazz) {
        super(info, clazz);
        for (AttributeInfos.AttributeInfo attributeInfo : info.attributes) {
            if (attributeInfo instanceof CodeAttribute) {
                codeAttribute = (CodeAttribute) attributeInfo;
            }
        }
        argSlotCount = 0;
        calcArgSlotCount(info.descriptor);
        //TODO Reflect.methodMap.put(info.name, this);
    }

    public void calcArgSlotCount(String descriptor) {
        parsedDescriptor = MethodDescriptorParser.parseMethodDescriptor(descriptor);
        for (String parameterType : parsedDescriptor.parameterTypes) {
            argSlotCount++;
            if (parameterType.equals("J") || parameterType.equals("D")) {
                argSlotCount++;
            }
        }
        if ((info.accessFlags & Bitmask.ACC_STATIC) == 0) {
            argSlotCount++;
        }
    }

    public static class MethodDescriptorParser {
        public static MethodDescriptor parseMethodDescriptor(String descriptor) {
            MethodDescriptor methodDescriptor = new MethodDescriptor();
            if (descriptor.charAt(0) != '(') {
                error(descriptor);
            }
            int paramEnd = descriptor.lastIndexOf(')');
            if (paramEnd < 0) {
                error(descriptor);
            }
            methodDescriptor.parameterTypes = parseTypes(descriptor, 1, paramEnd);
            String[] returnTypes = parseTypes(descriptor, paramEnd + 1, descriptor.length());
            if (returnTypes.length != 1) error(descriptor);
            methodDescriptor.returnType = returnTypes[0];
            return methodDescriptor;
        }

        public static String[] parseTypes(String descriptor, int begin, int end) {
            LinkedList<String> types = new LinkedList<>();
            String s;
            for (int i = begin; i < end; ) {
                s = parseOneType(descriptor, i, end);
                types.add(s);
                i += s.length();
            }
            return (String[]) types.toArray();
        }

        public static String parseOneType(String descriptor, int begin, int end) {
            char c = descriptor.charAt(begin);
            switch (c) {
                case 'B':
                case 'C':
                case 'D':
                case 'F':
                case 'I':
                case 'J':
                case 'S':
                case 'Z':
                    return String.valueOf(c);
                case 'L':
                    int j = descriptor.indexOf(';', begin);
                    if (j < 0) {
                        error(descriptor);
                    }
                    return descriptor.substring(begin, j + 1);
                case '[':
                    return "[" + parseOneType(descriptor, begin + 1, end);
                default:
                    error(descriptor);
                    return "";
            }
        }

        public static void error(String descriptor) {
            throw new IllegalArgumentException("Cannot parse descriptor: " + descriptor);
        }
    }

    public static class MethodDescriptor {
        public String[] parameterTypes;
        public String returnType;
    }
}
