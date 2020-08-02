package io.github.fxzjshm.jvm.java;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.fxzjshm.jvm.java.test.ByteArrayReaderTest;
import io.github.fxzjshm.jvm.java.test.ClassFileTest;
import io.github.fxzjshm.jvm.java.test.FrameTest;
import io.github.fxzjshm.jvm.java.test.HelloWorldTest;
import io.github.fxzjshm.jvm.java.test.TestRunnable;
import io.github.fxzjshm.jvm.java.test.TestWrapper;

import static com.badlogic.gdx.Application.LOG_DEBUG;

public class TestMain {
    public static Map<TestWrapper, Boolean> isAvaliable;
    public static boolean isSuccess;

    private TestMain() {
    }

    public static void main(String[] args) {
        isAvaliable = new HashMap<>();
        isSuccess = true;
        init();
        before();
        test();
        after();
        if (!isSuccess) throw new RuntimeException("Test failed. See above.");
    }

    // To avoid issues with static initializers, use this.
    private static void init() {

        ByteArrayReaderTest byteArrayReaderTest = new ByteArrayReaderTest();
        TestWrapper byteArrayReaderTestWrapper = new TestWrapper();
        byteArrayReaderTestWrapper.testFunctions.add(byteArrayReaderTest::testReadInt8);
        byteArrayReaderTestWrapper.testFunctions.add(byteArrayReaderTest::testReadInt16);
        byteArrayReaderTestWrapper.testFunctions.add(byteArrayReaderTest::testReadUInt8);
        isAvaliable.put(byteArrayReaderTestWrapper, true);

        ClassFileTest classFileTest = new ClassFileTest();
        TestWrapper classFileTestWrapper = new TestWrapper();
        classFileTestWrapper.testFunctions.add(classFileTest::parseClass);
        isAvaliable.put(classFileTestWrapper, true);

        FrameTest frameTest = new FrameTest();
        TestWrapper frameTestWrapper = new TestWrapper();
        frameTestWrapper.beforeFunctions.add(frameTest::init);
        frameTestWrapper.testFunctions.add(frameTest::testExecGauss);
        isAvaliable.put(frameTestWrapper, true);

        TestWrapper helloWorldTestWrapper = new TestWrapper();
        helloWorldTestWrapper.testFunctions.add(HelloWorldTest::test);
        isAvaliable.put(helloWorldTestWrapper, true);
    }

    private static void before() {
        Set<Map.Entry<TestWrapper, Boolean>> entrySet = isAvaliable.entrySet();
        for (Map.Entry<TestWrapper, Boolean> entry : entrySet) {
            try {
                Set<TestRunnable> beforeFunctions = entry.getKey().beforeFunctions;
                for (TestRunnable testRunnable : beforeFunctions) {
                    testRunnable.run();
                }
            } catch (Throwable throwable) {
                isAvaliable.replace(entry.getKey(), false); // ignore this test.
                // TODO check whether System.err is always available.
                throwable.printStackTrace();

                // clean up
                Set<TestRunnable> afterFunctions = entry.getKey().afterFunctions;
                for (TestRunnable testRunnable : afterFunctions) {
                    try {
                        testRunnable.run();
                    } catch (Throwable t) {
                        // I have nothing to say......
                        t.printStackTrace();
                        // TODO same as above
                    }
                }

            }
        }
    }

    private static void test() {
        Set<Map.Entry<TestWrapper, Boolean>> entrySet = isAvaliable.entrySet();
        for (Map.Entry<TestWrapper, Boolean> entry : entrySet) {
            if (entry.getValue()) {
                Set<TestRunnable> testFunctions = entry.getKey().testFunctions;
                for (TestRunnable testRunnable : testFunctions) {
                    try {
                        testRunnable.run();
                    } catch (Throwable throwable) {
                        Gdx.app.error("TestMain", throwable.toString());
                        isSuccess = false;
                    }
                }
            }
        }
    }

    private static void after() {
        Set<Map.Entry<TestWrapper, Boolean>> entrySet = isAvaliable.entrySet();
        for (Map.Entry<TestWrapper, Boolean> entry : entrySet) {
            if (entry.getValue()) {
                Set<TestRunnable> afterFunctions = entry.getKey().afterFunctions;
                for (TestRunnable testRunnable : afterFunctions) {
                    try {
                        testRunnable.run();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        }
    }

    public static class TestMainApplicationAdapter extends ApplicationAdapter {
        @Override
        public void create() {
            Gdx.app.setLogLevel(LOG_DEBUG);
            TestMain.main(null);
            Gdx.app.exit();
        }
    }
}
