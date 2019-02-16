package com.comcast.algorithms;

import java.util.Date;

/**
 * Created by zzhou200 on 8/16/15.
 */
public class ReflectionTest {

    //Abstract Product
    interface BaseClass {
        String getBaseClassName();
    }

    //Abstract Factory
    interface BaseClassFactory {
        BaseClass createBaseClass();
    }

    //Concrete Factory
    class TestFactory1 implements BaseClassFactory {
        public BaseClass createBaseClass() {
            return new TestClass1();
        }
    }

    //Concrete Factory
    class TestFactory2 implements BaseClassFactory {
        public BaseClass createBaseClass() {
            return new TestClass2();
        }
    }

    //Concrete Product
    class TestClass1 extends Date implements BaseClass {
        public String getBaseClassName() {
            return TestClass1.class.getSuperclass().getName();
        }
    }

    //Concrete Product
    class TestClass2 extends Stack implements BaseClass {
        public String getBaseClassName() {
            return TestClass2.class.getSuperclass().getName();
        }
    }

}
