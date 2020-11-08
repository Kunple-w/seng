package com.github.seng.core.rpc;

import com.github.seng.common.URL;
import com.github.seng.common.spi.SPI;

import java.util.List;

/**
 * load balance
 * @author qiankewei
 */

@SPI
public interface LoadBalance<T> {

    public Reference<T> select(List<Reference<T>> references, URL url);

}
