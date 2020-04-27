package com.nut2014.newtech.test;

/**
 * @author feiltel 2020/4/27 0027
 */
class Test {
    private static final Test ourInstance = new Test();

    static Test getInstance() {
        return ourInstance;
    }

    private Test() {
    }
}
