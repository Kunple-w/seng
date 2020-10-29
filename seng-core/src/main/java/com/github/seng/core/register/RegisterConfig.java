package com.github.seng.core.register;

import lombok.Data;

/**
 * register config
 *
 * @author wangyongxu
 */
@Data
public class RegisterConfig {

    /**
     * register node host
     */
    private String host;

    /**
     * register node port
     */
    private Integer port;

    /**
     * username for login register
     */
    private String username;

    /**
     * password for login register
     */
    private String password;

    /**
     * register protocol, such as http, zookeeper
     */
    private String protocol;

}
