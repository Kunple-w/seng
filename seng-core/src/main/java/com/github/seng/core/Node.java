package com.github.seng.core;

/**
 * @author wangyongxu
 */
public interface Node {

    String getAddress();

    NodeType getNodeType();

    boolean available();
}
