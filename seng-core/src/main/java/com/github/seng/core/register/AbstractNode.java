package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;

/**
 * @author wangyongxu
 */
public abstract class AbstractNode implements Node {
    private volatile State state;
    private URL url;

    public AbstractNode(URL url) {
        this.url = url;
    }

    @Override
    public URL getURL() {
        return url;
    }

    @Override
    public boolean isAvailable() {
        return state == State.NORMAL;
    }

    @Override
    public void init() {
        state = State.READY;
    }

    @Override
    public void destroy() {
        state = State.FINISH;
    }

    @Override
    public State getState() {
        return state;
    }
}
