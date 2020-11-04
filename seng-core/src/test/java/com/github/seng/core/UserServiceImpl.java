package com.github.seng.core;

import com.github.seng.core.spi.SPIAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangyongxu
 */
@SPIAlias
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String hello(String msg) {
        return "hello " + msg;
    }

    @Override
    public void hi(String name, String msg) {
        logger.info("hi, {}, {}", name, msg);
    }
}
