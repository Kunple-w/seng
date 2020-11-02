package com.github.seng.core.rpc;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * <pre>
 *
 * http://baidu.com?s=seng
 * seng://localhost:18080/com.github.seng.executor?limit=10&timeout=100s
 * </pre>
 *
 * @author wangyongxu
 */
@Data
public class URL {

    private String protocol;

    private String host;

    private int port;

    // interfaceName
    private String path;

    private String username;

    private String password;

    private Map<String, String> parameters;

    public URL(String protocol, String host, int port, String path) {
        this(protocol, host, port, path, null, null, new HashMap<>());
    }

    public URL(String protocol, String host, int port, String username, String password, String path) {
        this(protocol, host, port, path, username, password, new HashMap<>());
    }

    public URL(String protocol, String host, int port, String path, String username, String password, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.username = username;
        this.password = password;
        this.parameters = parameters;
    }

    public static URL of(String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url is blank?");
        }
        String paramString = url.substring(url.indexOf("?") + 1);
        Map<String, String> param = parseParams(paramString);
        String protocol;
        String host;
        int port;
        String path;
        String username = null;
        String password = null;
        int protocolIdx = url.indexOf("://");
        protocol = url.substring(0, protocolIdx);
        String noProtocol = url.substring(protocolIdx + 3);
        String hostAndPort = noProtocol.substring(0, noProtocol.indexOf("/"));
        if (hostAndPort.contains("@")) {
            String[] split = hostAndPort.split("@");
            String principal = split[0];
            String[] userPwd = principal.split(":");
            username = userPwd[0];
            password = userPwd[1];
            hostAndPort = split[1];
        }
        if (hostAndPort.contains(":")) {
            String[] parts = hostAndPort.split(":");
            host = parts[0];
            port = Integer.parseInt(parts[1]);
        } else {
            host = hostAndPort;
            port = -1;
        }
        path = StringUtils.substringBetween(noProtocol, "/", "?");

        return new URL(protocol, host, port, path, username, password, param);
    }

    private static Map<String, String> parseParams(String paramString) {
        Map<String, String> params = new HashMap<>();
        String[] parts = paramString.split("&");
        for (String part : parts) {
            String[] kv = part.split("=");
            if (kv.length == 2) {
                params.put(kv[0], kv[1] == null ? "" : kv[1]);
            } else {
                params.put(kv[0], "");
            }
        }
        return params;
    }

    public String getUrl() {
        String fullURL = getFullURL();
        return fullURL.substring(0, fullURL.indexOf("?"));
    }

    public String getIpAndPort() {
        return host + ":" + port;
    }

    public String getParam(String key, String defaultValue) {
        return getParameters().getOrDefault(key, defaultValue);
    }

    public String getParam(String key) {
        return getParameters().get(key);
    }

    public String setParam(String key, String value) {
        return getParameters().put(key, value);
    }

    public String getURLPath() {
        return protocol + "://" + host + ":" + port + "/" + path;
    }

    public String getFullURL() {
        String url = getURLPath();
        if (parameters == null || parameters.isEmpty()) {
            return url;
        }
        return url + "?" + buildParams();
    }

    private String buildParams() {
        StringJoiner sj = new StringJoiner("&");
        parameters.forEach((k, v) -> sj.add(k + "=" + v));
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return port == url.port &&
                Objects.equals(protocol, url.protocol) &&
                Objects.equals(host, url.host) &&
                Objects.equals(path, url.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, host, port, path);
    }

    @Override
    public String toString() {
        return getFullURL();
    }
}
