package com.github.seng.core.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangyongxu
 */
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String hello(String msg) {
        logger.info("hello {}", msg);
        return "hello " + msg;
    }
}
