package com.github.seng.core;

import com.github.seng.core.spi.SPI;

/**
 * @author wangyongxu
 */
@SPI
public interface UserService {
    String hello(String msg);

    void hi(String name, String msg);
}
