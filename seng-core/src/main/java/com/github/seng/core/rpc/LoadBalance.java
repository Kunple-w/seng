package com.github.seng.core.rpc;

import com.github.seng.common.URL;
import com.github.seng.common.spi.SPI;

import java.util.List;

/**
 * load balance
 * @author qiankewei
 */

@SPI
public interface LoadBalance {

    public URL select(List<URL> urlList);
}
