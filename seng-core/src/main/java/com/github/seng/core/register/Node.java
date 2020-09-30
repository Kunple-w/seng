package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;

/**
 * 节点信息
 *
 * @author wangyongxu
 * @date 2020/9/15 18:45
 */
public interface Node {


    boolean isAvailable();

    URL getURL();

    void destroy();
}
