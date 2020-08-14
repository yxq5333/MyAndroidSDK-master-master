package com.crazyhuskar.myandroidsdk.ip;


import android.text.TextUtils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * IP配置
 * <p>
 * Created by YXQ 2020-08-12.
 */
@Table(name = "IPConfig")
public class IPConfig implements Serializable {

    @Column(name = "tableId", isId = true)
    private int tableId;

    @Column(name = "protocol")
    private String protocol = "http://";
    @Column(name = "ip")
    private String ip;
    @Column(name = "port")
    private String port = "";
    @Column(name = "projectName")
    private String projectName = "";
    @Column(name = "realmNameHttp")
    private String realmName = "";

    public int getTableId() {
        return tableId;
    }

    public IPConfig setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public IPConfig setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public IPConfig setPort(String port) {
        this.port = port;
        return this;
    }

    public IPConfig setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public IPConfig setRealmName(String realmName) {
        this.realmName = realmName;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRealmName() {
        return realmName;
    }

    public String getRequestHead() {

        StringBuilder url = new StringBuilder();
        if (!TextUtils.isEmpty(protocol)) {
            url.append(protocol);
        }
        if (!TextUtils.isEmpty(ip)) {
            url.append(ip);
        }
        if (!TextUtils.isEmpty(port)) {
            url.append(":");
            url.append(port);
        }

        return url.toString();
    }

    public String getRequestUrl(String api) {

        StringBuilder url = new StringBuilder(getRequestHead());
        if (!TextUtils.isEmpty(projectName)) {
            url.append("/");
            url.append(projectName);
        }
        if (!TextUtils.isEmpty(realmName)) {
            url.append("/");
            url.append(realmName);
        }
        if (!TextUtils.isEmpty(api)) {
            if (!api.startsWith("/")) {
                url.append("/");
            }
            url.append(api);
        }

        return url.toString();
    }

    public String getRequestUrl() {
        return getRequestUrl(null);
    }

    public static boolean equals(IPConfig a, IPConfig b) {
        return TextUtils.equals(a.protocol, b.protocol)
                && TextUtils.equals(a.ip, b.ip)
                && TextUtils.equals(a.port, b.port)
                && TextUtils.equals(a.projectName, b.projectName)
                && TextUtils.equals(a.realmName, b.realmName);
    }

    public static IPConfig copy(IPConfig ipConfig) {

        IPConfig result = new IPConfig();

        result.protocol = ipConfig.protocol;
        result.ip = ipConfig.ip;
        result.port = ipConfig.port;
        result.projectName = ipConfig.projectName;
        result.realmName = ipConfig.realmName;

        return result;
    }
}
