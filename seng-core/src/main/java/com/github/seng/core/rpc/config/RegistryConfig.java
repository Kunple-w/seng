package com.github.seng.core.rpc.config;

import com.github.seng.common.URLConstant;
import com.github.seng.common.URL;
import lombok.Data;

/**
 * registry config
 *
 * @author wangyongxu
 */
@Data
public class RegistryConfig {
    public RegistryConfig(String address) {
        URL url = URL.of(address);
        this.protocol = url.getProtocol();
        this.host = url.getHost();
        this.port = url.getPort();
        this.username = url.getUsername();
        this.password = url.getPassword();
        this.timeout = Integer.parseInt(url.getParam(URLConstant.TIMEOUT_KEY, "5000"));
    }

    private String protocol;
    private String host;
    private int port;
    private String username;
    private String password;
    private int timeout;
}
