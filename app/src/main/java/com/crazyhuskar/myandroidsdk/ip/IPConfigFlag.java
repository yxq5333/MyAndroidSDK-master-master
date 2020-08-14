package com.crazyhuskar.myandroidsdk.ip;


import android.text.TextUtils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * IP配置标识
 * <p>
 * Created by YXQ 2020-08-12.
 */
@Table(name = "IPConfigFlag")
public class IPConfigFlag implements Serializable {

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

    public IPConfigFlag setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public IPConfigFlag setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public IPConfigFlag setPort(String port) {
        this.port = port;
        return this;
    }

    public IPConfigFlag setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public IPConfigFlag setRealmName(String realmName) {
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

    public static boolean equals(IPConfig a, IPConfigFlag b) {
        return TextUtils.equals(a.getProtocol(), b.protocol)
                && TextUtils.equals(a.getIp(), b.ip)
                && TextUtils.equals(a.getPort(), b.port)
                && TextUtils.equals(a.getProjectName(), b.projectName)
                && TextUtils.equals(a.getRealmName(), b.realmName);
    }

    public static IPConfigFlag copy(IPConfig ipConfig) {

        IPConfigFlag result = new IPConfigFlag();

        result.protocol = ipConfig.getProtocol();
        result.ip = ipConfig.getIp();
        result.port = ipConfig.getPort();
        result.projectName = ipConfig.getProjectName();
        result.realmName = ipConfig.getRealmName();

        return result;
    }
}
