package io.github.fxzjshm.jvm.java.runtime;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import io.github.fxzjshm.jvm.java.classfile.Bitmask;
import io.github.fxzjshm.jvm.java.classfile.ByteArrayReader;
import io.github.fxzjshm.jvm.java.runtime.data.Field;
import io.github.fxzjshm.jvm.java.runtime.data.Instance;
import io.github.fxzjshm.jvm.java.runtime.data.Method;
import io.github.fxzjshm.jvm.java.runtime.data.RuntimeConstantPool;
import io.github.fxzjshm.jvm.java.runtime.ref.ClassRef;
import io.github.fxzjshm.jvm.java.runtime.data.Class;
import io.github.fxzjshm.jvm.java.runtime.ref.FieldRef;

public class Frame {
    private Map<Integer, Object> localVars = new LinkedHashMap<>();
    private OperandStack<Object> operandStack = new OperandStack<>();
    private int nextPC;
    /**
     * Refers to the thread that contains this frame.
     */
    public Thread thread;
    private Method method;
    public ByteArrayReader reader;

    public Frame(Thread thread, Method method) {
        this.thread = thread;
        this.method = method;
        reader = new ByteArrayReader(method.code);
    }

    private void branch(int offset) {
        nextPC = reader.getPos() + offset - 2 * reader.sizeAfterLastReset;
    }

    /**
     * Do one step of bytecode.
     */
    public void exec() throws Throwable {
        reader.setPos(nextPC);
        int code = reader.readUint8(), ret = 0;
        switch (code) {
            case 0x0: // nop
                break;
            case 0x1: // aconst_null
                operandStack.push(null);
                break;
            case 0x2:
            case 0x3:
            case 0x4:
            case 0x5:
            case 0x6:
            case 0x7:
            case 0x8: // iconst_<n>
                operandStack.push(code - 0x03);
                break;
            case 0x9:
            case 0xa: // lconst_<n>
                operandStack.push((long) (code - 0x09));
                break;
            case 0xb:
            case 0xc:
            case 0xd: // fconst_<n>
                operandStack.push((float) (code - 0x0b));
                break;
            case 0xe:
            case 0xf: // dconst_<n>
                operandStack.push((double) (code - 0x0e));
                break;
            case 0x10: // bipush
                operandStack.push(reader.readInt8());
                break;
            case 0x11: // sipush
                operandStack.push(reader.readInt16());
                break;
            case 0x12: // ldc
            case 0x13: // ldc_w
            case 0x14: // ldc2_w
//TODO impl
                int index = (code == 0x12) ? reader.readUint8() : reader.readUint16();
                Object c = method.clazz.rtcp.consts[index];
                if (c instanceof Integer || c instanceof Float || c instanceof Long || c instanceof Double) {
                    operandStack.push(c);
                }/* else if (c instanceof ClassRef) {
                    operandStack.push(((ClassRef) c).resolvedClass().jClass());
                }*/ else {
                    throw new UnsupportedOperationException("TODO: ldc"); // TODO: ldc
                }
                break;
            case 0x15: // iload
            case 0x16: // lload
            case 0x17: // fload
            case 0x18: // dload
            case 0x19: // aload
                operandStack.push(localVars.get(reader.readUint8()));
                break;
            case 0x1a:
            case 0x1b:
            case 0x1c:
            case 0x1d: // iload_<n>
                operandStack.push(localVars.get(code - 0x1a));
                break;
            case 0x1e:
            case 0x1f:
            case 0x20:
            case 0x21: // lload_<n>
                operandStack.push(localVars.get(code - 0x1e));
                break;
            case 0x22:
            case 0x23:
            case 0x24:
            case 0x25: // fload_<n>
                operandStack.push(localVars.get(code - 0x22));
                break;
            case 0x26:
            case 0x27:
            case 0x28:
            case 0x29: // dload_<n>
                operandStack.push(localVars.get(code - 0x26));
                break;
            case 0x2a:
            case 0x2b:
            case 0x2c:
            case 0x2d: // aload_<n>
                operandStack.push(localVars.get(code - 0x2a));
                break;
            case 0x2e: // iaload
//TODO impl
                break;
            case 0x2f: // laload
//TODO impl
                break;
            case 0x30: // faload
//TODO impl
                break;
            case 0x31: // daload
//TODO impl
                break;
            case 0x32: // aaload
//TODO impl
                break;
            case 0x33: // baload
//TODO impl
                break;
            case 0x34: // caload
//TODO impl
                break;
            case 0x35: // saload
//TODO impl
                break;
            case 0x36: // istore
            case 0x37: // lstore
            case 0x38: // fstore
            case 0x39: // dstore
            case 0x3a: // astore
                localVars.put(reader.readUint8(), operandStack.pop());
                break;
            case 0x3b:
            case 0x3c:
            case 0x3d:
            case 0x3e: // istore_<n>
                localVars.put(code - 0x3b, operandStack.pop());
                break;
            case 0x3f:
            case 0x40:
            case 0x41:
            case 0x42: // lstore_<n>
                localVars.put(code - 0x3f, operandStack.pop());
                break;
            case 0x43:
            case 0x44:
            case 0x45:
            case 0x46: // fstore_<n>
                localVars.put(code - 0x43, operandStack.pop());
                break;
            case 0x47:
            case 0x48:
            case 0x49:
            case 0x4a: // dstore_<n>
                localVars.put(code - 0x47, operandStack.pop());
                break;
            case 0x4b:
            case 0x4c:
            case 0x4d:
            case 0x4e: // astore_<n>
                localVars.put(code - 0x4b, operandStack.pop());
                break;
            case 0x4f: // iastore
//TODO impl
                break;
            case 0x50: // lastore
//TODO impl
                break;
            case 0x51: // fastore
//TODO impl
                break;
            case 0x52: // dastore
//TODO impl
                break;
            case 0x53: // aastore
//TODO impl
                break;
            case 0x54: // bastore
//TODO impl
                break;
            case 0x55: // castore
//TODO impl
                break;
            case 0x56: // sastore
//TODO impl
                break;
            case 0x57: // pop
                operandStack.popRaw();
                break;
            case 0x58: // pop2
                operandStack.popRaw();
                operandStack.popRaw();
                break;
            case 0x59: // dup
                operandStack.push(operandStack.peek());
                break;
            case 0x5a: // dup_x1
                Object v1 = operandStack.popRaw(), v2 = operandStack.popRaw();
                operandStack.pushRaw(v1);
                operandStack.pushRaw(v2);
                operandStack.pushRaw(v1);
                break;
            case 0x5b: // dup_x2
                v1 = operandStack.popRaw();
                v2 = operandStack.popRaw();
                Object v3 = operandStack.popRaw();
                operandStack.pushRaw(v1);
                operandStack.pushRaw(v3);
                operandStack.pushRaw(v2);
                operandStack.pushRaw(v1);
                break;
            case 0x5c: // dup2
                v1 = operandStack.popRaw();
                v2 = operandStack.popRaw();
                operandStack.pushRaw(v2);
                operandStack.pushRaw(v1);
                operandStack.pushRaw(v2);
                operandStack.pushRaw(v1);
                break;
            case 0x5d: // dup2_x1
                v1 = operandStack.popRaw();
                v2 = operandStack.popRaw();
                v3 = operandStack.popRaw();
                operandStack.pushRaw(v2);
                operandStack.pushRaw(v1);
                operandStack.pushRaw(v3);
                operandStack.pushRaw(v2);
                operandStack.pushRaw(v1);
                break;
            case 0x5e: // dup2_x2
                v1 = operandStack.popRaw();
                v2 = operandStack.popRaw();
                v3 = operandStack.popRaw();
                Object v4 = operandStack.popRaw();
                operandStack.pushRaw(v2);
                operandStack.pushRaw(v1);
                operandStack.pushRaw(v4);
                operandStack.pushRaw(v3);
                operandStack.pushRaw(v2);
                operandStack.pushRaw(v1);
                break;
            case 0x5f: // swap
                v1 = operandStack.popRaw();
                v2 = operandStack.popRaw();
                operandStack.pushRaw(v1);
                operandStack.pushRaw(v2);
                break;
            case 0x60: // iadd
                Integer i1 = (Integer) operandStack.pop();
                Integer i2 = (Integer) operandStack.pop();
                operandStack.push(i1 + i2);
                break;
            case 0x61: // ladd
                Long l1 = (Long) operandStack.pop();
                Long l2 = (Long) operandStack.pop();
                operandStack.push(l1 + l2);
                break;
            case 0x62: // fadd
                Float f1 = (Float) operandStack.pop();
                Float f2 = (Float) operandStack.pop();
                operandStack.push(f1 + f2);
                break;
            case 0x63: // dadd
                Double d1 = (Double) operandStack.pop();
                Double d2 = (Double) operandStack.pop();
                operandStack.push(d1 + d2);
                break;
            case 0x64: // isub
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i2 - i1);
                break;
            case 0x65: // lsub
                l1 = (Long) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l2 - l1);
                break;
            case 0x66: // fsub
                f1 = (Float) operandStack.pop();
                f2 = (Float) operandStack.pop();
                operandStack.push(f2 - f1);
                break;
            case 0x67: // dsub
                d1 = (Double) operandStack.pop();
                d2 = (Double) operandStack.pop();
                operandStack.push(d2 - d1);
                break;
            case 0x68: // imul
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i1 * i2);
                break;
            case 0x69: // lmul
                l1 = (Long) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l1 * l2);
                break;
            case 0x6a: // fmul
                f1 = (Float) operandStack.pop();
                f2 = (Float) operandStack.pop();
                operandStack.push(f1 * f2);
                break;
            case 0x6b: // dmul
                d1 = (Double) operandStack.pop();
                d2 = (Double) operandStack.pop();
                operandStack.push(d1 * d2);
                break;
            case 0x6c: // idiv
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i2 / i1);
                break;
            case 0x6d: // ldiv
                l1 = (Long) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l2 / l1);
                break;
            case 0x6e: // fdiv
                f1 = (Float) operandStack.pop();
                f2 = (Float) operandStack.pop();
                operandStack.push(f2 / f1);
                break;
            case 0x6f: // ddiv
                d1 = (Double) operandStack.pop();
                d2 = (Double) operandStack.pop();
                operandStack.push(d2 - d1);
                break;
            case 0x70: // irem
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i2 % i1);
                break;
            case 0x71: // lrem
                l1 = (Long) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l2 % l1);
                break;
            case 0x72: // frem
                f1 = (Float) operandStack.pop();
                f2 = (Float) operandStack.pop();
                operandStack.push(f2 % f1);
                break;
            case 0x73: // drem
                d1 = (Double) operandStack.pop();
                d2 = (Double) operandStack.pop();
                operandStack.push(d2 - d1);
                break;
            case 0x74: // ineg
                i1 = (Integer) operandStack.pop();
                operandStack.push(-i1);
                break;
            case 0x75: // lneg
                l1 = (Long) operandStack.pop();
                operandStack.push(-l1);
                break;
            case 0x76: // fneg
                f1 = (Float) operandStack.pop();
                operandStack.push(-f1);
                break;
            case 0x77: // dneg
                d1 = (Double) operandStack.pop();
                operandStack.push(-d1);
                break;
            case 0x78: // ishl
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i2 << (i1 & 0x1f));
                break;
            case 0x79: // lshl
                i1 = (Integer) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l2 << (i1 & 0x3f));
                break;
            case 0x7a: // ishr
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i2 >> (i1 & 0x1f));
                break;
            case 0x7b: // lusr
                i1 = (Integer) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l2 >> (i1 & 0x3f));
                break;
            case 0x7c: // iushr
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i2 >>> (i1 & 0x1f));
                break;
            case 0x7d: // lushr
                i1 = (Integer) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l2 >>> (i1 & 0x3f));
                break;
            case 0x7e: // iand
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i1 & i2);
                break;
            case 0x7f: // land
                l1 = (Long) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l1 & l2);
                break;
            case 0x80: // ior
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i1 | i2);
                break;
            case 0x81: // lor
                l1 = (Long) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l1 | l2);
                break;
            case 0x82: // ixor
                i1 = (Integer) operandStack.pop();
                i2 = (Integer) operandStack.pop();
                operandStack.push(i1 ^ i2);
                break;
            case 0x83: // lxor
                l1 = (Long) operandStack.pop();
                l2 = (Long) operandStack.pop();
                operandStack.push(l1 ^ l2);
                break;
            case 0x84: // iinc
                index = reader.readUint8();
                int cst = reader.readInt8();
                localVars.put(index, (int) localVars.get(index) + cst);
                break;
            case 0x85: // i2l
                operandStack.push((long) ((int) operandStack.pop()));
                break;
            case 0x86: // i2f
                operandStack.push((float) ((int) operandStack.pop()));
                break;
            case 0x87: // i2d
                operandStack.push((double) ((int) operandStack.pop()));
                break;
            case 0x88: // l2i
                operandStack.push((int) ((long) operandStack.pop()));
                break;
            case 0x89: // l2f
                operandStack.push((float) ((long) operandStack.pop()));
                break;
            case 0x8a: // l2d
                operandStack.push((double) ((long) operandStack.pop()));
                break;
            case 0x8b: // f2i
                operandStack.push((int) ((float) operandStack.pop()));
                break;
            case 0x8c: // f2l
                operandStack.push((long) ((float) operandStack.pop()));
                break;
            case 0x8d: // f2d
                operandStack.push((double) ((float) operandStack.pop()));
                break;
            case 0x8e: // d2i
                operandStack.push((int) ((double) operandStack.pop()));
                break;
            case 0x8f: // d2l
                operandStack.push((long) ((double) operandStack.pop()));
                break;
            case 0x90: // d2f
                operandStack.push((float) ((double) operandStack.pop()));
                break;
            case 0x91: // i2b
                operandStack.push((byte) ((int) operandStack.pop()));
                break;
            case 0x92: // i2c
                operandStack.push((char) ((int) operandStack.pop()));
                break;
            case 0x93: // i2s
                operandStack.push((short) ((int) operandStack.pop()));
                break;
            case 0x94: // lcmp
                l1 = (Long) operandStack.pop();
                l2 = (Long) operandStack.pop();
                if (l1 < l2) operandStack.push(1);
                else if (l1 > l2) operandStack.push(-1);
                else operandStack.push(0);
                break;
            case 0x95: // fcmpl
                ret = -1;
            case 0x96: // fcmpg
                if (ret == 0) ret = 1;
                f1 = (Float) operandStack.pop();
                f2 = (Float) operandStack.pop();
                if (f1.equals(Float.NaN)) operandStack.push(ret);
                else if (f2.equals(Float.NaN)) operandStack.push(ret);
                else if (f1 < f2) operandStack.push(1);
                else if (f1 > f2) operandStack.push(-1);
                else if (f1.equals(f2)) operandStack.push(0);
                break;
            case 0x97: // dcmpl
                ret = -1;
            case 0x98: // dcmpg
                if (ret == 0) ret = 1;
                d1 = (Double) operandStack.pop();
                d2 = (Double) operandStack.pop();
                if (d1.equals(Double.NaN)) operandStack.push(ret);
                else if (d2.equals(Double.NaN)) operandStack.push(ret);
                else if (d1 < d2) operandStack.push(1);
                else if (d1 > d2) operandStack.push(-1);
                else if (d1.equals(d2)) operandStack.push(0);
                break;
            case 0x99: // ifeq
                int address = reader.readInt16();
                if (((Integer) operandStack.pop()) == 0) branch(address);
                break;
            case 0x9a: // ifne
                address = reader.readInt16();
                if (((Integer) operandStack.pop()) != 0) branch(address);
                break;
            case 0x9b: // iflt
                address = reader.readInt16();
                if (((Integer) operandStack.pop()) < 0) branch(address);
                break;
            case 0x9c: // ifge
                address = reader.readInt16();
                if (((Integer) operandStack.pop()) >= 0) branch(address);
                break;
            case 0x9d: // ifgt
                address = reader.readInt16();
                if (((Integer) operandStack.pop()) > 0) branch(address);
                break;
            case 0x9e: // ifle
                address = reader.readInt16();
                if (((Integer) operandStack.pop()) <= 0) branch(address);
                break;
            case 0x9f: // if_icmpeq
                i2 = (Integer) operandStack.pop();
                i1 = (Integer) operandStack.pop();
                address = reader.readInt16();
                if (Objects.equals(i1, i2)) branch(address);
                break;
            case 0xa0: // if_icmpne
                i2 = (Integer) operandStack.pop();
                i1 = (Integer) operandStack.pop();
                address = reader.readInt16();
                if (!Objects.equals(i1, i2)) branch(address);
                break;
            case 0xa1:// if_icmplt
                i2 = (Integer) operandStack.pop();
                i1 = (Integer) operandStack.pop();
                address = reader.readInt16();
                if (i1 < i2) branch(address);
                break;
            case 0xa2:// if_icmpge
                i2 = (Integer) operandStack.pop();
                i1 = (Integer) operandStack.pop();
                address = reader.readInt16();
                if (i1 >= i2) branch(address);
                break;
            case 0xa3:// if_icmpgt
                i2 = (Integer) operandStack.pop();
                i1 = (Integer) operandStack.pop();
                address = reader.readInt16();
                if (i1 > i2) branch(address);
                break;
            case 0xa4:// if_icmple
                i2 = (Integer) operandStack.pop();
                i1 = (Integer) operandStack.pop();
                address = reader.readInt16();
                if (i1 <= i2) branch(address);
                break;
            case 0xa5:// if_acmpeq
                Object o2 = operandStack.pop();
                Object o1 = operandStack.pop();
                address = reader.readInt16();
                if (o1 == o2) branch(address);
                break;
            case 0xa6:// if_acmpne
                o2 = operandStack.pop();
                o1 = operandStack.pop();
                address = reader.readInt16();
                if (o1 != o2) branch(address);
                break;
            case 0xa7:// goto
                address = reader.readInt16();
                branch(address);
                break;
            case 0xa8:// jsr
                if (method.info.classFile.major <= 49) {
                    address = reader.readInt16();
                    branch(address);
                    operandStack.push(reader.getPos() + 3);
                } else
                    throw new RuntimeException("JSR is just like tan(PI/2) when class file version is"
                            + method.info.classFile.major + "." + method.info.classFile.minor);
                break;
            case 0xa9:// ret
                if (method.info.classFile.major <= 49) {
                    branch((Integer) localVars.get(reader.readUint8()));
                } else
                    throw new RuntimeException("RET is just like tan(PI/2) when class file version is"
                            + method.info.classFile.major + "." + method.info.classFile.minor);
                break;
            case 0xaa:// tableswitch
                reader.skipPadding();
                int defaultOffset = reader.readInt32(),
                        min = reader.readInt32(),
                        offset,
                        max = reader.readInt32(),
                        jumpOffsetsCount = max - min + 1;
                int[] jumpOffsets = reader.readInt32s(jumpOffsetsCount);
                index = (Integer) operandStack.pop();
                if (min <= index && index <= max) offset = jumpOffsets[index - min];
                else offset = defaultOffset;
                branch(offset);
                break;
            case 0xab:// lookupswitch
                int key = (Integer) operandStack.pop();
                reader.skipPadding();
                defaultOffset = reader.readInt32();
                int npairs = reader.readInt32();
                int[] data = reader.readInt32s(npairs * 2);
                for (int i = 0; i < npairs * 2; i += 2)
                    if (data[i] == key) {
                        branch(data[i + 1]);
                        break;
                    }
                branch(defaultOffset);
                break;
            case 0xac:// ireturn
//TODO impl
                break;
            case 0xad:// lreturn
//TODO impl
                break;
            case 0xae:// freturn
//TODO impl
                break;
            case 0xaf:// dreturn
//TODO impl
                break;
            case 0xb0:// areturn
//TODO impl
                break;
            case 0xb1:// return
//TODO impl
                break;
            case 0xb2:// getstatic
            case 0xb3:// putstatic
                index = reader.readUint16();
                Field field = ((FieldRef) (method.clazz.rtcp.consts[index])).resolvedField();
                if ((field.info.accessFlags & Bitmask.ACC_STATIC) == 0)
                    throw new IncompatibleClassChangeError(field.info.name + " is not a static field!");
                switch (code) {
                    case 0xb2:// getstatic
                        operandStack.push(field.clazz.staticVars[field.slotId]);
                        break;
                    case 0xb3:// putstatic
                        if ((field.info.accessFlags & Bitmask.ACC_FINAL) != 0) {
                            if (method.clazz != field.clazz || (!Objects.equals(method.info.name, "<clinit>")))
                                throw new IllegalAccessError("Cannot init a final field "
                                        + field.info.name + " fron normal method "
                                        + method.clazz.classFile.name + "/" + method.info.name);
                        }
                        field.clazz.staticVars[field.slotId] = operandStack.pop();
                        break;
                    default:
                        break;
                }
                break;
            case 0xb4:// getfield
            case 0xb5:// putfield
                index = reader.readUint16();
                field = ((FieldRef) (method.clazz.rtcp.consts[index])).resolvedField();
                if ((field.info.accessFlags & Bitmask.ACC_STATIC) != 0)
                    throw new IncompatibleClassChangeError(field.info.name + " is a static field!");
                switch (code) {
                    case 0xb4:// getfield
                        Instance ref = (Instance) operandStack.pop();
                        operandStack.push(ref.data[field.slotId]);
                        break;
                    case 0xb5:// putfield
                        if ((field.info.accessFlags & Bitmask.ACC_FINAL) != 0) {
                            if (method.clazz != field.clazz || (!Objects.equals(method.info.name, "<init>")))
                                throw new IllegalAccessError("Cannot init a final field "
                                        + field.info.name + " fron normal method "
                                        + method.clazz.classFile.name + "/" + method.info.name);
                        }
                        Object val = operandStack.pop();
                        ref = (Instance) operandStack.pop();
                        ref.data[field.slotId] = val;
                        break;
                    default:
                        break;
                }
                break;
            case 0xb6:// invokevirtual
//TODO impl
                break;
            case 0xb7:// invokespecial
//TODO impl
                operandStack.pop();
                break;
            case 0xb8:// invokestatic
//TODO impl
                break;
            case 0xb9:// invokeinterface
//TODO impl
                break;
            case 0xba:// invokedynamic
//TODO impl
                break;
            case 0xbb:// new
                index = reader.readUint16();
                RuntimeConstantPool rcp = method.clazz.rtcp;
                ClassRef classRef = (ClassRef) (rcp.consts[index]);
                Class clazz = classRef.resolvedClass();
                if (((clazz.classFile.accessFlags & Bitmask.ACC_INTERFACE) != 0)
                        || ((clazz.classFile.accessFlags & Bitmask.ACC_ABSTRACT) != 0))
                    throw new InstantiationError("Cannot new " + clazz.classFile.name);
                Instance instance = new Instance(clazz);
                operandStack.push(instance);
                break;
            case 0xbc:// newarray
//TODO impl
                break;
            case 0xbd:// anewarray
//TODO impl
                break;
            case 0xbe:// arraylength
//TODO impl
                break;
            case 0xbf:// athrow
//TODO impl
                break;
            case 0xc0:// checkcast
                index = reader.readUint16();
                FieldRef fieldRef = (FieldRef) operandStack.peek();
                if (fieldRef == null)
                    break;
                else {
                    clazz = ((ClassRef) (method.clazz.rtcp.consts[index])).resolvedClass();
                    if (!fieldRef.isInstanceOf(clazz))
                        throw new ClassCastException("Cannot cast " + fieldRef.className + " to " + clazz.classFile.name);
                }
                break;
            case 0xc1:// instanceof
                index = reader.readUint16();
                fieldRef = (FieldRef) operandStack.pop();
                if (fieldRef == null)
                    operandStack.push(0);
                else {
                    clazz = ((ClassRef) (method.clazz.rtcp.consts[index])).resolvedClass();
                    operandStack.push((fieldRef.isInstanceOf(clazz)) ? 1 : 0);
                }
                break;
            case 0xc2:// monitorenter
//TODO impl
                break;
            case 0xc3:// monitorexit
//TODO impl
                break;
            case 0xc4:// wide
//TODO impl
                break;
            case 0xc5:// multianewarray
//TODO impl
                break;
            case 0xc6:// ifnull
//TODO impl
                break;
            case 0xc7:// ifnonnull
//TODO impl
                break;
            case 0xc8:// goto_w
//TODO impl
                break;
            case 0xc9:// jsr_w
//TODO impl
                break;
            case 0xca:// breakpoint
//TODO impl
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown bytecode %x", code));
        }
        nextPC += reader.sizeAfterLastReset;
    }
}
