package com.github.seng.core.rpc.config;

import lombok.Data;

/**
 * service config
 *
 * @author wangyongxu
 */
@Data
public class ServiceConfig {
    private String host;
    private int port;
}