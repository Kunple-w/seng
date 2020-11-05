package com.github.seng.common;

/**
 * life cycle
 *
 * @author wangyongxu
 */
public interface LifeCycle {

    boolean isAvailable();

    void init();

    void destroy();

    default State getState(){
        throw new UnsupportedOperationException();
    }

    enum State {

        /**
         * ready, such as: when new Object
         * can't work
         */
        READY,

        /**
         * normal
         * working
         */
        NORMAL,

        /**
         * finish, success or failed
         * can't work
         */
        FINISH,

    }
}
