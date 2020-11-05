package com.github.seng.core.spi;

import com.github.seng.common.spi.ExtensionLoader;
import com.github.seng.core.UserService;
import com.github.seng.core.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionLoaderTest {
    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoaderTest.class);

    @Test
    void getExtension() {
        ExtensionLoader<UserService> extensionLoader = ExtensionLoader.getExtensionLoader(UserService.class);
        UserService userService = extensionLoader.getExtension("userServiceImpl");
        assertEquals(UserServiceImpl.class, userService.getClass(), "获取扩展类错误");
    }
}