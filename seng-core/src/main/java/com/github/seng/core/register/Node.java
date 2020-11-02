package com.github.seng.core.register;

import com.github.seng.core.rpc.URL;
import com.github.seng.core.rpc.config.LifeCycle;

/**
 * 节点信息
 *
 * @author wangyongxu
 * @date 2020/9/15 18:45
 */
public interface Node extends LifeCycle {

    URL getURL();

}
